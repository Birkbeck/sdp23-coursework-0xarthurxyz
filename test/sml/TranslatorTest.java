package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

class TranslatorTest {
  private Translator translator;
  private Machine machine;
  Path path;
  File file;

  /**
   * Temporary directory used to create a temporary "test.sml" file.
   */
  @TempDir
  Path temporaryDirectory;

  /**
   * Creates a temporary directory and populates it with a temporary "test.sml" file.
   * 
   * <p> The directory and file are created and deleted after every test, including
   * when tests fail or throw exceptions.
   */
  @BeforeEach
  void setUp() {
    try {
      // Creates temporary file path
      path = temporaryDirectory.resolve("test.sml");
    } catch (InvalidPathException ipe) {
      System.err.println(
          "error creating temporary test file in " +
              this.getClass().getSimpleName());
    }
    // Creates temporary file
    file = path.toFile();

    // Creates translator and machine using temporary file
    translator = new Translator(path.toString());
    machine = new Machine(new Registers());
  }

  @AfterEach
  void tearDown() {
    path = null;
    file = null;
    translator = null;
    machine = null;
  }

  @Test
  public void givenFileWithOneInstruction_whenTranslating_thenListLengthIsOne() {
    // Produces test instructions
    try {
      FileWriter fw1 = new FileWriter(file);
      BufferedWriter bw1 = new BufferedWriter(fw1);
      bw1.write("    mov EAX 6");
      bw1.close();
    } catch (IOException ioe) {
      System.err.println(
          "error creating temporary test file in " +
              this.getClass().getSimpleName());
    }

    // Parses test instructions from temporary file
    try {
      translator.readAndTranslate(machine.getLabels(), machine.getProgram());
    } catch (Exception e) {
      System.out.println("Error reading the program from " + path.toString());
    }

    Assertions.assertEquals(1, machine.getProgram().size());
  }
}
