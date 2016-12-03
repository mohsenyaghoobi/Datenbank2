package de.umr.xxl.records;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Iterator;

import de.umr.xxl.records.impl.PAX;
import xxl.core.io.converters.FixedSizeConverter;

public class Main {

	private static class IntRecord implements Serializable {
		private int i1;
		private int i2;
		private int i3;

		public IntRecord(int i1, int i2, int i3) {
			this.i1 = i1;
			this.i2 = i2;
			this.i3 = i3;
		}

		public int getI1() {
			return i1;
		}

		public int getI2() {
			return i2;
		}

		public int getI3() {
			return i3;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i1;
			result = prime * result + i2;
			result = prime * result + i3;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			IntRecord other = (IntRecord) obj;
			if (i1 != other.i1)
				return false;
			if (i2 != other.i2)
				return false;
			if (i3 != other.i3)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "IntRecord [i1=" + i1 + ", i2=" + i2 + ", i3=" + i3 + "]";
		}
	}
	
	private static class IntRecordConverter extends FixedSizeConverter<IntRecord> {

		private static final long serialVersionUID = 1L;

		public IntRecordConverter() {
			super(12);
		}

		@Override
		public IntRecord read(DataInput dataInput, IntRecord object) throws IOException {
			return new IntRecord(dataInput.readInt(), 
					dataInput.readInt(), 
					dataInput.readInt());
		}

		@Override
		public void write(DataOutput dataOutput, IntRecord object) throws IOException {
			dataOutput.writeInt(object.getI1());
			dataOutput.writeInt(object.getI2());
			dataOutput.writeInt(object.getI3());
		}
		
	}
	private static class BytesConverter extends FixedSizeConverter<byte[]> {

		private static final long serialVersionUID = 1L;

		public BytesConverter() {
			super(4);
		}

		@Override
		public byte[] read(DataInput dataInput, byte[] object) throws IOException {
			int n = dataInput.readInt();
			return ByteBuffer.allocate(4).putInt(n).array();
		}

		@Override
		public void write(DataOutput dataOutput, byte[] object) throws IOException {
			dataOutput.write(object);
		}

	}


    public static void main(String[] args) throws IOException {
		//NSMPage<IntRecord> testPage = new NSMPage<>(65, new IntRecordConverter());
		PAX<IntRecord> testPage = new PAX<>(80, new BytesConverter(), new IntRecordConverter(), new int[]{4,4,4});

		testPage(testPage);
	}
	
	private static void testPage( Page<IntRecord> page ) throws IOException {
		IntRecord r1 = new IntRecord(1, 1, 1);
		IntRecord r2 = new IntRecord(2, 2, 2);
		IntRecord r3 = new IntRecord(3, 3, 3);
		IntRecord r4 = new IntRecord(4, 4, 4);
		IntRecord r5 = new IntRecord(5, 5, 5);
		IntRecord r6 = new IntRecord(6, 6, 6);

        System.out.println("Total Size: " + page.getTotalSize());
		System.out.println("Free space: " + page.getFreeSpace());
		page.store(r1);
		System.out.println("Free space: " + page.getFreeSpace());
		page.store(r2);
		System.out.println("Free space: " + page.getFreeSpace());
		short i3 = page.store(r3);
		System.out.println("Free space: " + page.getFreeSpace());
		page.store(r4);
		System.out.println("Free space: " + page.getFreeSpace());
        page.store(r5);
        System.out.println("Free space: " + page.getFreeSpace());

		
		Iterator<Short> iter = page.ids();
		while ( iter.hasNext() ) {
			Short id = iter.next();
			System.out.println(id + ": " + page.get(id) );
		}
		
		page.delete(i3);
		System.out.println("Free space: " + page.getFreeSpace());

		iter = page.ids();
		while ( iter.hasNext() ) {
			Short id = iter.next();
			System.out.println(id + ": " + page.get(id) );
		}
		
		
		page.store(r6);
		System.out.println("Free space: " + page.getFreeSpace());
		
		iter = page.ids();
		while ( iter.hasNext() ) {
			Short id = iter.next();
			System.out.println(id + ": " + page.get(id) );
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		page.write(dos);
		page.read( new DataInputStream(new ByteArrayInputStream(bos.toByteArray())));
		
		System.out.println("Free space (after serialization): " + page.getFreeSpace());
		
		iter = page.ids();
		while ( iter.hasNext() ) {
			Short id = iter.next();
			System.out.println(id + ": " + page.get(id) );
		}
	}

}
