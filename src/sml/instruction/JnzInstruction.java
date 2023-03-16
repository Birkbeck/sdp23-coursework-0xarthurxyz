package sml.instruction;

import sml.Instruction;
import sml.RegisterName;

// TODO: write a JavaDoc for the class
/**
 * @author
 */
public class JnzInstruction extends Instruction {
	// My TODO: Add javadoc
	private final RegisterName source;
  // My TODO: Add javadoc
	private final String label;
	// My TODO: Add javadoc
	public static final String OP_CODE = "jnz";
}
