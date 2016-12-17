import xxl.core.collections.containers.io.BlockFileContainer;
import xxl.core.cursors.sources.io.FileInputCursor;
import xxl.core.io.converters.Converter;
import xxl.core.io.converters.DoubleArrayConverter;
import xxl.core.io.converters.LongConverter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Main {
    public static <T> void main(String[] args) {
        FileInputCursor<Long> cursor = new FileInputCursor<>(LongConverter.DEFAULT_INSTANCE, new File("TimeSeries.bin"));

        Converter<Long> longConverter = new Converter<Long>() {
            @Override
            public Long read(DataInput dataInput, Long object) throws IOException {
                Long l = dataInput.readLong();
                Log("++ Debug : Converter Long Read: "  + l);
                return l;
            }

            @Override
            public void write(DataOutput dataOutput, Long object)
                    throws IOException {
                Log("++ Debug : Converter Long Write: "  + object);
                dataOutput.writeLong(object);
            }
        };

        Converter<Double> doubleConverter = new Converter<Double>() {
            @Override
            public Double read(DataInput dataInput, Double object)
                    throws IOException {
                Double d  = dataInput.readDouble();
                Log("++ Debug : Double Converter Read: " + d);
                return d;
            }

            @Override
            public void write(DataOutput dataOutput, Double object)
                    throws IOException {
                Log("++ Debug : Double Converter Write: " + object);
                dataOutput.writeDouble(object);
            }
        };

        BlockFileContainer blockFileContainer = new BlockFileContainer("BlockFileContainer", 32);

        BasicCOLA<Long, Double> cola = new BasicCOLA<>(16, longConverter,
                doubleConverter,
                blockFileContainer);

        cursor.open();
        long key = 0;
        double value = 0;
        int counter = 0;
        while (cursor.hasNext()) {
            key = cursor.next();
            value = Double.longBitsToDouble(cursor.next());
            Date d = new Date(key * 1000);
            Log(d.toString() + " :: " + value);
            cola.insertElement(key, value);
            Log("--------------------------");
            Log ("Actual State:");
            Log(cola.toString());
            Log("--------------------------");
            counter++;
        }

        Log(cola.searchElement(key));
        Log(cola.toString());
        Log("Number of elements: " + counter);
        cola.close();

    }

    private static void Log(Object o) {
        System.out.println(o);
    }
}
