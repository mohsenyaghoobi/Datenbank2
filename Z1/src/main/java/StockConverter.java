import xxl.core.io.converters.Converter;
import xxl.core.io.converters.DoubleConverter;
import xxl.core.io.converters.IntegerConverter;
import xxl.core.io.converters.StringConverter;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;


public class StockConverter extends Converter<StockEntry> {
    @Override
    public StockEntry read(DataInput dataInput, StockEntry object)
            throws IOException {
        int ID = IntegerConverter.DEFAULT_INSTANCE.readInt(dataInput);
        int length = IntegerConverter.DEFAULT_INSTANCE.readInt(dataInput);
        String name = StringConverter.DEFAULT_INSTANCE.read(dataInput);
        String Zeitstempel = StringConverter.DEFAULT_INSTANCE.read(dataInput);
        double kurswert = DoubleConverter.DEFAULT_INSTANCE.readDouble(dataInput);
        return new StockEntry(ID, name, Zeitstempel, kurswert);
    }

    @Override
    public void write(DataOutput dataOutput, StockEntry object)
            throws IOException {
        IntegerConverter.DEFAULT_INSTANCE.write(dataOutput, object.getId());
        IntegerConverter.DEFAULT_INSTANCE.write(dataOutput, object.getName().length());
        StringConverter.DEFAULT_INSTANCE.write(dataOutput, object.getName());
        StringConverter.DEFAULT_INSTANCE.write(dataOutput, object.getZeitstempel());
        DoubleConverter.DEFAULT_INSTANCE.write(dataOutput, object.getKurswert());
    }

}
