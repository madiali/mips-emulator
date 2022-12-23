package mips;

import mips.instructions.IType.*;
import mips.instructions.Instruction;
import mips.instructions.JType.*;
import mips.instructions.RType.*;

public class InstructionFactory {
  // A comment from the original code, but all these were already implemented in the original
  // TODO: Add new instructions (ANDI, XORI, ADDU)

  /**
   * The original code assigns numeric values to enum elements, which isn't directly possible using
   * = in Java I could not get enums to work, so I'm just hardcoding it Also, 0b100011 is recognized
   * as positive 35, not a negative number. Yay! - Jesse
   */

  // Java has no const keyword, using final instead
  private final int SIX_MASK = 0b111111;

  private final int FIVE_MASK = 0b11111;
  private final int SIXTEEN_MASK = 0b1111111111111111;
  private final int TWENTY_SIX_MASK = 0b11111111111111111111111111;

  /**
   * The original has no constructor, so ours doesn't have a constructor either (though an
   * InstructionFactory is constructed in InstructionMemory.java)
   */

  /**
   * This is modified from the original code!! This method does not throw an exception when
   * instruction has a bad opcode or func bits, instead returns null on failure. When using this
   * method, must check that it doesn't return null.
   *
   * @param instruction
   * @return Instruction if successful, null if not successful
   */
  public Instruction createInstruction(int instruction) {
    int func = instruction & SIX_MASK;
    int shamt = (instruction >> 6) & FIVE_MASK;
    int rd = (instruction >> 11) & FIVE_MASK;
    int rt = (instruction >> 16) & FIVE_MASK;
    int rs = (instruction >> 21) & FIVE_MASK;
    int op = (instruction >> 26) & SIX_MASK;
    int immediate = instruction & SIXTEEN_MASK;
    int address = instruction & TWENTY_SIX_MASK;

    if (op == 0) {
      switch (func) {
        case 0b100000:
          return new AddInstruction(rd, rs, rt);
        case 0b100010:
          return new SubInstruction(rd, rs, rt);
        case 0b100100:
          return new AndInstruction(rd, rs, rt);
        case 0b100101:
          return new OrInstruction(rd, rs, rt);
        case 0b100110:
          return new XorInstruction(rd, rs, rt);
        case 0b100111:
          return new NorInstruction(rd, rs, rt);
        case 0b101010:
          return new SltInstruction(rd, rs, rt);
        case 0b101011:
          return new SltuInstruction(rd, rs, rt);
        case 0b000000:
          return new SllInstruction(rd, rt, shamt);
        case 0b000100:
          return new SllvInstruction(rd, rt, rs);
        case 0b000010:
          return new SrlInstruction(rd, rt, shamt);
        case 0b000110:
          return new SrlvInstruction(rd, rt, rs);
        case 0b000011:
          return new SraInstruction(rd, rt, shamt);
        case 0b000111:
          return new SravInstruction(rd, rt, rs);
        case 0b001000:
          return new JrInstruction(rs);
        case 0b100001:
          return new AdduInstruction(rd, rs, rt);
        case 0b001001:
          return new JalrInstruction(rs);
      }
    } else {
      switch (op) {
        case 0b100011:
          return new LwInstruction(rt, rs, immediate);
        case 0b101011:
          return new SwInstruction(rt, rs, immediate);
        case 0b001000:
          return new AddiInstruction(rt, rs, immediate);
        case 0b001001:
          return new AddiuInstruction(rt, rs, immediate);
        case 0b001100:
          return new AndiInstruction(rt, rs, immediate);
        case 0b001010:
          return new SltiInstruction(rt, rs, immediate);
        case 0b001011:
          return new SltiuInstruction(rt, rs, immediate);
        case 0b001101:
          return new OriInstruction(rt, rs, immediate);
        case 0b001111:
          return new LuiInstruction(rt, immediate);
        case 0b000100:
          return new BeqInstruction(rs, rt, immediate);
        case 0b000101:
          return new BneInstruction(rs, rt, immediate);
        case 0b000010:
          return new JInstruction(address);
        case 0b000011:
          return new JalInstruction(address);
        case 0b001110:
          return new XoriInstruction(rt, rs, immediate);
      }
    }
    // We need this return statement because the method returns Instruction
    // But if we throw a UnknownInstructionException here, then this return statement may be
    // unreachable, and that causes a compiler error
    // We also can't modify the switch statements with default: throw new
    // UnknownInstructionException for the same reason
    return null;
  }
}
