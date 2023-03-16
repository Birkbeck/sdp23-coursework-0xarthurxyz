package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static sml.Registers.Register.*;

class TranslatorTest {
  private String fileName = "MainDotJavaTest.txt";
  private Translator translator;
  private Machine machine;
  private Registers registers;

  @BeforeEach
  void setUp() {
    translator = new Translator(fileName);
    machine = new Machine(new Registers());
    registers = machine.getRegisters();
  }

  @AfterEach
  void tearDown() {
    translator = null;
    machine = null;
    registers = null;
  }

  @Test
  void givenFileName_whenTranslatingIntoInstructions_thenListLengthCorrect() {
    // Parses instructions from plaintext file
    try {
      translator.readAndTranslate(machine.getLabels(), machine.getProgram());
    } catch (Exception e) {
      System.out.println("Error reading the program from " + fileName 
          + ". Exception" + e.getStackTrace());
    }
    // List<Instruction> `program` should be of length 1
    // because there is only 1 instruction in the plaintext file.
    Assertions.assertEquals(1, machine.getProgram().size());
  }
}
