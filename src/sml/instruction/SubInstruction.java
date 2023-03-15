package sml.instruction;

import sml.Instruction;
import sml.RegisterName;

public class SubInstruction extends Instruction {
    private final RegisterName result;
	private final RegisterName source;

	public static final String OP_CODE = "sub";

	/**
	 * Constructor: Instantiates a subtraction instruction given two register names.
	 * 
	 * @param label		optional name given to this instruction; label name can be used to jump to
	 * 					this instruction from other instructions.
	 * @param result	name of the register in which the result of the subtraction will be stored;
	 * 					also name of the register whose value will be used in the subtraction.
	 * @param source	name of the register whose value will be used in the subtraction.
	 */
	public SubInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}
    
}
