package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

class AddInstructionTest {
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
  void givenInstruction_whenInstantiating_thenOpcodeCorrect() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    Assertions.assertEquals(instruction.getOpcode(), "add");
  }

  @Test
  void givenSimpleValues_whenAdding_thenResultIsCorrect() {
    registers.set(EAX, 5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(11, machine.getRegisters().get(EAX));
  }

  @Test
  void givenPositiveAndNegativeValues_whenAdding_thenResultIsCorrect() {
    registers.set(EAX, -5);
    registers.set(EBX, 6);
    Instruction instruction = new AddInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(1, machine.getRegisters().get(EAX));
  }

  /**
   * Unit test for {@code equals()} method
   */
  @Test
  void givenEquivalentInstructions_whenEquals_thenTrue() {
    registers.set(EAX, 6);
    registers.set(EBX, 5);
    // Instantiates first instruction
    Instruction firstInstruction = new AddInstruction(null, EAX, EBX);
    // Instantiates second instruction
    Instruction SecondInstruction = new AddInstruction(null, EAX, EBX);
    // Compares equivalent instructions
    Assertions.assertTrue(firstInstruction.equals(SecondInstruction));
  }

  /**
   * Unit test for {@code equals()} method and null value
   */
  @Test
  void givenNullAndAnInstruction_whenEquals_thenFalse() {
    // Instantiates an instruction
    Instruction firstInstruction = new AddInstruction(null, EAX, EBX);
    // Compares instruction with null
    Assertions.assertFalse(firstInstruction.equals(null));
  }

  /**
   * Unit test for reflexivity in {@code equals()} method
   */
  @Test
  void givenSameObject_whenEquals_thenTrue() {
    // Instantiates an instruction
    Instruction firstInstruction = new AddInstruction(null, EAX, EBX);
    // Compares instruction with itself
    Assertions.assertTrue(firstInstruction.equals(firstInstruction));
  }

  @Test
  void givenEquivalentInstructions_whenHashCodes_thenEquivalent() {
    registers.set(EAX, 6);
    registers.set(EBX, 5);
    // Instantiates first instruction
    Instruction firstInstruction = new AddInstruction(null, EAX, EBX);
    // Instantiates second instruction
    Instruction SecondInstruction = new AddInstruction(null, EAX, EBX);
    // Compares equivalent instructions
    Assertions.assertEquals(firstInstruction.hashCode(), SecondInstruction.hashCode());
  }
}