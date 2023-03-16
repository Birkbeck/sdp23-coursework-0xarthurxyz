package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class OutInstructionTest {
  private Machine machine;
  private Registers registers;
  /**
   * The "standard" output stream is already open and ready to accept output data.
   * 
   * <p> This is used to capture the standard output stream and re-assign it after
   * each test completes to avoid conflicts across tests.
   * This is important because {@code System.out} is a static resource 
   * and thus shared across objects.
   */
  private final PrintStream standardOutput = System.out;
  /**
   * The {@code ByteArrayOutputStream} class implements an output stream in which the 
   * data is written into a byte array. The buffer automatically grows as data is written to it. 
   * 
   * <p> The data can be retrieved using {@code toString()}.
   */
  private final ByteArrayOutputStream output = new ByteArrayOutputStream();

  @BeforeEach
  void setUp() {
    // Captures the standard output for unit testing
    System.setOut(new PrintStream(output));

    machine = new Machine(new Registers());
    registers = machine.getRegisters();
    // ...
  }

  @AfterEach
  void tearDown() {
    // Re-assigns the previously captured standard output stream after
    // each test completes to avoid conflicts across tests.
    System.setOut(standardOutput);

    machine = null;
    registers = null;
  }

  @Test
  void givenInstruction_whenInstantiating_thenOpcodeCorrect() {
    registers.set(EAX, 5);
    Instruction instruction = new OutInstruction(null, EAX);
    Assertions.assertEquals(instruction.getOpcode(), "out");
    
  }

  @Test
  void givenSimpleValue_whenPrinting_thenConsoleOutputIsCorrect() {
    registers.set(EAX, 5);
    Instruction instruction = new OutInstruction(null, EAX);
    instruction.execute(machine);
    // Converts the (captured) standard output stream to a String using `toString()`
    // Then removes the new line that is added by `System.out.println()`
    Assertions.assertEquals( "5", output.toString().trim());
  }

  @Test
  void givenNegativeValue_whenPrinting_thenConsoleOutputIsCorrect() {
    registers.set(EAX, -5);
    Instruction instruction = new OutInstruction(null, EAX);
    instruction.execute(machine);
    // Converts the (captured) standard output stream to a String using `toString()`
    // Then removes the new line that is added by `System.out.println()`
    Assertions.assertEquals( "-5", output.toString().trim());
  }

  @Test
  void givenEquivalentInstructions_whenComparing_thenTrue() {
    registers.set(EAX, -5);
    // Instantiates first instruction
    Instruction firstInstruction = new OutInstruction(null, EAX);
    // Instantiates second instruction
    Instruction SecondInstruction = new OutInstruction(null, EAX);
    // Compares equivalent instructions
    Assertions.assertTrue(firstInstruction.equals(SecondInstruction));
  }

  @Test
  void givenEquivalentInstructions_whenHashCodes_thenEquivalent() {
    registers.set(EAX, -5);
    // Instantiates first instruction
    Instruction firstInstruction = new OutInstruction(null, EAX);
    // Instantiates second instruction
    Instruction SecondInstruction = new OutInstruction(null, EAX);
    // Compares equivalent instructions
    Assertions.assertEquals(firstInstruction.hashCode(), SecondInstruction.hashCode());
  }
}
