package sml;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * This class represents the logical memory of the machine.
 * 
 * <p>
 * It defines all relevant methods and holds all relevant data
 * to help the machine perform state transitions.
 * 
 * @author Arthur Gousset
 */
public final class Registers {
    /**
     * The registers of the machine, which represent the memory locations
     * that can be written to and read from to perform the instructions
     * supported by the machine.
     * 
     * <p>
     * The registers are implemented as a mapping from an element of
     * the {@code Register} enumeration to an {@code Integer}.
     * 
     * <p>
     * This field is:
     * <ul>
     * <li>{@code private}, which means it is only accessible in this class
     * <li>{@code final}, which means it cannot be overridden/modified
     * </ul>
     */
    private final Map<Register, Integer> registers = new HashMap<>();

    /**
     * An enumeration of the register names available to be written to and
     * read from in the machine.
     */
    public enum Register implements RegisterName {
        EAX, EBX, ECX, EDX, ESP, EBP, ESI, EDI;
    }

    /**
     * Constructor: produces a mapping from every element of
     * the {@code Register} enumeration to the integer 0.
     * 
     * @return mapping from every {@code Register} element to
     *         the integer 0.
     */
    public Registers() {
        clear();
    }

    /**
     * Resets all registers by setting every
     * mapping from an element of the {@code Register} enumeration to the integer 0
     */
    public void clear() {
        for (Register register : Register.values())
            registers.put(register, 0);
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value    new value
     */
    public void set(RegisterName register, int value) {
        registers.put((Register) register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        return registers.get((Register) register);
    }

    /**
     * Compares the state and type of this object to that of another object.
     * 
     * <p>
     * The state of this object is defined by the fields of this object, which is
     * why they are used to calculate unique hash codes for this class.
     * 
     * @param o an object to compare this object to.
     * @return {@code true} if the state and type of the objects is equal,
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        // Uses `instanceof` pattern matching.
        if (o instanceof Registers other) {
            // The binding variable `other` can be used in this scope
            // because the `instanceof` predicate evaluates to true in this block.
            return registers.equals(other.registers);
        }
        return false;
    }

    /**
     * Produces a unique hash code for every possible state of this object.
     * 
     * <p>
     * The state of this object is defined by the fields of this object, which is
     * why they are used to calculate unique hash codes for this class.
     * 
     * @return a unique hash code
     */
    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    /**
     * Returns a formatted String representation of this object.
     * 
     * @return Nicely formatted String representation of the object.
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
