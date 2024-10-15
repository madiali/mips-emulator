package com.comp541.mips;

/**
 * https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java. This link has some
 * discussion about which library to use to parse JSON in Java. org.json seems to match the original
 * code closely, so I decided on that.
 *
 * <p>C#'s JSON stuff will return null if a field is not found, whereas org.json will throw an
 * Exception. So, the original code is able to use the uint? (meaning either uint or null) type,
 * which is null if some field is not found in the JSON. e.g. uint? pc = token["programCounter"],
 * where the JSON token has the field "programCounter": some int. pc will then be an int if the
 * field is found, else null.
 *
 * <p>I replicate this behavior by using Integer, which is initialized to null, and try catch to
 * catch the Exception thrown if the field is not found. Then the Integer remains null if the field
 * is not found.
 *
 * <p>Lastly, some stuff about org.json. The C# code has JObject, JToken, and JArray. We have only
 * JSONObject and JSONArray. In JSON, anything in braces {...} is a JSONObject, and anything in
 * brackets [...] is a JSONArray. From a JSONObject, you can parse a field with o.getString(field
 * name), o.getInt(field name), etc. JSONArray can be iterated over pretty easily.
 */

import com.comp541.mips.exception.MappingException;
import com.comp541.mips.memory.MappedMemoryUnit;
import com.comp541.mips.memory.MemoryMapper;
import com.comp541.mips.memory.MemoryUnit;
import com.comp541.mips.memory.MemoryUnitFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramLoader {
    private Mips mips;
    private final String basePath;

    public ProgramLoader(File file) throws IOException {
        // If file is in /MIPS_Emulator/, getParent() returns /MIPS_Emulator (without the trailing /)
        // Works the same as FileInfo's .DirectoryName
        this.basePath = file.getParent();
        this.mips = loadMipsFromFile(file);
    }

    private Mips loadMipsFromFile(File file) throws IOException {
        String json = Files.readString(file.toPath());
        JSONObject project = new JSONObject(json);

        int pc = parseRequiredInt(project, "programCounter");
        JSONArray memoriesArray = parseRequiredJSONArray(project, "memories");

        Map<Class, List<MemoryUnit>> memDict = buildMemoryUnits(memoriesArray);

        String name;
        try {
            name = project.getString("projectName");
        } catch (Exception e) {
            name = file.getName();
        }
        float desiredClockSpeed = 0;
        try {
            // Assume clockSpeed is float, not String
            desiredClockSpeed = project.getFloat("clockSpeed");
        } catch (Exception e) {
        }

        // registers parameter is optional in the original code, passing in null
        // Mips.registers will be a new Registers() object
        return new Mips(pc, memDict, null, name, desiredClockSpeed);
    }

    /**
     * Parse a hex, binary, or decimal String field to int.
     *
     * @param token
     */
    private int parseNumber(String token) {
        token = token.replace("_", "");
        if (token.startsWith("0x")) {
            return Integer.parseUnsignedInt(token.substring(2), 16);
        }
        if (token.startsWith("0b")) {
            return Integer.parseUnsignedInt(token.substring(2), 2);
        }
        return Integer.parseUnsignedInt(token, 10);
    }

    /**
     * Parse a required integer field.
     *
     * @param object
     * @param field
     * @return int, throw IllegalArgumentException if field not found or illegally formatted.
     */
    private int parseRequiredInt(JSONObject object, String field) {
        Integer rv = null;
        try {
            rv = object.getInt(field);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Required integer field " + field + " not found or illegally formatted");
        }
        return rv;
    }

    /**
     * Parse a required JSONArray field.
     *
     * @param object
     * @param field
     * @return JSONArray, throw IllegalArgumentException if field not found or illegally formatted.
     */
    private JSONArray parseRequiredJSONArray(JSONObject object, String field) {
        JSONArray jArray = null;
        try {
            jArray = object.getJSONArray(field);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Required JSON array field " + field + " not found or illegally formatted");
        }
        return jArray;
    }

    /**
     * An integer field (e.g. startAddr, endAddr) in JSON can be either an integer (no quotes) or
     * String (with quotes) if hex or binary; this function parses the field both ways.
     *
     * @param object
     * @param field
     * @return the integer or null if the field doesn't exist
     */
    private Integer parseJSONIntField(JSONObject object, String field) {
        Integer rv = null;
        try {
            rv = object.getInt(field);
        } catch (Exception e) {
        }
        try {
            rv = parseNumber(object.getString(field));
        } catch (Exception e) {
        }
        return rv;
    }

    /**
     * Parse a String field
     *
     * @param object
     * @param field
     * @return the String or null if the field doesn't exist
     */
    private String parseJSONStringField(JSONObject object, String field) {
        String rv = null;
        try {
            rv = object.getString(field);
        } catch (Exception e) {
        }
        return rv;
    }

    private Map<Class, List<MemoryUnit>> buildMemoryUnits(JSONArray memoriesArray) {
        Map<Class, List<MemoryUnit>> memoryDict = new HashMap<>();
        List<MappedMemoryUnit> memUnits = new ArrayList<>();

        for (int i = 0; i < memoriesArray.length(); i++) {
            MemoryUnit mem = buildMemoryUnit(memoriesArray.getJSONObject(i));
            try {
                MappedMemoryUnit mappedMem = mapMemoryToAddresses(memoriesArray.getJSONObject(i), mem);
                memUnits.add(mappedMem);
            } catch (MappingException me) {
                // nop
            }
            if (!memoryDict.containsKey(mem.getClass())) {
                memoryDict.put(mem.getClass(), new ArrayList<MemoryUnit>());
            }
            memoryDict.get(mem.getClass()).add(mem);
        }
        MemoryMapper mapper = new MemoryMapper(memUnits);
        memoryDict.put(mapper.getClass(), new ArrayList<MemoryUnit>());
        memoryDict.get(mapper.getClass()).add(mapper);

        return memoryDict;
    }

    private MemoryUnit buildMemoryUnit(JSONObject memoryToken) {
        String type = parseJSONStringField(memoryToken, "type");
        Integer length = parseJSONIntField(memoryToken, "length");
        Integer wordSize = parseJSONIntField(memoryToken, "wordSize");

        int[] init = null;
        try {
            init = readInitFile(memoryToken.getJSONObject("initFile"));
        } catch (Exception e) {
        }

        MemoryUnit mem;
        MemoryUnitFactory memUnitFactory = new MemoryUnitFactory();

        if (length != null || init != null) {
            mem =
                    memUnitFactory.createMemoryUnit(
                            type, length == null ? init.length : length, wordSize == null ? 4 : wordSize);
        } else if (wordSize != null) {
            mem = memUnitFactory.createMemoryUnit(type, 0, wordSize);
        } else {
            mem = memUnitFactory.createMemoryUnit(type);
        }

        if (init != null) {
            for (int i = 0; i < init.length; i++) {
                mem.setMemoryUnit(i * mem.getWordSize(), init[i]);
            }
        }

        return mem;
    }

    private MappedMemoryUnit mapMemoryToAddresses(JSONObject memoryToken, MemoryUnit mem) {
        Integer startAddr = parseJSONIntField(memoryToken, "startAddr");
        Integer endAddr = parseJSONIntField(memoryToken, "endAddr");
        Integer size = parseJSONIntField(memoryToken, "size");
        String bitmask = parseJSONStringField(memoryToken, "bitmask");
        String name = parseJSONStringField(memoryToken, "name");

        MappedMemoryUnit mappedMem;
        if (startAddr != null) {
            if (endAddr != null) {
                mappedMem = new MappedMemoryUnit(mem, startAddr, endAddr, name);
            } else if (size != null) {
                mappedMem = new MappedMemoryUnit(mem, startAddr, startAddr + size - 1, name);
            } else {
                mappedMem = new MappedMemoryUnit(mem, startAddr, name);
            }
        } else if (bitmask != null) {
            mappedMem = new MappedMemoryUnit(mem, bitmask, name);
        } else {
            throw new MappingException("MappedMemoryUnit requires either startAddr or bitmask");
        }
        return mappedMem;
    }

    private int[] readInitFile(JSONObject initFileObj) throws IOException {
        String path;
        try {
            path = initFileObj.getString("filepath");
        } catch (Exception e) {
            throw new IllegalArgumentException("One of your initFile objects doesn't have a filepath");
        }
        String format;
        try {
            format = initFileObj.getString("format");
        } catch (Exception e) {
            format = "hex";
        }
        int baseNum = parseFormat(format);
        return parseInitData(path, baseNum);
    }

    /**
     * {"hex": 16, "dec": 10, "bin": 2} For any other string, also return 16 (assume hex). Doesn't use
     * a Dictionary like the original code, but same behavior.
     *
     * @param format
     * @return
     */
    private static int parseFormat(String format) {
        if (format.equals("dec")) {
            return 10;
        } else if (format.equals("bin")) {
            return 2;
        } else {
            return 16;
        }
    }

    /**
     * Needs to return array of unsigned integers for bmem and smem and array of signed integers for
     * imem and dmem.
     *
     * @param path
     * @param baseNum
     * @return
     */
    private int[] parseInitData(String path, int baseNum) throws IOException {
        // ArrayList is terrible for runtime, but it's fine since it's a one-time cost at startup
        // (copium)
        List<Integer> data = new ArrayList<>();
        File file = new File(basePath + "/" + path);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while ((line = br.readLine()) != null) {
            line = cleanLine(line);
            if (line.length() > 0) {
                // It is necessary to use parseUnsignedInt over parseInt because parseInt("8fbf0004", 16)
                // doesn't work
                // parseUnsignedInt("8fbf0004", 16) will return a negative number, for imem and dmem, as
                // expected
                // But parseUnsignedInt("f00", 16) returns +3840, as expected for smem and bmem
                // Overall, I believe parseUnsignedInt works as expected in all cases
                data.add(Integer.parseUnsignedInt(line, baseNum));
            }
        }
        return convertListToArr(data);
    }

    private static String cleanLine(String line) {
        int index = line.indexOf("//");
        if (index >= 0) {
            line = line.substring(0, index);
        }
        line = line.trim().replace("_", "");
        return line;
    }

    private static int[] convertListToArr(List<Integer> data) {
        int[] res = new int[data.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = data.get(i);
        }
        return res;
    }

    public Mips getMips() {
        return this.mips;
    }
}
