package sml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;


enum supportedOpcodes implements OpcodeClassName {
  add {
    @Override
    public String className() {
      return "AddInstruction";
    }
  }, 

  sub {
    @Override
    public String className() {
      return "SubInstruction";
    }
  }, 
  
  mul {
    @Override
    public String className() {
      return "MulInstruction";
    }
  },
  
  div {
    @Override
    public String className() {
      return "DivInstruction";
    }
  },

  mov {
    @Override
    public String className() {
      return "MovInstruction";
    }
  },

  out {
    @Override
    public String className() {
      return "OutInstruction";
    }
  },
  
  jnz {
    @Override
    public String className() {
      return "JnzInstruction";
    }
  }
}

/**
 * Represents the machine, the context in which programs run.
 * 
 * <p> An instance contains 8 registers and methods to access and change them.
 * @author Arthur Gousset
 */
public final class Machine {
	// My TODO: add javadoc
	private final Labels labels = new Labels();
	// My TODO: add javadoc
	private final List<Instruction> program = new ArrayList<>();
	// My TODO: add javadoc
	private final Registers registers;

	// The program counter; it contains the index (in program)
	// of the next instruction to be executed.
	private int programCounter = 0;

	// Constructor
	public Machine(Registers registers) {
		this.registers = registers;
	}

	// My TODO: add better javadoc
	/**
	 * Execute the program in program, beginning at instruction 0.
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

	public static String getOpcodeClassName(supportedOpcodes op) {
  	return op.className();
  }

	// My TODO: add javadoc
	public Labels getLabels() {
		return this.labels;
	}

	// My TODO: add javadoc
	public List<Instruction> getProgram() {
		return this.program;
	}

	// My TODO: add javadoc
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

	// My TODO: consider removing comment below
	// TODO: use pattern matching for instanceof
	// https://docs.oracle.com/en/java/javase/14/language/pattern-matching-instanceof-operator.html
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

	// My TODO: Test hashCode implementation of Machine.java
	// My TODO: add javadoc
	@Override
	public int hashCode() {
		return Objects.hash(labels, program, registers, programCounter);
	}
}
