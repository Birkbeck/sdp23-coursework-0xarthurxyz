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
	private final String destinationLabel;
	// My TODO: Add javadoc
	public static final String OP_CODE = "jnz";

  /**
	 * Constructor: Instantiates a "jump if not zero" instruction given a register name
   * and an label name to jump to.
	 * 
	 * @param label		optional name given to this instruction; label name can be used to jump to 
	 * 					this instruction from other instructions.
	 * @param source	name of the register whose value will be compared to 0.
	 * @param label	  name of the instruction to which the program should jump if the value at
   *          register {@code source} is equal to 0.
	 */
	public JnzInstruction(String label, RegisterName source, String destinationLabel) {
		super(label, OP_CODE);
		this.source = source;
		this.destinationLabel = destinationLabel;
	}
}
