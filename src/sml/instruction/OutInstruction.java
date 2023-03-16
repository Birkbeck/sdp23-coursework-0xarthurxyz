package sml.instruction;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;

// TODO: write a JavaDoc for the class
/**
 * @author
 */
public class OutInstruction extends Instruction {
  // My TODO: Add javadoc
  private final RegisterName source;
  // My TODO: Add javadoc
  public static final String OP_CODE = "out";

  /**
	 * Constructor: Instantiates an out instruction given a register name.
	 * 
	 * @param label		optional name given to this instruction; label name can be used to jump to 
	 * 					this instruction from other instructions.
	 * @param source	name of the register whose value will be printed to the console
	 */
	public OutInstruction(String label, RegisterName source) {
		super(label, OP_CODE);
		this.source = source;
	}
}
