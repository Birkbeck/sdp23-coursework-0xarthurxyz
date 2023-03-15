package sml.instruction;

import sml.Instruction;
import sml.RegisterName;

public class SubInstruction extends Instruction {
    private final RegisterName result;
	private final RegisterName source;

	public static final String OP_CODE = "sub";

	/**
	 * Constructor: 
	 * 
	 * @param label
	 * @param result
	 * @param source
	 */
	public SubInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}
    
}
