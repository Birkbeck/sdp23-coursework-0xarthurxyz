package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static sml.Registers.Register;

/**
 * This class defines all relevant methods and holds all relevant data
 * to translate instructions from a plaintext file into internal form.
 * 
 * <p>
 * This class is used to parse instructions from a plaintext file and initiate a
 * machine ready to execute the program.
 *
 * @author Arthur Gousset
 */
public final class Translator {
  /**
   * The name of the plaintext file containing the instructions
   * that the machine will execute.
   * 
   * <p>
   * This field is {@code final}, which means that it cannot be
   * overridden/modified.
   */
  private final String fileName; // source file of SML code

  /**
   * String representing the characters in the current line of the plaintext file
   * that have not be processed yet and will be processed next.
   */
  private String line = "";

  /**
   * Constructor: Produces a translator object with a file name and
   * helper methods to parse the instructions contained in the plaintext file
   * into internal form before being executed.
   * 
   * @param fileName name of the plaintext file containing the instructions
   *                 that the translator object should parse.
   */
  public Translator(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Translates every instruction from the plaintext file into the appropriate
   * internal form.
   * 
   * <p>
   * Specifically:
   * Appends an Instruction object to the end of the {@code List<Instruction>}
   * {@code program}
   * list, and creates a mapping from the {@code String} {@code label} to the
   * appropriate index in the {@code List<Instruction>} {@code program} list.
   * 
   * <p>
   * Post condition: {@code program} is a list of all instructions, each
   * instruction
   * has values and register names to read from and write to, {@code labels} maps
   * any label to
   * the appropriate index in the {@code program} list to change the sequence of
   * execution.
   * 
   * @param labels  a mapping from labels to index locations
   *                ({@code Map<String, Integer>})
   * @param program a list of Instructions ({@code List<Instruction>})
   * @throws IOException
   */
  public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
    // This `try-with-resources` block declares resources that can be used in the
    // `try`
    // block and will be closed after the execution of this block.
    //
    // Specifically declares and initializes a Scanner object using the supplied
    // plaintext file
    try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
      // Removes any existing labels
      //
      // Specifically removes all mappings from the Map<String, Integer> `labels` map.
      // The `labels` map will be empty after this method is called.
      labels.reset();
      // Removes any existing instructions stored in the program.
      //
      // Specifically removes all elements from the List<Instruction> `program` list.
      // The `program` list will be empty after this method is called.
      program.clear();

      // Iteratively translates every instruction into internal form.
      //
      // Specifically:
      // + Appends an Instruction object to the List<Instruction> `program` list, and
      // + Creates a mapping from the String `label` to the appropriate index in the
      // List<Instruction> `program` list.
      while (sc.hasNextLine()) {
        // Reads a line from the plaintext file using the Scanner `nextLine()` method
        line = sc.nextLine();
        // Parses the label if present using the Translator `getLabel()` method
        String label = getLabel();
        // Produces an Instruction object of the appropriate type
        // e.g. AddInstruction, SubInstruction, MovInstruction, etc
        Instruction instruction = getInstruction(label);
        // Saves the instruction to internal form.
        //
        // Specifically, appends the Instruction object to the end of the
        // List<Instruction> `program` list (to be executed later).
        //
        // Note: An instruction is only appended if it is parsed correctly
        // (i.e. not `null`)
        if (instruction != null) {
          // Saves the label to internal form.
          //
          // Specifically, creates a mapping from the String `label` to the
          // index (integer) at which the Instruction object is stored in the
          // List<Instruction> `program` list.
          //
          // This allows the program to find the Instruction stored at a specific
          // location.
          //
          // If the map previously contained the key String `label`,
          // the old mapping is replaced by the new value.
          //
          // Note: A label is only mapped to if it is present and parsed correctly
          // (i.e. not `null`)
          try {
            if (label != null) {
              labels.addLabel(label, program.size());
            }
          } catch (Exception e) {
            System.out.println(e.getMessage());
          }
          program.add(instruction);
        }
      }
    } catch (Exception e) {
      System.out.println("Error transforming plaintext file into internal form.");
    }
  }

  /**
   * Translates the current line into an instruction with the given label
   *
   * @param label the instruction label
   * @return the new instruction
   *         <p>
   *         The input line should consist of a single SML instruction,
   *         with its label already removed.
   */
  private Instruction getInstruction(String label) {
    if (line.isEmpty())
      return null;

    String opcode = scan();

    // Creates new instruction
    switch (opcode) {
      case AddInstruction.OP_CODE -> {
        String r = scan();
        String s = scan();
        // Uses Reflection API but couldn't work out way to remove switch statement
        try {
          Class<?> instructionImpl = Machine.OPCODE_MAP.get(opcode);
          Class<?>[] parameterTypes = new Class[] {
              String.class,
              sml.RegisterName.class,
              sml.RegisterName.class };
          Constructor<?> constructor = instructionImpl.getConstructor(parameterTypes);
          return (Instruction) constructor.newInstance(
              label,
              Register.valueOf(r),
              Register.valueOf(s));
        } catch (Exception e) {
          System.out.println("Error constructing " + opcode + " instruction.");
        }
      }

      case SubInstruction.OP_CODE -> {
        String r = scan();
        String s = scan();
        // Uses Reflection API but couldn't work out way to remove switch statement
        try {
          Class<?> instructionImpl = Machine.OPCODE_MAP.get(opcode);
          Class<?>[] parameterTypes = new Class[] {
              String.class,
              sml.RegisterName.class,
              sml.RegisterName.class };
          Constructor<?> constructor = instructionImpl.getConstructor(parameterTypes);
          return (Instruction) constructor.newInstance(
              label,
              Register.valueOf(r),
              Register.valueOf(s));
        } catch (Exception e) {
          System.out.println("Error constructing " + opcode + " instruction.");
        }
      }

      case MulInstruction.OP_CODE -> {
        String r = scan();
        String s = scan();
        // Uses Reflection API but couldn't work out way to remove switch statement
        try {
          Class<?> instructionImpl = Machine.OPCODE_MAP.get(opcode);
          Class<?>[] parameterTypes = new Class[] {
              String.class,
              sml.RegisterName.class,
              sml.RegisterName.class };
          Constructor<?> constructor = instructionImpl.getConstructor(parameterTypes);
          return (Instruction) constructor.newInstance(
              label,
              Register.valueOf(r),
              Register.valueOf(s));
        } catch (Exception e) {
          System.out.println("Error constructing " + opcode + " instruction.");
        }
      }

      case DivInstruction.OP_CODE -> {
        String r = scan();
        String s = scan();
        // Uses Reflection API but couldn't work out way to remove switch statement
        try {
          Class<?> instructionImpl = Machine.OPCODE_MAP.get(opcode);
          Class<?>[] parameterTypes = new Class[] {
              String.class,
              sml.RegisterName.class,
              sml.RegisterName.class };
          Constructor<?> constructor = instructionImpl.getConstructor(parameterTypes);
          return (Instruction) constructor.newInstance(
              label,
              Register.valueOf(r),
              Register.valueOf(s));
        } catch (Exception e) {
          System.out.println("Error constructing " + opcode + " instruction.");
        }
      }

      case OutInstruction.OP_CODE -> {
        String s = scan();
        // Uses Reflection API but couldn't work out way to remove switch statement
        try {
          Class<?> instructionImpl = Machine.OPCODE_MAP.get(opcode);
          Class<?>[] parameterTypes = new Class[] { String.class, sml.RegisterName.class };
          Constructor<?> constructor = instructionImpl.getConstructor(parameterTypes);
          return (Instruction) constructor.newInstance(label, Register.valueOf(s));
        } catch (Exception e) {
          System.out.println("Error constructing " + opcode + " instruction.");
        }
      }

      case MovInstruction.OP_CODE -> {
        String r = scan();
        int value = Integer.parseInt(scan());
        return new MovInstruction(label, Register.valueOf(r), value);
      }

      case JnzInstruction.OP_CODE -> {
        String s = scan();
        String L = scan();
        // Uses Reflection API but couldn't work out way to remove switch statement
        try {
          Class<?> instructionImpl = Machine.OPCODE_MAP.get(opcode);
          Class<?>[] parameterTypes = new Class[] {
              String.class,
              sml.RegisterName.class,
              String.class };
          Constructor<?> constructor = instructionImpl.getConstructor(parameterTypes);
          return (Instruction) constructor.newInstance(label, Register.valueOf(s), L);
        } catch (Exception e) {
          System.out.println("Error constructing " + opcode + " instruction.");
        }
      }

      default -> {
        System.out.println("Unknown instruction: " + opcode);
      }
    }
    // TODO: Then, replace the switch by using the Reflection API

    // Was able to construct Instructions above using Relection API
    // but was not able to completely remove the switch statement
    // I started using the `builder` method below, but couldn't get it to work fully.

    // TODO: Next, use dependency injection to allow this machine class
    // to work with different sets of opcodes (different CPUs)

    return null;
  }

  /**
   * Creates an instance of the class described in param className should such a
   * class exist. An appropriate constructor is chosen based on the number of
   * elements in the array argumentsList and all elements are passed to the
   * constructor as an appropriate type.
   *
   * <p>
   * Source: SDP Worksheet 4 solutions
   * 
   * @param className     fully qualified Java class name as a string
   * @param argumentsList String array of arguments or an empty String array to
   *                      pass to the class constructor
   * @return instantiated Object of class type className or null if construction
   *         is
   *         not possible.
   * @throws ClassNotFoundException className does not refer to a known Class
   */
  public static Object builder(String className, String[] argumentsList) throws ClassNotFoundException {

    int argumentLen = argumentsList.length;
    // Constructor<?> constructor = Class.forName(className).getConstructor();

    for (Constructor<?> candidateConstructor : Class.forName(className).getConstructors()) {
      if (candidateConstructor.getParameterCount() == argumentLen) {
        try {
          Object[] parameterObjs = new Object[argumentLen];
          // get the candidate constructor parameters
          Class<?>[] paramCons = candidateConstructor.getParameterTypes();
          for (int i = 0; i < argumentLen; i++) {
            // attempt to type the parameters using any available string constructors
            // NoSuchMethodException will be thrown where retyping isn't possible
            Class<?> c = toWrapper(paramCons[i]);
            parameterObjs[i] = c.getConstructor(String.class).newInstance(argumentsList[i]);
          }
          // return instance ob object using the successful constructor
          // and parameters of the right class types.
          return candidateConstructor.newInstance(parameterObjs);
        } catch (NoSuchMethodException ignored) {
        } catch (Exception e) {
          e.getStackTrace();
        }
      }
    }
    return null;
  }

  /**
   * Map from primitive type to primitive type wrapper.
   * 
   * <p>
   * Source: SDP Worksheet 4 solutions
   */
  private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_WRAPPERS = Map.of(
      int.class, Integer.class,
      long.class, Long.class,
      boolean.class, Boolean.class,
      byte.class, Byte.class,
      char.class, Character.class,
      float.class, Float.class,
      double.class, Double.class,
      short.class, Short.class,
      void.class, Void.class);

  /**
   * Return the correct Wrapper class if testClass is primitive
   * 
   * <p>
   * Source: SDP Worksheet 4 solutions
   *
   * @param testClass class being tested
   * @return Object class or testClass
   */
  private static Class<?> toWrapper(Class<?> testClass) {
    return PRIMITIVE_TYPE_WRAPPERS.getOrDefault(testClass, testClass);
  }

  /**
   * Parses the characters representing the label in the current instruction that
   * is being translated.
   * 
   * @return characters representing the label, if present
   */
  private String getLabel() {
    // Parses next word from the remaining characters in the current line
    String word = scan();

    // If label is present:
    // Parses the characters representing the label
    if (word.endsWith(":"))
      return word.substring(0, word.length() - 1);

    // If no label is present:
    // Reconstructs the remaining characters in the current line
    // mostly likely representing opcodes, register names and values so they can be
    // parsed
    // elsewhere.
    line = word + " " + line;
    return null;
  }

  /**
   * Parses a single word from the remaining characters in the current line of the
   * plaintext file
   * that have not be processed yet.
   * 
   * @return first word of line and remove it from line; if there is no word,
   *         return {@code ""}
   */
  private String scan() {
    line = line.trim();

    for (int i = 0; i < line.length(); i++)
      if (Character.isWhitespace(line.charAt(i))) {
        String word = line.substring(0, i);
        line = line.substring(i);
        return word;
      }

    return line;
  }
}