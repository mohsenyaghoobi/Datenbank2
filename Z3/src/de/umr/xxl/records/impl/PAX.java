package de.umr.xxl.records.impl;

import de.umr.xxl.records.Page;
import xxl.core.io.converters.FixedSizeConverter;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;


public class PAX<T> implements Page<T> {
    private int attributesQuantity;
    private int recordsQuantity;
    private int[] atributesSizes;
    private int freeSpace;
    private int totalSize;
    private NSMPage<byte[]>[] minipages;
    /**
     * Converter used
     */
    private FixedSizeConverter<byte[]> converterForNSM;
    private FixedSizeConverter<T> converterForPAX;

    public PAX(int size, FixedSizeConverter<byte[]> converterForNSM, FixedSizeConverter<T> converterForPAX, int[] attributesSizes) {
        this.converterForNSM = converterForNSM;
        this.converterForPAX = converterForPAX;
        this.totalSize = size;
        this.attributesQuantity = attributesSizes.length;
        this.minipages = new NSMPage[attributesQuantity];
        for (int i = 0 ; i< minipages.length; i++){
            minipages[i] = new NSMPage<>(totalSize / attributesQuantity, converterForNSM);
        }
        this.recordsQuantity = minipages[0].getRecordSize();
        this.atributesSizes = attributesSizes;
        this.freeSpace = minipages[0].getFreeSpace() * attributesQuantity;
    }

    @Override
    public int getFreeSpace() {
        return minipages[0].getFreeSpace() * attributesQuantity;
    }

    @Override
    public int getTotalSize() {
        return totalSize;
    }

    @Override
    public int getRecordSize() {
        return converterForNSM.getSerializedSize();
    }

    @Override
    public short store(T element) {
        byte[] bytes = null;
        try (ByteArrayOutputStream b = new ByteArrayOutputStream())
        {
            try (ObjectOutputStream o = new ObjectOutputStream(b))
            {
                o.writeObject(element);
            }
            bytes = b.toByteArray();
            int dataLength = IntStream.of(atributesSizes).sum();
            bytes = Arrays.copyOfRange(bytes, bytes.length-dataLength, bytes.length);
        } catch (IOException e)
        {
            System.out.println("Error by converting object T to byte array");
        }
        int offset = 0;
        short idx = 0;
        for (int i = 0; i < minipages.length; i++)
        {
            byte[] array =  Arrays.copyOfRange(bytes, offset, offset + atributesSizes[i]);
            idx = minipages[i].store(array);
            offset += atributesSizes[i];
        }
        return idx;
    }

    @Override
    public void delete(short id) {
        Arrays.stream(minipages).forEach(page -> page.delete(id));
    }

    @Override
    public T get(short id) {
        byte[] bytes = new byte[0];
        T object = null;
        for (int i = 0; i < minipages.length; i++)
        {
            byte[] bs = minipages[i].get(id);
            bytes = concatenateArray(bytes, bs);
        }
        try (DataInputStream b = new DataInputStream( new ByteArrayInputStream(bytes)))
        {

                object = (T) converterForPAX.read(b);

        } catch (IOException e2)
        {
            System.out.println("Error by converting byte array to object T");
        }
        return object;
    }

    @Override
    public Iterator<Short> ids() {
        return minipages[0].ids();
    }

    @Override
    public void read(DataInput dataInput) throws IOException {
        Arrays.stream(minipages).forEach(page ->
        {
            try
            {
                page.read(dataInput);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        Arrays.stream(minipages).forEach(page ->
        {
            try
            {
                page.write(dataOutput);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    private byte[] concatenateArray(byte[] array1, byte[] array2) {
        byte[] array1and2 = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, array1and2, 0, array1.length);
        System.arraycopy(array2, 0, array1and2, array1.length, array2.length);
        return array1and2;
    }
}
