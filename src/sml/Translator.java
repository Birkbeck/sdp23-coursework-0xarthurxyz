package sml;

import sml.instruction.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static sml.Registers.Register;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {
    /**
     * The name of the plaintext file containing the instructions
     * that the machine will execute.
     * 
     * <p> This field is {@code final}, which means that it cannot be overridden/modified.
     */
    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    /**
     * String representing the next line of the plaintext file
     * that will be processed next.
     */
    private String line = "";

    /**
     * Constructor: Produces a translator object with a file name and
     * helper methods to parse the instructions contained in the plaintext file
     * into internal form before being executed.
     * 
     * @param fileName  name of the plaintext file containing the instructions
     *                  that the translator object should parse.
     */
    public Translator(String fileName) {
        this.fileName =  fileName;
    }

    // translate the small program in the file into lab (the labels) and
    // prog (the program)
    // return "no errors were detected"
    /**
     * 
     * 
     * @param labels        a mapping from 
     * @param program       
     * @throws IOException  
     */
    public void readAndTranslate(Labels labels, List<Instruction> program) throws IOException {
        // This `try-with-resources` block declares resources that can be used in the `try`
        // block and will be closed after the execution of this block.
        // 
        // Specifically declares and initializes a Scanner object using the supplied plaintext file
        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            // Removes any existing labels
            // 
            // Specifically removes all mappings from the Map<String, Integer> `labels`  map. 
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
            //   List<Instruction> `program` list.
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
                    if (label != null) {
                        labels.addLabel(label, program.size());
                    }
                    program.add(instruction);
                }
            }
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label) {
        if (line.isEmpty())
            return null;

        String opcode = scan();
        switch (opcode) {
            case AddInstruction.OP_CODE -> {
                String r = scan();
                String s = scan();
                return new AddInstruction(label, Register.valueOf(r), Register.valueOf(s));
            }

            // TODO: add code for all other types of instructions
            
            case SubInstruction.OP_CODE -> {
                String r = scan();
                String s = scan();
                return new SubInstruction(label, Register.valueOf(r), Register.valueOf(s));
            }

            // TODO: Then, replace the switch by using the Reflection API

            // TODO: Next, use dependency injection to allow this machine class
            //       to work with different sets of opcodes (different CPUs)

            default -> {
                System.out.println("Unknown instruction: " + opcode);
            }
        }
        return null;
    }


    private String getLabel() {
        String word = scan();
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /*
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
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