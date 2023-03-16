package sml.instruction;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sml.Instruction;
import sml.Machine;
import sml.Registers;

/**
 * Provides the unit tests with access to the enumeration {@code Registers} defined
 * in the {@code Register} class.
 */
import static sml.Registers.Register.*;

class SubInstructionTest {
    private Machine machine;
    private Registers registers;

    @BeforeEach
    void setUp() {
        machine = new Machine(new Registers());
        registers = machine.getRegisters();
        // ...
    }

    @AfterEach
    void tearDown() {
        machine = null;
        registers = null;
    }

    @Test
    void givenSimpleValues_whenSubtracting_thenResultIsCorrect() {
        registers.set(EAX, 6);
        registers.set(EBX, 5);
        Instruction instruction = new SubInstruction(null, EAX, EBX);
        instruction.execute(machine);
        Assertions.assertEquals(1, machine.getRegisters().get(EAX));
    }

    @Test
    void givenEquivalentMachines_whenComparing_thenTrue() {
        // instantiates second machine for comparison
        Machine otherMachine = new Machine(new Registers());
        Registers otherRegisters = otherMachine.getRegisters();
        // sets both machines to equal state
        registers.set(EAX, 6);
        otherRegisters.set(EAX, 6);
        Assertions.assertTrue(machine.equals(otherMachine));
    }
}
