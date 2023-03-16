package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

class DivInstructionTest {
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
    registers.set(EAX, 100);
    registers.set(EBX, 2);
    Instruction instruction = new DivInstruction(null, EAX, EBX);
    Assertions.assertEquals(instruction.getOpcode(), "div");
  }

  @Test
  void givenSimpleValues_whenAdding_thenResultIsCorrect() {
    registers.set(EAX, 100);
    registers.set(EBX, 2);
    Instruction instruction = new DivInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(50, machine.getRegisters().get(EAX));
  }

  @Test
  void givenPositiveAndNegativeValues_whenAdding_thenResultIsCorrect() {
    registers.set(EAX, 100);
    registers.set(EBX, -2);
    Instruction instruction = new DivInstruction(null, EAX, EBX);
    instruction.execute(machine);
    Assertions.assertEquals(-50, machine.getRegisters().get(EAX));
  }

  @Test
  void givenEquivalentInstructions_whenComparing_thenTrue() {
    registers.set(EAX, 100);
    registers.set(EBX, 2);
    // Instantiates first instruction
    Instruction firstInstruction = new DivInstruction(null, EAX, EBX);
    // Instantiates second instruction
    Instruction SecondInstruction = new DivInstruction(null, EAX, EBX);
    // Compares equivalent instructions
    Assertions.assertTrue(firstInstruction.equals(SecondInstruction));
  }

  @Test
  void givenEquivalentInstructions_whenHashCodes_thenEquivalent() {
    registers.set(EAX, 100);
    registers.set(EBX, 2);
    // Instantiates first instruction
    Instruction firstInstruction = new DivInstruction(null, EAX, EBX);
    // Instantiates second instruction
    Instruction SecondInstruction = new DivInstruction(null, EAX, EBX);
    // Compares equivalent instructions
    Assertions.assertEquals(firstInstruction.hashCode(), SecondInstruction.hashCode());
  }
}
