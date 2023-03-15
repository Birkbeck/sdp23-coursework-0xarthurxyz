package sml.instruction;

import sml.Instruction;
import sml.RegisterName;

public class SubInstruction extends Instruction {
    private final RegisterName result;
	private final RegisterName source;

	public static final String OP_CODE = "sub";
    
}
