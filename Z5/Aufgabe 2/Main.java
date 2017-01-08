package aufgabe53;

public class Main {

	public static void main(String[] args) {
		HashFunction<Integer> h = new HashFunction<Integer>() {
			@Override
			public int hash(Integer obj) {
				return obj*3+7;
			}
		};
		HashFunction<Integer> i = new HashFunction<Integer>() {
			@Override
			public int hash(Integer obj) {
				return obj*13+3;
			}
		};
		HashFunction<Integer> j = new HashFunction<Integer>() {
			@Override
			public int hash(Integer obj) {
				return obj*7+4;
			}
		};
		// 10, 5, 6, 8, 7, 1, 3, 5, 6, 4
		BloomSet<Integer> bs = new BloomSet<Integer>(10, h, i, j);
		bs.add(10);
		bs.add(5);
		bs.add(6);
		bs.add(8);
		bs.add(7);
		bs.add(1);
		bs.add(3);
		bs.add(5);
		bs.add(6);
		bs.add(4);
		// 14, 9, 3
		System.out.println(bs.contains(14)); // true  (false)
		System.out.println(bs.contains(9));  // true  (false)
		System.out.println(bs.contains(3));  // true  (true)
		
		//Es kann nur die 3 richtig von Bloom Filter beantwortet werden,
	}
}
