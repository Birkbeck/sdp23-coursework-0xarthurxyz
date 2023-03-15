package sml;

// TODO: write a JavaDoc for the class

/**
 * Represents an abstract instruction.
 *
 * @author ...
 */
public abstract class Instruction {
	protected final String label;
	protected final String opcode;
	public static int NORMAL_PROGRAM_COUNTER_UPDATE = -1;

	// My TODO: Consider using an ENUM for the operations that are supported
	// My TODO: Consider adding names of 8 registers that are supported

	/**
	 * Constructor: an instruction with a label and an opcode
	 * (opcode must be an operation of the language)
	 *
	 * @param label optional label (can be null)
	 * @param opcode operation name
	 */
	// My TODO: Consider running string validation to confirm label is legal
	public Instruction(String label, String opcode) {
		this.label = label;
		this.opcode = opcode;
	}

	// My todo: add javadoc
	public String getLabel() {
		return label;
	}

	// My todo: add javadoc
	public String getOpcode() {
		return opcode;
	}

	// My TODO: Add javadoc
	protected String getLabelString() {
		return (getLabel() == null) ? "" : getLabel() + ": ";
	}

	/**
	 * Executes the instruction in the given machine.
	 *
	 * @param machine the machine the instruction runs on
	 * @return the new program counter (for jump instructions)
	 *          or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *          the instruction with the next address is to be executed
	 */
	public abstract int execute(Machine machine);

	/**
	 * Returns a formatted String representation of the given object.
	 * 
	 * This is an abstract method, which means it does not have a method body defined in this
	 * abstract class. Instead the method body is defined in each respective subclass that 
	 * extends this abstract class.
	 * 
	 * @return 	Nicely formatted String representation of the object.
	 */
	@Override
	public abstract String toString();

	/**
	 * Evaluates whether some other object is "equal to" this one.
	 * 
	 * This is an abstract method, which means it does not have a method body defined in this
	 * abstract class. Instead the method body is defined in each respective subclass that 
	 * extends this abstract class.
	 * 
	 * @param   o   the reference object to compare
	 * @return	{@code true} if this object is the same as the {@code o} argument, {@code false}
	 * 			otherwise.
	 */
	@Override
	public abstract boolean equals(Object o);

	/**
	 * Returns a hash code value for this object.
	 * 
	 * This is an abstract method, which means it does not have a method body defined in this
	 * abstract class. Instead the method body is defined in each respective subclass that 
	 * extends this abstract class.
	 * 
	 * @return	a hash code value for this object.
	 */
	@Override
	public abstract int hashCode();
}
