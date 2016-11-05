import xxl.core.collections.containers.io.BlockFileContainer;
import xxl.core.collections.containers.io.ConverterContainer;
import xxl.core.cursors.Cursor;
import xxl.core.cursors.filters.Filter;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Mohsen on 31-Oct-16.
 */
public class Start2 {

    public static void main(String[] args) throws FileNotFoundException {
        BlockFileContainer block = new BlockFileContainer("StockContainer2.bin", 64);
        StockConverter converter = new StockConverter();
        ConverterContainer container = new ConverterContainer(block, converter);

        StockCursor e = new StockCursor("Testdata.bin");
        StockFilterPeredicate predicate = new StockFilterPeredicate();
        Filter filter = new Filter(e, predicate);
        filter.open();

        while (filter.hasNext()) {
            container.insert(filter.next());
        }
        filter.close();
        System.out.println("----------------------------------------------------------");
        Cursor<StockEntry> c = container.objects();
        while (c.hasNext()) {
            StockEntry f11 = c.next();
            f11.Print();
        }
    }
}