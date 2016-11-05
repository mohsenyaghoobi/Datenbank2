import xxl.core.collections.containers.io.BlockFileContainer;
import xxl.core.collections.containers.io.ConverterContainer;
import xxl.core.cursors.filters.Filter;

import java.util.Iterator;


public class Main2 {
    public static void main(String[] args) {
        String file_name = "Testdata.bin";
        StockCursor stockCursor = new StockCursor(file_name);
        BlockFileContainer blockFileContainer = new BlockFileContainer("blockFileContainer_2.0", 100);
        StockConverter stockConverter = new StockConverter();
        ConverterContainer converterContainer = new ConverterContainer(blockFileContainer, stockConverter);
        Filter filter =  new Filter(stockCursor, new StockFilterPredicate());

        log("Read file: " + file_name);

        //write data to blockfilecontainer with a filter
        while (filter.hasNext()) {
                converterContainer.insert(filter.next());
        }

        //test if it is correct
        Iterator iterator = converterContainer.objects();
        while (iterator.hasNext()){
            log(iterator.next().toString());
        }
        stockCursor.close();
        converterContainer.close();
    }

    static void log(Object o) {
        System.out.println(o);
    }
}
