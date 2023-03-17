package sml;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import sml.instruction.*;

import static sml.Registers.Register.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

class TranslatorTest {
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
  }

  @Test
  public void givenFileWithTwoInstructions_whenTranslating_thenCorrect() {
    // Writes test instructions to test file
    try {
      bufferedWriter.write("    mov EAX 6" + "\n");
      bufferedWriter.write("    mov EBX 5" + "\n");
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

    // Checks program length is 2
    Assertions.assertEquals(2, machine.getProgram().size());

    // Checks 1st instruction is "mov EAX 6"
    Assertions.assertEquals(
        machine.getProgram().get(0),
        new MovInstruction(null, EAX, 6));
    Assertions.assertEquals(6, registers.get(EAX));

    // Checks 2nd instruction is "mov EBX 5"
    Assertions.assertEquals(
        machine.getProgram().get(1),
        new MovInstruction(null, EBX, 5));
    Assertions.assertEquals(5, registers.get(EBX));
  }

  @Test
  public void givenFileWithManyInstructions_whenTranslating_thenCorrect() {
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

    // Parses test instructions from test file
    try {
      translator.readAndTranslate(machine.getLabels(), machine.getProgram());
    } catch (Exception e) {
      System.out.println("Error reading the program from " + path.toString());
    }
    // Executes instructions
    machine.execute();

    // Checks 1st instruction is "mov EAX 6"
    Assertions.assertEquals(
        machine.getProgram().get(0),
        new MovInstruction(null, EAX, 6));

    // Checks 2nd instruction is "mov EBX 1"
    Assertions.assertEquals(
        machine.getProgram().get(1),
        new MovInstruction(null, EBX, 1));

    // Checks 3rd instruction is "mov ECX 1"
    Assertions.assertEquals(
        machine.getProgram().get(2),
        new MovInstruction(null, ECX, 1));

    // Checks 4th instruction is "f3: mul EBX EAX"
    Assertions.assertEquals(
        machine.getProgram().get(3),
        new MulInstruction("f3", EBX, EAX));

    // Checks 5th instruction is "sub EAX ECX"
    Assertions.assertEquals(
        machine.getProgram().get(4),
        new SubInstruction(null, EAX, ECX));

    // Checks 6th instruction is "jnz EAX f3"
    Assertions.assertEquals(
        machine.getProgram().get(5),
        new JnzInstruction(null, EAX, "f3"));

    // Checks 7th instruction is "out EBX"
    Assertions.assertEquals(
        machine.getProgram().get(6),
        new OutInstruction(null, EBX));

    // Checks registers are correct after execution
    Assertions.assertEquals(720, registers.get(EBX));
    Assertions.assertEquals(0, registers.get(EAX));
  }
}
