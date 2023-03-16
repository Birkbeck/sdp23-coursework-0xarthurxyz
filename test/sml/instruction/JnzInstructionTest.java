package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Instruction.NORMAL_PROGRAM_COUNTER_UPDATE;
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
  void givenRegisterEqualsZero_whenExecutingJnz_thenContinueSequentialExecution() {
    // Set source register to zero
    registers.set(EAX, 0);
    Instruction instruction = new JnzInstruction(null, EAX, "anyLabel");
    // Assert programCounter is equal to NORMAL_PROGRAM_COUNTER_UPDATE
    Assertions.assertEquals(NORMAL_PROGRAM_COUNTER_UPDATE, instruction.execute(machine));
  }

  @Test
  void givenRegisterNonZero_whenExecutingJnz_thenContinueAtLabel() {
    // Set source register to some non-zero value
    registers.set(EAX, 1);
    // Set a label point to the instruction stored at index 10
    machine.getLabels().addLabel("someLabel", 10); 
    Instruction instruction = new JnzInstruction(null, EAX, "someLabel");
    // Assert programCounter is equal to NORMAL_PROGRAM_COUNTER_UPDATE
    Assertions.assertEquals(10, instruction.execute(machine));
  }

  @Test
  void givenEquivalentInstructions_whenComparing_thenTrue() {
    // Instantiates first instruction
    Instruction firstInstruction = new JnzInstruction(null, EAX, "f3");
    // Instantiates second instruction
    Instruction SecondInstruction = new JnzInstruction(null, EAX, "f3");
    // Compares equivalent instructions
    Assertions.assertTrue(firstInstruction.equals(SecondInstruction));
  }

  @Test
  void givenEquivalentInstructions_whenHashCodes_thenEquivalent() {
    // Instantiates first instruction
    Instruction firstInstruction = new JnzInstruction(null, EAX, "f3");
    // Instantiates second instruction
    Instruction SecondInstruction = new JnzInstruction(null, EAX, "f3");
    // Compares equivalent instructions
    Assertions.assertEquals(firstInstruction.hashCode(), SecondInstruction.hashCode());
  }

}
