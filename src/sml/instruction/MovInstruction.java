package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class
/**
 * @author
 */
public class MovInstruction extends Instruction {
    // My TODO: Add javadoc
    private final RegisterName result;
    // My TODO: Add javadoc
	private final int value;
    // My TODO: Add javadoc
	public static final String OP_CODE = "mov";

    /**
	 * Constructor: Instantiates a move instruction given a register name and an integer value.
	 * 
	 * @param label		optional name given to this instruction; label name can be used to jump to 
	 * 					this instruction from other instructions.
	 * @param result	name of the register in which the integer value will be stored
	 * @param value 	integer value that  will be stored in the register
	 */
	public MovInstruction(String label, RegisterName result, int value) {
		super(label, OP_CODE);
		this.result = result;
		this.value = value;
	}

    /** 
	 * Performs a state transition on a given machine.
	 * 
	 * <p> Stores the value of an integer in a register.
	 * 
	 * @param m	Machine object with a given set of registers
	 * @return 	the new program counter (for jump instructions)
	 * 			or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 * 			the instruction with the next address is to be executed
	 */
	@Override
	public int execute(Machine m) {
		m.getRegisters().set(result, value);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}
}
