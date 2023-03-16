package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

class JnzInstructionTest {
  private Machine machine;
  private Registers registers;

  @BeforeEach
  void setUp() {
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
    // ...
  }

  @AfterEach
  void tearDown() {
    machine = null;
    registers = null;
  }

  @Test
  void givenRegisterSetToZero_whenExecutingJnz_thenContinueSequentialExecution() {
    // Set source register to 0
    registers.set(EAX, 0);
    // Execute JnzInstruction
    Instruction instruction = new JnzInstruction(null, EAX, "testLabel");
    // Assert programCounter is equal to NORMAL_PROGRAM_COUNTER_UPDATE
    Assertions.assertEquals(instruction.getOpcode(), "add");
    // Construct next instruction
    // Instruction nextInstruction = new MovInstruction(null, EBX, 2)
    // Assert next instruction is equal
    // Assertions.assertEquals(program.get(programCounter), nextInstruction);
  }

}
