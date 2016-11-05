/**
 * Created by Mohsen on 04-Nov-16.
 */
public class ArrayBytes {
    private byte [] array;
    public ArrayBytes(int size)
    {
        array= new byte[size];
    }

    public byte[] getArray() {
        return array;
    }

    public void setArray(byte[] array) {
        this.array = array;
    }
}
