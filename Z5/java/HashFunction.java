package aufgabe53;
/**
 * 
 */

/**
 * Represents a hash function for objects of type <T>
 * @author Michael Körber
 *
 */
public interface HashFunction<T> {
    
    /**
     * Computes the hash value for the given object.
     * The returned value ranges from {@link Integer#MIN_VALUE} to {@link Integer#MIN_VALUE}.
     * It must be ensured that for two objects: T o1 and T 2 the following
     * equations hold:
     * <ul>
     * <li>o1 == o2 => hash(o1) == hash(o2)</li>
     * <li>o1.equals(o2) => hash(o1) == hash(o2)</li>
     * </ul>
     * @param obj the object to calculate the hash value for.
     * @return the hash value for the given object.
     */
    int hash(T obj);

}
