package aufgabe53;

import java.util.HashSet;

public class BloomSet<T> extends HashSet<T>{
	private CountingBloomFilter<T> filter;
	private int buckets;
	private HashFunction<T>[] hashes;
	public BloomSet(int buckets, HashFunction<T>...hashes) {
		this.buckets = buckets;
		this.hashes = hashes;
		filter = new CountingBloomFilter<>(buckets, hashes);
	}
	
	@Override
	public boolean add(T e) {
		if(super.add(e))
			filter.insert(e);
		else return false;
		return true;
	}
	@Override
	public void clear() {
		filter = new CountingBloomFilter<>(buckets, hashes);
		super.clear();
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o) {
		if(filter.contains((T) o)==CountingBloomFilter.Result.MAYBE)
			return super.contains(o);
		return false;
	}
	@Override
	public boolean remove(Object o) {
		if(super.remove(o))
			filter.remove((T) o);
		else return false;
		return true;
	}
}
