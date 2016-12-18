import xxl.core.collections.containers.Container;
import xxl.core.collections.containers.io.ConverterContainer;
import xxl.core.functions.Constant;
import xxl.core.io.converters.Converter;
import xxl.core.util.Pair;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Implementation of the basic COLA index structure.
 *
 * @param <K> Type of the keys
 * @param <V> Type of the values
 */
public class BasicCOLA<K extends Comparable<K>, V> {

    /**
     * The main memory cache
     */
    private ArrayList<COLABlock<K, V>> cache;
    /**
     * Stores the block-offset of each array within the container
     */
    private ArrayList<Long> arrayOffsets;
    /**
     * Stores a flag for filling state of each array
     */
    private ArrayList<Boolean> filled;
    private int usedSpaceInFirstBlock = 0;

    /**
     * Underlying container
     */
    private Container container;
    /**
     * Size of an element in bytes stored in the COLA blocks
     */
    private int elementSize;
    /**
     * Size of each disk block in bytes
     */
    private static int DISK_BLOCK_SIZE = 32; // in real applications this should be larger, e.g. 4096
    /**
     * Number of elements per COLA block
     */
    private int elementsPerBlock;
    /**
     * Stores the number of levels in the cache.
     * The maximum level in the cache is the largest array that is smaller or equal to block size
     */
    private int levelsInCache;


    /**
     * Creates a new basic COLA index structure with an underlying container and the given element size in bytes.
     *
     * @param elementSize    The size of each element in bytes
     * @param keyConverter   Converter for the keys
     * @param valueConverter Converter for the values
     * @param rawContainer   An underlying container (e.g. BlockFileContainer)
     */
    public BasicCOLA(int elementSize, final Converter<K> keyConverter, final Converter<V> valueConverter, Container rawContainer) {
        this.arrayOffsets = new ArrayList<Long>();
        this.filled = new ArrayList<Boolean>();
        this.elementSize = elementSize;
        this.elementsPerBlock = DISK_BLOCK_SIZE / elementSize;
        this.levelsInCache = (int) (Math.log(elementsPerBlock) / Math.log(2));
        this.cache = new ArrayList<COLABlock<K, V>>(elementsPerBlock);
        String info = "elementSize = " + elementSize + "\n" +
                "elementsPerBlock = " + elementsPerBlock + "\n" +
                "levelsInCache = " + levelsInCache + "\n";
        Log(info);
        // TODO: this.container = .... ConverterContainer f�r rawContainer erzeugen

        this.container = new ConverterContainer(rawContainer, new Converter<COLABlock<K, V>>() {
            @Override
            public COLABlock<K, V> read(DataInput dataInput,
                                        COLABlock<K, V> object) throws IOException {
                COLABlock<K, V> colaBlock = new COLABlock<>(elementsPerBlock);

                colaBlock.set(0, new Pair<K, V>(
                        keyConverter.read(dataInput),
                        valueConverter.read(dataInput)));

                return colaBlock;
            }

            @Override
            public void write(DataOutput dataOutput, COLABlock<K, V> object)
                    throws IOException {
                for (int i = 0; i < object.getSize(); i++) {
                    keyConverter.write(dataOutput, object.get(i).getFirst());
                    valueConverter.write(dataOutput, object.get(i).getSecond());
                }
            }
        });
    }

    /**
     * Inserts a new element into this COLA
     *
     * @param key   The key of the element
     * @param value The value of the element
     */
    public void insertElement(K key, V value) {
        //find out which level is still free
        int lev = 0;
        try {
            while (filled.get(lev)) {
                lev++;
            }
        } catch (IndexOutOfBoundsException e) {
            filled.add(false);
            arrayOffsets.add(reserveArray(lev));
        }
        Log("Insert to level: " + lev + " [ " + key + " , " + value + "]");

        ArrayList<Pair<K, V>> collection = new ArrayList<>();
        collection.add(new Pair<K, V>(key, value));

        //save all of elements from 0 to lev
        int index = 0;
        while (filled.get(index) && index < filled.size()) {
            if (index < levelsInCache) {
                for (int blockcounter = 0; blockcounter < (int) Math.pow(2, index); blockcounter++) {
                    COLABlock<K, V> block = cache.get((int) (arrayOffsets.get(index) + blockcounter));
                    collection.add(block.get(0));

                }
            } else {
                for (int blockcounter = 0; blockcounter < (int) Math.pow(2, index); blockcounter++) {
                    COLABlock<K, V> block = (COLABlock<K, V>) container.get(arrayOffsets.get(index) + blockcounter * DISK_BLOCK_SIZE, false);
                    collection.add(block.get(0));
                }
            }
            //reset the value of filled
            filled.set(index, false);
            index++;
        }
        filled.set(lev, true);
        //sort list of Pair <K,V>
        collection.sort((Pair<K, V> o1, Pair<K, V> o2) -> o1.getFirst().compareTo(o2.getFirst()));

        //add new COLA BLOCK to cache or extend disk
        if (lev < levelsInCache) {
            //COLABlock<K, V> block = new COLABlock<>(collection.size());
            //add all elements of new sorted collection to new COLA Block
            for (int i = 0; i < collection.size(); i++) {
                COLABlock<K, V> block = new COLABlock<>(elementsPerBlock);
                block.set(0, collection.get(i));
                cache.set((int) (arrayOffsets.get(lev) + i), block);
            }
            Log("Inserted Cache!!!" + " level: " + lev);
        } else {
            for (int i = 0; i < collection.size(); i++) {
                COLABlock<K, V> block = new COLABlock<>(elementsPerBlock);
                block.set(0, collection.get(i));
                container.update(arrayOffsets.get(lev) + i * DISK_BLOCK_SIZE, block, false);
            }
            Log("Inserted Extend Disk!!!" + " level: " + lev + " offset: " + arrayOffsets.get(lev));
        }
    }


    /**
     * Searches for the value with the given key. If there is no such
     * value, an exception is thrown.
     *
     * @param key The key for the requested value
     * @return The value for the key if existing
     * @throws NoSuchElementException If there is no such value
     */
    public V searchElement(K key) throws NoSuchElementException {
        V result = null;
        for (int level = 0; level < filled.size(); level++) {
            if (filled.get(level)) {
                if (level < levelsInCache) {
                    result = binarySeachInLevel(key, level);
                } else {
                    result = binarySeachInLevel(key, level);
                }
            }
            if (result != null) break;
        }
        return result;
    }

    private V binarySeachInLevel(K key, int level) {
        int min = 0;
        int max = (int) Math.pow(level, 2);

        while (max >= min) {
            int middle = min + max / 2;
            COLABlock<K, V> block = null;
            if (level < levelsInCache) {
                block = cache.get((int) (arrayOffsets.get(level) + middle));
            } else {
                block = (COLABlock<K, V>) container.get(arrayOffsets.get(level) + middle * DISK_BLOCK_SIZE);
            }
            Pair<K, V> kvPair = block.get(0);
            if (kvPair.getFirst().compareTo(key) == 0) {
                Log("++ Key: " + key + " found!");
                return kvPair.getSecond();
            }
            if (kvPair.getFirst().compareTo(key) < 0) {
                Log("++ Key: " + key + " found :" + kvPair.getFirst() + " go to right");
                min = middle + 1;
            }
            if (kvPair.getFirst().compareTo(key) > 0) {
                Log("++ Key: " + key + " found :" + kvPair.getFirst() + " go to left");
                max = middle - 1;
            }
        }
        Log("++ Key not found !");
        return null;
    }


    /**
     * Writes the cached arrays to disk.
     */
    public void close() {
        // TODO
        Log("Writing all data in cache to extend disk ...");
        for (int i = 0; i < levelsInCache; i++) {
            if (filled.get(i)) {
                for (int blockcounter = 0; blockcounter < (int) Math.pow(2, i); blockcounter++) {
                    COLABlock<K, V> block = cache.get((int) (arrayOffsets.get(i) + blockcounter));
                    arrayOffsets.add((Long) container.insert(block));
                }
            }
        }
    }

    /**
     * Reserves memory in the container for the new array and returns the
     * ID of the first block representing the array. All other blocks have
     * consecutive IDs.
     *
     * @param level The level of the array
     * @return The id of the first COLABlock
     */
    private Long reserveArray(int level) {
        if (level >= levelsInCache) {

            Long id = (Long) container.reserve(new Constant(null));
            long arrayLength = 1L << level;
            long blockCount = arrayLength; // elementsPerBlock;

            for (int i = 1; i < blockCount; i++)
                container.reserve(new Constant(null));
            Log("Reserve space in extend disk (id: " + id + ")");
            return id;
        } else {
            // TODO Das Array ist kleiner als ein COLABlock und wird
            // im Hauptspeicher gehalten. Arrays im Hauptspeicher werden
            // ebenfalls in COLABlocks gespeichert. Um keinen Speicher zu verschwenden,
            // werden jedoch auch Teile meherer Arrays in einem COLA-Block abgelegt.
            Log("Reserve space in cache");
            int size = (int) Math.pow(2, level);
            for (int i = 0; i < size; i++)
                this.cache.add(new COLABlock<K, V>(elementsPerBlock));
            return (long) size - 1;
        }
    }

    public void Log(Object o) {
        System.out.println(o);
    }

    @Override
    public String toString() {
        boolean inCache = false;
        StringBuilder sb = new StringBuilder();
        for (int level = 0; level < filled.size(); level++) {
            if (level < levelsInCache) {
                inCache = true;
                COLABlock<K, V> block = cache.get(level);
                sb.append(buildString(block, level, inCache).toString());
            } else {
                inCache = false;
                sb.append(buildString(null, level, inCache).toString());
            }
        }
        return sb.toString();
    }

    private StringBuilder buildString(COLABlock<K, V> block, int level, boolean inCache) {
        StringBuilder sb = new StringBuilder();

        if (inCache) {
            sb.append("Cache  :");
        } else {
            sb.append("Extend :");
        }
        if (filled.get(level)) {
            sb.append("[*]");
        } else {
            sb.append("[ ]");
        }

        sb.append("Level: " + level + " Offset: " + arrayOffsets.get(level));
        sb.append("[ ");

        if (inCache) {
            for (int j = 0; j < block.getSize(); j++) {
                sb.append(block.get(j).getFirst() + "  ");
            }
        } else {
            for (int blockcounter = 0; blockcounter < (int) Math.pow(2, level); blockcounter++) {
                COLABlock<K, V> b = (COLABlock<K, V>) container.get(arrayOffsets.get(level) + blockcounter * DISK_BLOCK_SIZE, false);
                for (int j = 0; j < b.getSize(); j++) {
                    sb.append(b.get(j).getFirst() + "  ");
                }
            }
        }
        sb.append("] \n");
        return sb;
    }
}
