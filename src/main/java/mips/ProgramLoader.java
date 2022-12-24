package mips;

/**
 * https://stackoverflow.com/questions/2591098/how-to-parse-json-in-java
 * This link has some discussion about which library to use to parse JSON in Java
 * org.json seems to match the original code closely, so I decided on that
 * I omit the ParseRequiredNumber method since it's only used one time
 * Also, it seems that C#'s JSON stuff will return null if a field is not found, whereas org.json will throw an Exception.
 */


import mips.instructions.Instruction;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import org.json.*;

public class ProgramLoader {
  private Mips mips;
  private final String basePath;

  public ProgramLoader(File file) throws IOException {
    // If file is in /MIPS_Emulator/, the below returns /MIPS_Emulator (without the trailing /)
    // Works the same as FileInfo's .DirectoryName
    this.basePath = file.getParent();
    this.mips = loadMipsFromFile(file);
  }

  private Mips loadMipsFromFile(File file) throws IOException {
    // TODO: We have not yet implemented sound but will do so soon (TM)
    // SoundModule.waveOut.stop();

    String json = Files.readString(file.toPath());
    JSONObject project = new JSONObject(json);

    int pc;
    try {
      pc = project.getInt("programCounter");
    } catch (Exception e) {
      throw new IllegalArgumentException("Expected field programCounter not found in project JSON");
    }
    JSONArray memoriesArray = project.getJSONArray("memories");
    Map<Class, List<MemoryUnit>> memDict = buildMemoryUnits(memoriesArray);
    String name = null;
    try {
      name = project.getString("projectName");
    } catch (Exception e) {
      name = file.getName();
    }
    float desiredClockSpeed = 0;
    try {
      // Assume clockSpeed is float, not String
      desiredClockSpeed = project.getFloat("clockSpeed");
    } catch (Exception e) { }

    // registers parameter is optional in the original code, passing in null
    // Mips.registers will be a new Registers() object
    return new Mips(pc, memDict, null, name, desiredClockSpeed);
  }

  private int parseNumber(String token) {
    token = token.replace("_", "");
    if (token.startsWith("0x")) {
      return Integer.parseInt(token.substring(2), 16);
    }
    if (token.startsWith("0b")) {
      return Integer.parseInt(token.substring(2), 2);
    }
    return Integer.parseInt(token, 10);
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
    String type = null;
    try {
      type = memoryToken.getString("type");
    } catch (Exception e) { }

    Integer length = null;
    try {
      length = memoryToken.getInt("length");
    } catch (Exception e) { }

    Integer wordSize = null;
    try {
      // Assuming wordSize would be an int, not a String
      wordSize = memoryToken.getInt("wordSize");
    } catch (Exception e) { }

    int[] init = null;
    try {
      init = readInitFile(memoryToken.getJSONObject("initFile"));
    } catch (Exception e) { }

    MemoryUnit mem = null;
    MemoryUnitFactory memUnitFactory = new MemoryUnitFactory();

    if (length != null || init != null) {
      mem = memUnitFactory.createMemoryUnit(type, length == null ? init.length : length, wordSize == null ? 4 : wordSize);
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
    // startAddr and endAddr are always Strings
    Integer startAddr = null;
    try {
      startAddr = parseNumber(memoryToken.getString("startAddr"));
    } catch (Exception e) { }

    Integer endAddr = null;
    try {
      endAddr = parseNumber(memoryToken.getString("endAddr"));
    } catch (Exception e) { }

    Integer size = null;
    try {
      // size may be an int, not a String, this may be incorrect logic
      size = parseNumber(memoryToken.getString("size"));
    } catch (Exception e) { }

    String bitmask = null;
    try {
      bitmask = memoryToken.getString("bitmask");
    } catch (Exception e) { }

    String name = null;
    try {
      name = memoryToken.getString("name");
    } catch (Exception e) { }

    MappedMemoryUnit mappedMem = null;
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
      throw new IllegalArgumentException("One of your initFile tokens doesn't have a filepath");
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
   * {"hex": 16, "dec": 10, "bin": 2}
   * For any other string, also return 16 (assume hex). Doesn't use a Dictionary like the original code, but same behavior.
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
   * Needs to return array of unsigned integers for bmem and smem and array of signed integers for imem and dmem.
   * @param path
   * @param baseNum
   * @return
   */
  private int[] parseInitData(String path, int baseNum) throws IOException {
    // This is probably terrible for runtime but it's fine
    List<Integer> data = new ArrayList<>();
    boolean signed = path.toLowerCase().contains("imem") || path.toLowerCase().contains("dmem");
    File file = new File(basePath + "/" + path);
    FileReader fr = new FileReader(file);
    BufferedReader br = new BufferedReader(fr);
    String line;
    while ((line = br.readLine()) != null) {
     line = cleanLine(line);
     if (line.length() > 0) {
       data.add(signed ? Integer.parseInt(line, baseNum) : Integer.parseUnsignedInt(line, baseNum));
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