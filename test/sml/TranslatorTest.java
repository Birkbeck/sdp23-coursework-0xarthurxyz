package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static sml.Registers.Register.*;

class TranslatorTest {
  // private String fileName = "test3.sml";
  // private Translator translator;
  // private Machine machine;
  // private Registers registers;

  @BeforeEach
  void setUp() {
    // translator = new Translator(fileName);
    // machine = new Machine(new Registers());
    // registers = machine.getRegisters();
  }

  @AfterEach
  void tearDown() {
    // translator = null;
    // machine = null;
    // registers = null;
  }

  // My Todo: use temporary files here
  @Test
  void givenFileName_whenTranslatingIntoInstructions_thenListLengthCorrect() {
      Translator t = new Translator("test3.sml");
			Machine m = new Machine(new Registers());
      // Parses instructions from plaintext file
      try {
			t.readAndTranslate(m.getLabels(), m.getProgram());
    } catch (Exception e) {
      System.out.println("Error reading the program from " + "test3.sml");
    }
    // List<Instruction> `program` should be of length 1
    // because there is only 1 instruction in the plaintext file.
    Assertions.assertEquals(1, m.getProgram().size());
  }
}
