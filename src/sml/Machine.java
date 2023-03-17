package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;

/**
 * Represents the machine, the context in which programs run.
 * 
 * <p>
 * An instance contains 8 registers and methods to access and change them.
 * 
 * @author Arthur Gousset
 */

/**
 * This class defines all relevant methods and holds all relevant data
 * to execute from a plaintext file into internal form.
 * 
 * <p>
 * This class is used to parse instructions from a plaintext file and initiate a
 * machine ready to execute the program.
 *
 * @author Arthur Gousset
 */
public final class Machine {
	/**
	 * This fields represents the set of labels that can be used to jump across
	 * instructions
	 * in the program.
	 */
	private final Labels labels = new Labels();
	/**
	 * This field represents the instructions that can be executed by the machine.
	 */
	private final List<Instruction> program = new ArrayList<>();
	/**
	 * This field represents the logical memory of the machine.
	 */
	private final Registers registers;
	/**
	 * The program counter which contains the index in the program
	 * of the next instruction to be executed.
	 */
	private int programCounter = 0;

	/**
	 * Constructor: Instantiates a machine object with a specific state.
	 * 
	 * @param registers the set of register and their values with which to
	 *                  instantiate the machine
	 */
	public Machine(Registers registers) {
		this.registers = registers;
	}

	/**
	 * Executes the instructions stored in {@code program}, beginning at instruction
	 * 0.
	 * Precondition: the program and its labels have been stored properly.
	 */
	public void execute() {
		programCounter = 0;
		registers.clear();
		while (programCounter < program.size()) {
			Instruction ins = program.get(programCounter);
			int programCounterUpdate = ins.execute(this);
			programCounter = (programCounterUpdate == NORMAL_PROGRAM_COUNTER_UPDATE)
					? programCounter + 1
					: programCounterUpdate;
		}
	}

	static final Map<String, Class<?>> OPCODE_MAP = Map.of(
			"add", sml.instruction.AddInstruction.class,
			"sub", sml.instruction.SubInstruction.class,
			"mul", sml.instruction.MulInstruction.class,
			"div", sml.instruction.DivInstruction.class,
			"mov", sml.instruction.MovInstruction.class,
			"out", sml.instruction.OutInstruction.class,
			"jnz", sml.instruction.JnzInstruction.class);

	public Labels getLabels() {
		return this.labels;
	}

	public List<Instruction> getProgram() {
		return this.program;
	}

	public Registers getRegisters() {
		return this.registers;
	}

	/**
	 * String representation of the program under execution.
	 *
	 * @return pretty formatted version of the code.
	 */
	@Override
	public String toString() {
		return program.stream()
				.map(Instruction::toString)
				.collect(Collectors.joining("\n"));
	}

	/**
	 * Compares the state and type of this object to that of another object.
	 * 
	 * <p>
	 * The state of this object is defined by the fields of this object, which is
	 * why they are used to calculate unique hash codes for this class.
	 * 
	 * @param o an object to compare this object to.
	 * @return {@code true} if the state and type of the objects is equal,
	 *         {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		// Uses `instanceof` pattern matching.
		if (o instanceof Machine other) {
			// The binding variable `other` can be used in this scope
			// because the `instanceof` predicate evaluates to true in this block.
			return Objects.equals(this.labels, other.labels)
					&& Objects.equals(this.program, other.program)
					&& Objects.equals(this.registers, other.registers)
					&& this.programCounter == other.programCounter;
		}
		return false;
	}

	/**
	 * Produces a unique hash code for every possible state of this object.
	 * 
	 * <p>
	 * The state of this object is defined by the fields of this object, which is
	 * why they are used to calculate unique hash codes for this class.
	 * 
	 * @return a unique hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(labels, program, registers, programCounter);
	}
}
