package sml.instruction;

import java.util.Objects;

import sml.Instruction;
import sml.Machine;
import sml.RegisterName;
import sml.Registers.Register;

public class SubInstruction extends Instruction {
	// My TODO: Add javadoc
  private final RegisterName result;
	// My TODO: Add javadoc
	private final RegisterName source;
	// My TODO: Add javadoc
	public static final String OP_CODE = "sub";

	/**
	 * Constructor: Instantiates a subtraction instruction given two register names.
	 * 
	 * @param label		optional name given to this instruction; label name can be used to jump to
	 * 					this instruction from other instructions.
	 * @param result	name of the register in which the result of the subtraction will be stored;
	 * 					also name of the register whose value will be used in the subtraction.
	 * @param source	name of the register whose value will be used in the subtraction.
	 */
	public SubInstruction(String label, RegisterName result, RegisterName source) {
		super(label, OP_CODE);
		this.result = result;
		this.source = source;
	}

	/** 
	 * Performs a state transition on a given machine.
	 * 
	 * <p> Subtracts the value of a register from the value of another register 
	 * and stores that result in a register.
	 * 
	 * @param m	Machine object with a given set of registers 
	 * @return 	the new program counter (for jump instructions)
	 * 			or NORMAL_PROGRAM_COUNTER_UPDATE to indicate that
	 * 			the instruction with the next address is to be executed
	 */
	@Override
	public int execute(Machine m) {
		int value1 = m.getRegisters().get(result);
		int value2 = m.getRegisters().get(source);
		m.getRegisters().set(result, value1 - value2);
		return NORMAL_PROGRAM_COUNTER_UPDATE;
	}

	// My TODO: Add javadoc
	@Override
	public String toString() {
		return getLabelString() + getOpcode() + " " + result + " " + source;
	}

	// My TODO: Add javadoc
	// My TODO: Implement equals method
	@Override
	public boolean equals(Object o) {
		// Uses `instanceof` pattern matching.
		if (o instanceof SubInstruction other) {
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
	 * <p> The state of this object is defined by the fields of this object, which is why they 
	 * are used to calculate unique hash codes for this class.
	 * 
	 * @return	a unique hash code
	 */
	@Override
	public int hashCode() {
		int hash = 7; // Arbitrary non-zero constant
		hash = 31 * hash + ((Register) this.result).hashCode(); // Hash code of this Enum constant
		hash = 31 * hash + ((Register) this.source).hashCode(); // Hash code of this Enum constant
		hash = 31 * hash + OP_CODE.hashCode(); // // Hash code of this String
		return hash;
	}
}
