package sml.instruction;

import java.util.Objects;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers.Register;

/**
 * This class represents a move instruction given a register name and an integer
 * value.
 * 
 * <p>
 * This class, both, defines all relevant methods and holds all relevant data
 * to perform the appropriate state transition on a machine.
 * 
 * @author Arthur Gousset
 */
public class MovInstruction extends Instruction {
	private final RegisterName result;
	private final int value;
	public static final String OP_CODE = "mov";

	/**
	 * Constructor: Instantiates a move instruction given a register name and an
	 * integer value.
	 * 
	 * @param label  optional name given to this instruction; label name can be used
	 *               to jump to this instruction from other instructions.
	 * @param result name of the register in which the integer value will be stored
	 * @param value  integer value that will be stored in the register
	 */
	public MovInstruction(String label, RegisterName result, int value) {
		super(label, OP_CODE);
		this.result = result;
		this.value = value;
	}

	/**
	 * Performs a state transition on a given machine.
	 * 
	 * <p>
	 * Stores the value of an integer in a register.
	 * 
	 * @param m Machine object with a given set of registers
	 * @return the new program counter (for jump instructions)
	 *         or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *         the instruction with the next address is to be executed
	 */
	@Override
	public int execute(Machine m) {
		m.getRegisters().set(result, value);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	/**
	 * Returns a formatted String representation of this object.
	 * 
	 * @return Nicely formatted String representation of the object.
	 */
	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + value;
	}

	/**
	 * Evaluates whether an object is equal to this one by comparing
	 * the state and type of this object to that of the other object.
	 * 
	 * <p>
	 * The state of this object is defined by the fields of this object, which is
	 * why they
	 * are used to calculate unique hash codes for this class.
	 * 
	 * @param o the reference object to compare
	 * @return {@code true} if the state and type of the objects is equal,
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		// Uses `instanceof` pattern matching.
		if (o instanceof MovInstruction other) {
			// The binding variable `other` can be used in this scope
			// because the `instanceof` predicate evaluates to true in this block.
			return Objects.equals(this.result, other.result)
					&& Objects.equals(this.value, other.value);
		}
		return false;
	}

	/**
	 * Produces a unique hash code for every possible state of this object.
	 * 
	 * <p>
	 * The state of this object is defined by the fields of this object, which is
	 * why they
	 * are used to calculate unique hash codes for this class.
	 * 
	 * @return a unique hash code
	 */
	@Override
	public int hashCode() {
		int hash = 7; // Arbitrary non-zero constant
		hash = 31 * hash + ((Register) result).hashCode(); // Hash code of this Enum constant
		hash = 31 * hash + value;
		hash = 31 * hash + OP_CODE.hashCode(); // // Hash code of this String
		return hash;
	}
}
