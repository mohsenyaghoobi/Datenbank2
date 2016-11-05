import xxl.core.collections.containers.io.BlockFileContainer;
import xxl.core.collections.containers.io.ConverterContainer;
import xxl.core.cursors.Cursor;

import java.io.IOException;

/**
 * Created by qwert on 05/11/2016.
 */
public class Start1 {
    public static void main(String[] args) throws IOException {

        BlockFileContainer block = new BlockFileContainer("StockContainer1.bin", 64);
        StockConverter converter = new StockConverter();
        ConverterContainer container = new ConverterContainer(block, converter);

        StockCursor e = new StockCursor("Testdata.bin");

        StockFilterPeredicate filter = new StockFilterPeredicate();
        while (e.hasNextObject()) {
            StockEntry t = e.nextObject();
            if (filter.invoke(t)) {
                container.insert(t);
            }

            t.Print();
        }
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------------------------------------------");
        System.out.println("----------------------------------------------------------");
        Cursor<StockEntry> c = container.objects();
        while (c.hasNext()) {
            StockEntry f11 = c.next();
            f11.Print();
        }
    }
}
