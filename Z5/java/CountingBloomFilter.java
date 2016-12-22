package aufgabe53;
/**
 * 
 */

/**
 * @author Michael Körber
 *
 */
public class CountingBloomFilter<T> {
    
    public static enum Result { FALSE, MAYBE };
    
    private final int[] counts;
    
    private final HashFunction<T>[] hashes;
    
    public CountingBloomFilter( int buckets, HashFunction<T>...hashes) {
        this.hashes = hashes;
        this.counts = new int[buckets];
    }
    
    /**
     * Inserts the given object into this filter. Ensure, that this
     * object was not saved before calling this method.
     * @param obj the object to insert
     */
    public void insert( T obj ) {
    	for(int i=0; i<hashes.length; i++)
    		counts[Math.abs(hashes[i].hash(obj)%counts.length)]++;
    }
    
    /**
     * Removes the given object from this filter. Ensure, that this
     * object was definitely saved before calling this method.
     * @param obj the object to remove.
     */
    public void remove( T obj ) {
    	if(contains(obj)!= Result.FALSE)
        	for(int i=0; i<hashes.length; i++)
        		counts[Math.abs(hashes[i].hash(obj)%counts.length)]--;
    }
    
    
    /**
     * Checks if the given object is possibly stored in this bloom filter.
     * @param obj the object to check
     * @return {@link Result#FALSE}, if the given object is not stored inside the filter, {@link Result#MAYBE} otherwise
     */
    public Result contains( T obj ) {
    	Result r = Result.FALSE;
    	for(int i=0; i<hashes.length; i++){
    		int h = counts[Math.abs(hashes[i].hash(obj)%counts.length)];
    		if(h>0)
    			r = Result.MAYBE;
    		else if(h == 0)
    			return Result.FALSE;
    	}
    	
        return r;
    }

}
