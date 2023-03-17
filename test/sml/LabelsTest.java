package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import sml.instruction.*;

import static sml.Registers.Register.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

class LabelsTest {
  // SML specific fields
  private Translator translator;
  private Machine machine;
  private Registers registers;

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
    registers = machine.getRegisters();

    // Captures the standard output for unit testing
    System.setOut(new PrintStream(output));
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
    registers = null;

    // Re-assigns the previously captured standard output stream after
    // each test completes to avoid conflicts across tests.
    System.setOut(standardOutput);
  }

  @Test
  public void givenInstructionsWithLabels_whenTranslating_thenCorrect() {
    // Writes test instructions to test file
    try {
      bufferedWriter.write("f1: mov EAX 6" + "\n");
      bufferedWriter.write("f2: mov EBX 5" + "\n");
      bufferedWriter.write("f3: mul EBX EAX" + "\n");
      bufferedWriter.close();
    } catch (IOException ioe) {
      System.err.println("Error creating file: " + this.getClass().getSimpleName());
    }

    // Parses test instructions from test file
    try {
      translator.readAndTranslate(machine.getLabels(), machine.getProgram());
    } catch (Exception e) {
      System.out.println("Error reading the program from " + path.toString());
    }
    // Executes instructions
    machine.execute();

    // Checks labels print to console correctly
    Assertions.assertEquals("[f1 -> 0, f2 -> 1, f3 -> 2]", machine.getLabels().toString());
  }
}
