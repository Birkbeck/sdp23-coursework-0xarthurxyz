package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

import static sml.Registers.Register.*;

public class MovInstructionTest {
    private Machine machine;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        // ...
    }

    @AfterEach
    void tearDown() {
        machine = null;
    }

    @Test
    void givenPositiveInteger_whenMoving_thenRegisterIsSetCorrectly() {
        Instruction instruction = new MovInstruction(null, EAX, 1);
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void givenEquivalentInstructions_whenComparing_thenTrue() {
        // Instantiates first instruction
        Instruction firstInstruction = new MovInstruction(null, EAX, 1);;
        // Instantiates second instruction
        Instruction SecondInstruction = new MovInstruction(null, EAX, 1);;
        // Compares equivalent instructions
        Assertions.assertTrue(firstInstruction.equals(SecondInstruction));
    }

    @Test
    void givenEquivalentInstructions_whenHashCodes_thenEquivalent() {
        // Instantiates first instruction
        Instruction firstInstruction = new MovInstruction(null, EAX, 1);;
        // Instantiates second instruction
        Instruction SecondInstruction = new MovInstruction(null, EAX, 1);;
        // Compares equivalent instructions
        Assertions.assertEquals(firstInstruction.hashCode(), SecondInstruction.hashCode());
    }
}
