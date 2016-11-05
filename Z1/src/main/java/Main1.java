import xxl.core.collections.containers.io.BlockFileContainer;
import xxl.core.collections.containers.io.ConverterContainer;

import java.util.Iterator;

public class Main1 {
    public static void main(String[] args) {
        String file_name = "Testdata.bin";
        StockCursor stockCursor = new StockCursor(file_name);
        BlockFileContainer blockFileContainer = new BlockFileContainer("blockFileContainer_1.0", 100);
        StockConverter stockConverter = new StockConverter();
        ConverterContainer converterContainer = new ConverterContainer(blockFileContainer, stockConverter);
        StockFilterPredicate stockFilterPredicate = new StockFilterPredicate();
        log("Read file: " + file_name);
        //write data to blockfilecontainer
        while (stockCursor.hasNextObject()) {
            StockEntry currentEntry = stockCursor.nextObject();
            if (currentEntry.getKurswert() > 50){
                converterContainer.insert(currentEntry);
            }
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
