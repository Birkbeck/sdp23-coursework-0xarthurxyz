package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * Provides the unit tests with access to the enumeration {@code Registers} defined
 * in the {@code Register} class.
 */
import static sml.Registers.Register.*;

class SubInstructionTest {
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
    Instruction instruction = new SubInstruction(null, EAX, EBX);
    Assertions.assertEquals(instruction.getOpcode(), "sub");
  }

  @Test
  void givenSimpleValues_whenSubtracting_thenResultIsCorrect() {
    registers.set(EAX, 6);
    registers.set(EBX, 5);
    Instruction instruction = new SubInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(1, machine.getRegisters().get(EAX));
  }

  @Test
  void givenEquivalentInstructions_whenComparing_thenTrue() {
    registers.set(EAX, 6);
    registers.set(EBX, 5);
    // Instantiates first instruction
    Instruction firstInstruction = new SubInstruction(null, EAX, EBX);
    // Instantiates second instruction
    Instruction SecondInstruction = new SubInstruction(null, EAX, EBX);
    // Compares equivalent instructions
    Assertions.assertTrue(firstInstruction.equals(SecondInstruction));
  }

  @Test
  void givenEquivalentInstructions_whenHashCodes_thenEquivalent() {
    registers.set(EAX, 6);
    registers.set(EBX, 5);
    // Instantiates first instruction
    Instruction firstInstruction = new SubInstruction(null, EAX, EBX);
    // Instantiates second instruction
    Instruction SecondInstruction = new SubInstruction(null, EAX, EBX);
    // Compares equivalent instructions
    Assertions.assertEquals(firstInstruction.hashCode(), SecondInstruction.hashCode());
  }
}
