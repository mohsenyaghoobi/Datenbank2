import java.nio.ByteBuffer;
/**
 * Created by Mohsen on 04-Nov-16.
 */
public class ByteArrayToX {

    public static int byteArrayToInt(byte[] array){
        return  ByteBuffer.wrap(array).getInt();

    }
    public static double byteArrayToDouble(byte[] array){
        return  ByteBuffer.wrap(array).getDouble();

    }
    public static String byteArrayToString(byte[] array){
        String result = "";

        for (byte d : array)
        {
            result+=(char)d;
        }

        return result;
    }

}
