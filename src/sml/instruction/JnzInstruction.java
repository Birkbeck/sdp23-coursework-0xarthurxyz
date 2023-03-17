package sml.instruction;

import java.util.Objects;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers.Register;

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

  /** 
	 * Performs a state transition on a given machine.
	 * 
	 * <p> Returns the index of the next instruction to be executed
	 * 
	 * @param m	Machine object with a given set of registers 
	 * @return 	the new program counter (for jump instructions)
	 * 			or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 * 			the instruction with the next address is to be executed
	 */
	@Override
	public int execute(Machine m) {
		try {
			// If the value stored at register `source` is not zero
			// then execute the instruction labeled `destinationLabel` next.
			if ( m.getRegisters().get(source) != 0 ) {
				// Returns the index of the next instruction to execute in the 
				// List<Instruction> `program` list.
				return m.getLabels().getAddress(destinationLabel);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    

    // If the value stored at register `source` is zero
    // then continue with the sequential execution of the program.
    return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

  // My TODO: Add javadoc
	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source + " " + destinationLabel;
	}
	
	// My TODO: Add javadoc
	// My TODO: Implement equals method
	@Override
	public boolean equals(Object o) {
		// Uses `instanceof` pattern matching.
		if (o instanceof JnzInstruction other) {
			// The binding variable `other` can be used in this scope
			// because the `instanceof` predicate evaluates to true in this block.
			return Objects.equals(this.source, other.source)
					&& Objects.equals(this.destinationLabel, other.destinationLabel);
		}
		return false;
	}
	
	/**
	 * Produces a unique hash code for every possible state of this object.
	 * 
	 * <p> The state of this object is defined by the fields of this object, which is why they 
	 * are used to calculate unique hash codes for this class.
	 * 
	 * @return	a unique hash code
	 */
	@Override
	public int hashCode() {
		int hash = 7; // Arbitrary non-zero constant
		hash = 31 * hash + ((Register) source).hashCode(); // Hash code of this Enum constant
		hash = 31 * hash + destinationLabel.hashCode(); // // Hash code of this String
		hash = 31 * hash + OP_CODE.hashCode(); // // Hash code of this String
		return hash;
	}
}
