/**
 * InstrumentedInt is essentially an integer wrapper class that keeps track of 
 * the number of comparisons that are done on InstrumentedInt.
 * 
 * @author CS 367, copyright 2011
 */
public class InstrumentedInt implements Comparable<InstrumentedInt> {
    private static int compares = 0;  // the comparison counter
    private int value;  // integer value for each InstrumentedInt object

    /**
     * Resets the comparison counter to 0.
     */
    public static void resetCompares() {
        compares = 0;
    }

    /**
     * Returns the value of the comparison counter.
     * 
     * @return the value of the comparison counter
     */
    public static int getCompares() {
        return compares;
    }

    /**
     * Creates a new InstrumentedInt with the given value.
     * 
     * @param value  the integer value for this object
     */
    public InstrumentedInt(int value) {
        this.value = value;
    }

    /**
     * Returns the integer value for this InstrumentedInt.
     * 
     * @return the integer value for this InstrumentedInt
     */
    public int intValue() {
        return value;
    }

    /**
     * Compares this InstrumentedInt to the one given and increments the 
     * comparison counter.
     * 
     * @param other  the item to compare to
     * @return < 0, 0, > 0 depending on whether this InstrumentedInt is less 
     *         than, equal to, or greater than the one given
     */
    public int compareTo(InstrumentedInt other) {
        ++compares;
        if (value == other.value)
            return 0;
        else if (value < other.value)
            return -1;
        else
            return 1;
    }

    /**
     * Returns true if the given InstrumentedInt has the same integer value as
     * this one.
     * 
     * @param obj  other object to compare with this one
     * @return true if the integer values are the same
     */
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof InstrumentedInt))
            return false;
        return ((InstrumentedInt) obj).value == this.value;
    }

    /**
     * Returns a String representation, in this case a String containing the
     * integer value.
     * 
     * @return a String representation suitable for printing
     */
    public String toString() {
        return Integer.toString(value);
    }
}
