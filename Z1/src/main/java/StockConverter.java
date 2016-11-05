import xxl.core.io.converters.Converter;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Mohsen on 29-Oct-16.
 */
class StockConverter extends Converter<StockEntry> {


    @Override
    public StockEntry read(DataInput dataInput, StockEntry object) throws IOException {
        if (object==null) {
            object = new StockEntry();
        }
            object.setId(dataInput.readInt());
            int len =dataInput.readInt();
            byte [] name = new byte[len];
            dataInput.readFully(name);

            object.setName(ByteArrayToX.byteArrayToString( name));
            byte [] temp = new byte[10];
            dataInput.readFully(temp);
            object.setZeitStempel(ByteArrayToX.byteArrayToString( temp));

            object.setKursWert(dataInput.readDouble());
            return object;
    }

    @Override
    public void write(DataOutput dataOutput, StockEntry object) throws IOException {
        if (object!=null)
        dataOutput.writeInt(object.getId());
        dataOutput.writeInt(object.getName().length());
        dataOutput.writeBytes(object.getName());
        dataOutput.writeBytes(object.getZeitStempel());
        dataOutput.writeDouble(object.getKursWert());
    }
}
