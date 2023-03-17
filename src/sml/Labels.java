package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// TODO: write a JavaDoc for the class

/* 
 * a mapping from labels to index locations ({@code Map<String, Integer>})
 * 
 * * creates a mapping from the {@code String} {@code label} to the 
     * appropriate index in the {@code List<Instruction>} {@code program} list.
 */

/**
 * This class manages String labels assigned to specific instructions
 * 
 * <p> Specifically, this class maintains a mapp
 * 
 * @author Arthur Gousset
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

	// Saves the label to internal form.
	// 
	// 
	// 
	// This allows the program to find the Instruction stored at a specific 
	// location.
	// 
	// If the map previously contained the key String `label`, 
	// the old mapping is replaced by the new value.
	//
	// Note: A label is only mapped to if it is present and parsed correctly 
	// (i.e. not `null`)

	/**
	 * Adds a label with the associated address to the map.
	 *
	 * @param label the label
	 * @param address the address the label refers to
	 */
	public void addLabel(String label, int address) {
		Objects.requireNonNull(label);
		// TODO: Add a check that there are no label duplicates.
		labels.put(label, address);
	}

	/**
	 * Returns the address associated with the label.
	 *
	 * @param label the label
	 * @return the address the label refers to
	 */
	public int getAddress(String label) {
		// TODO: Where can NullPointerException be thrown here?
		//       (Write an explanation.)
		//       Add code to deal with non-existent labels.
		return labels.get(label);
	}

	/**
	 * representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return the string representation of the labels map
	 */
	@Override
	public String toString() {
		// TODO: Implement the method using the Stream API (see also class Registers).
		return "";
	}

	/**
	 * Compares the state and type of this object to that of another object.
	 * 
	 * <p> The state of this object is defined by the fields of this object, which is why they 
	 * are used to calculate unique hash codes for this class.
	 * 
	 * @param 	o 	an object to compare this object to.
	 * @return			{@code true} if the state and type of the objects is equal,
	 * 							{@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		// Uses `instanceof` pattern matching.
		if (o instanceof Labels other) {
			// The binding variable `other` can be used in this scope
			// because the `instanceof` predicate evaluates to true in this block.
			return Objects.equals(this.labels, other.labels);
		}
		return false;
	}

	/**
	 * Produces a unique hash code for every possible state of this object.
	 * 
	 * <p> The state of this object is defined by the fields of this object, which is why they 
	 * are used to calculate unique hash codes for this class.
	 * 
	 * @return	a unique hash code
	 */
	@Override
	public int hashCode() {
		return labels.hashCode();
	}

	/**
	 * Removes any existing labels
	 * 
	 * <p> The mapping will be empty after this method is called.
	 */
	public void reset() {
		labels.clear();
	}
}
