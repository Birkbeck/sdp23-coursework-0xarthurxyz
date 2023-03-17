package sml.instruction;

import java.util.Objects;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers.Register;

/**
 * This class represents multiplication instruction given two register names.
 * 
 * <p>
 * This class, both, defines all relevant methods and holds all relevant data
 * to perform the appropriate state transition on a machine.
 * 
 * @author Arthur Gousset
 */
public class MulInstruction extends Instruction {
	// My TODO: Add javadoc
	private final RegisterName result;
	// My TODO: Add javadoc
	private final RegisterName source;
	// My TODO: Add javadoc
	public static final String OP_CODE = "mul";

	/**
	 * Constructor: Instantiates a multiplication instruction given two register
	 * names.
	 * 
	 * @param label  optional name given to this instruction; label name can be used
	 *               to jump to
	 *               this instruction from other instructions.
	 * @param result name of the register in which the result of the addition will
	 *               be stored;
	 *               also name of the register whose value will be used in the
	 *               addition.
	 * @param source name of the register whose value will be used in the addition.
	 */
	public MulInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	/**
	 * Performs a state transition on a given machine.
	 * 
	 * <p>
	 * Multiplies the value of a register by the value of another register
	 * and stores that result in a register.
	 * 
	 * @param m Machine object with a given set of registers
	 * @return the new program counter (for jump instructions)
	 *         or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 *         the instruction with the next address is to be executed
	 */
	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 * value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	/**
	 * Returns a formatted String representation of this object.
	 * 
	 * @return Nicely formatted String representation of the object.
	 */
	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
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
		if (o instanceof MulInstruction other) {
			// The binding variable `other` can be used in this scope
			// because the `instanceof` predicate evaluates to true in this block.
			return Objects.equals(this.result, other.result)
					&& Objects.equals(this.source, other.source);
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
		hash = 31 * hash + ((Register) source).hashCode(); // Hash code of this Enum constant
		hash = 31 * hash + OP_CODE.hashCode(); // // Hash code of this String
		return hash;
	}
}
