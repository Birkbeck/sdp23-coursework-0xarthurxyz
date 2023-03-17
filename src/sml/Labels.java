package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class manages optional labels that can be assigned to instructions in the program.
 * A label name can be used to jump to a specific instruction for execution
 * and allows the program to find the Instruction stored at a specific location.
 * 
 * @author Arthur Gousset
 */
public final class Labels {
	private final Map<String, Integer> labels = new HashMap<>();

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
	 * <p> This method deals with non-existent labels by throwing an exception.
	 * 
	 * <p> If the specified label is null, then this method throws a
	 * {@code NullPointerException} exception. This is required by the {@code get}
	 * method interface defined in {@code Map<K, V>}, unless the map explicitly
	 * permits null keys.
	 * 
	 *
	 * @param label the label
	 * @return the address the label refers to
	 * @throws NullPointerException if the specified label is null
	 */
	public int getAddress(String label) {
		//       Add code to deal with non-existent labels.
		return labels.get(label);
	}

	/**
	 * A representation of this instance,
	 * in the form "[label -> address, label -> address, ..., label -> address]"
	 *
	 * @return string representation of the labels map
	 */
	@Override
	public String toString() {
		return labels.entrySet().stream()
				.sorted(Map.Entry.comparingByKey())
				.map(e -> e.getKey() + " -> " + e.getValue())
				.collect(Collectors.joining(", ", "[", "]"));
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
