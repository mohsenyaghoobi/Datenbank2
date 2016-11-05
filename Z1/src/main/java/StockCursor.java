import xxl.core.cursors.AbstractCursor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * Created by Mohsen on 28-Oct-16.
 */
public class StockCursor extends AbstractCursor<StockEntry> {

    FileInputStream fis;
    String filename = "";

    public StockCursor(String filename) throws FileNotFoundException {
        this.filename = filename;
        fis = new FileInputStream(this.filename);
    }

    @Override
    protected boolean hasNextObject() {
        boolean result = false;
        try {
            if (fis.available() > 0)
                result = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;


    }

    @Override
    protected StockEntry nextObject() {
        StockEntry entry=null;
        try {
            ArrayBytes idByte = new ArrayBytes(4);
            fis.read(idByte.getArray());
            ArrayBytes lenght = new ArrayBytes(4);
            fis.read(lenght.getArray());
            int len = ByteArrayToX.byteArrayToInt(lenght.getArray());
            ArrayBytes name = new ArrayBytes(len);
            fis.read(name.getArray());

            ArrayBytes timestamp = new ArrayBytes(10);
            fis.read(timestamp.getArray());

            ArrayBytes value = new ArrayBytes(8);
            fis.read(value.getArray());

            int m_byte = ByteArrayToX.byteArrayToInt(idByte.getArray());
            String m_name = ByteArrayToX.byteArrayToString(name.getArray());
            String m_timestamp = ByteArrayToX.byteArrayToString(timestamp.getArray());
            double m_value = ByteArrayToX.byteArrayToDouble(value.getArray());

            entry  = new StockEntry(m_byte, m_name, m_timestamp, m_value);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return  entry;
    }
}

