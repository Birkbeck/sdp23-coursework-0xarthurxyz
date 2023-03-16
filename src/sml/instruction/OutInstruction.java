package sml.instruction;

import java.util.Objects;

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

  /** 
	 * Prints the value of a register to the console.
	 * 
	 * @param m	Machine object with a given set of registers
	 * @return 	the new program counter (for jump instructions)
	 * 			or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 * 			the instruction with the next address is to be executed
	 */
	@Override
	public int execute(Machine m) {
    System.out.println( m.getRegisters().get(source) );
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

  // My TODO: Add javadoc
	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + source;
	}

	// My TODO: Add javadoc
	// My TODO: Implement equals method
	@Override
	public boolean equals(Object o) {
		// Uses `instanceof` pattern matching.
		if (o instanceof OutInstruction other) {
			// The binding variable `other` can be used in this scope
			// because the `instanceof` predicate evaluates to true in this block.
			return Objects.equals(this.source, other.source);
		}
		return false;
	}
	
	// My TODO: Add javadoc
	// My TODO: Implement and override hashCode method
	@Override
	public int hashCode() {
		// temporary implementation to stop compilation errors
		return  (int) ( java.lang.Math.random() * 1000 );
	}
}
