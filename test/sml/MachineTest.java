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

class MachineTest {
  // SML specific fields
  private Translator translator;
  private Machine machine;

  // File management specific fields
  private Path path;
  private File file;
  private FileWriter fileWriter;
  private BufferedWriter bufferedWriter;

  /**
   * Temporary directory used to create a temporary "test.sml" file.
   */
  @TempDir
  Path temporaryDirectory;

  /**
   * Creates a temporary directory and populates it with a temporary "test.sml"
   * file.
   * 
   * <p>
   * The directory and file are created and deleted after every test, including
   * when tests fail or throw exceptions.
   */
  @BeforeEach
  void setUp() {
    // Creates temporary test file: "test.sml"
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

    try {
      // Starts writing test instructions
      fileWriter = new FileWriter(file);
      bufferedWriter = new BufferedWriter(fileWriter);
      // Actual test instructions are written in each unit test below
    } catch (IOException ioe) {
      System.err.println(
          "error creating temporary test file in " +
              this.getClass().getSimpleName());
    }

    // Sets up SML specific fields
    translator = new Translator(path.toString());
    machine = new Machine(new Registers());
  }

  @AfterEach
  void tearDown() {
    // Resets file management specific fields
    path = null;
    file = null;
    fileWriter = null;
    bufferedWriter = null;

    // Resets SML specific fields
    translator = null;
    machine = null;
  }

  @Test
  void givenSameInstruction_whenComparingMachines_ThenCorrect() {
    // Writes test instructions to test file
    try {
      bufferedWriter.write("    mov EAX 6" + "\n");
      bufferedWriter.write("    mov EBX 1" + "\n");
      bufferedWriter.write("    mov ECX 1" + "\n");
      bufferedWriter.write("f3: mul EBX EAX" + "\n");
      bufferedWriter.write("    sub EAX ECX" + "\n");
      bufferedWriter.write("    jnz EAX f3" + "\n");
      bufferedWriter.write("    out EBX" + "\n");
      bufferedWriter.close();
    } catch (IOException ioe) {
      System.err.println("Error creating file: " + this.getClass().getSimpleName());
    }

    // Create comparison machine
    Machine otherMachine = new Machine(new Registers());

    // Parses test instructions from test file
    try {
      translator.readAndTranslate(machine.getLabels(), machine.getProgram());
      translator.readAndTranslate(otherMachine.getLabels(), otherMachine.getProgram());
    } catch (Exception e) {
      System.out.println("Error reading the program from " + path.toString());
    }
    // Executes instructions
    machine.execute();
    otherMachine.execute();

    // Compares machines
    Assertions.assertEquals(machine, otherMachine);
    Assertions.assertEquals(machine.hashCode(), otherMachine.hashCode());
  }
}
