package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class

/**
 * @author
 */
public class DivInstruction extends Instruction {
  // My TODO: Add javadoc
  private final RegisterName result;
  // My TODO: Add javadoc
  private final RegisterName source;
  // My TODO: Add javadoc
  public static final String OP_CODE = "div";

  /**
	 * Constructor: Instantiates a division instruction given two register names.
	 * 
	 * @param label		optional name given to this instruction; label name can be used to jump to 
	 * 					      this instruction from other instructions.
	 * @param result	name of the register in which the result of the addition will be stored;
	 * 					      also name of the register whose value will be used in the addition.
	 * @param source	name of the register whose value will be used in the addition.
	 */
	public DivInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

  /** 
	 * Performs a state transition on a given machine.
	 * 
	 * <p> Divides the value of a register by the value of another register using
   * Java integer division and stores that result in a register.
	 * 
	 * @param m	Machine object with a given set of registers 
	 * @return 	the new program counter (for jump instructions)
	 * 			or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 * 			the instruction with the next address is to be executed
	 */
	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 / value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}
}
