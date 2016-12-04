import java.util.ArrayList;
import java.util.Iterator;

public class DEVB {
    int arrSize;
    // to encode Array of with DiffEn
    public int[] encodeDiff(int[] numbers){
        int[] list = new int[numbers.length];
        int temp=0;
        int addNumber=0;
        for(int i=0; i< numbers.length ; i++ ){
            addNumber = numbers[i] - temp;
            list[i] = addNumber;
            temp = addNumber+temp;
        }
        return list;
    }

    // to decode Array of with DiffEn
    public int[] decodeDiff(int[] numbers){
        int[] list = new int[numbers.length] ;
        int temp=0;
        int addNumber=0;
       for (int i=0; i<numbers.length; i++){
            addNumber = numbers[i] + temp;
            list[i] = addNumber;
            temp = addNumber;
        }
        return list;
    }

    // to encode number Variable Byte
    public byte[] VBEncode(int number) {
        byte[] bytes = new byte[0] ;
        while (true) {
            byte t = (byte) (number % 128);
            bytes = Prepend(bytes, t);

            if (number < 128)
                break;
            number = number / 128;
        }
        bytes[bytes.length-1] += 128;
        return bytes;
    }

    // to encode  int Array numbers Variable Byte
    public byte[] encodeVB (int[] numbers) {
        byte[] bytestream = new byte[0];

        for (int i=0; i<numbers.length ; i++){
            byte[] b = VBEncode(numbers[i]);
            bytestream = Extend(bytestream, b);
        }
        arrSize = numbers.length;
        return bytestream;
    }

    // to decode byte Array numbers Variable Byte
    public int[] decodeVB(byte[] numbers ){
        int [] ArNumbers = new int[arrSize];
        int n=0;
        int temp=0;
        for(int i=0; i<numbers.length; i++){
            if ( numbers[i]>0) {
                n = 128 * n + numbers[i];
            } else {
                n = 128* n + (byte)(numbers[i] - 128);
                ArNumbers[temp] = n;
                n = 0;
                temp++;
            }
        }
        return ArNumbers;
    }


    public byte[] Prepend(byte[] list, byte element) {

        int listLen = list.length;
        if (listLen > 0) {
            byte[] b = list;
            byte[] temp = new byte[listLen + 1];
            for (int i = 0; i < b.length; i++) {
                temp[i] = b[i];
            }
            for (int i = temp.length; i > 1; i--) {
                temp[i-1] = temp[i - 2];
            }
            temp[0] = element;
            return temp;
        } else {
            byte[] temp = new byte[listLen + 1];
            temp[0] = element;
            return temp;
        }
    }

    public byte[] Extend(byte[] b1,byte[] b2){
        int b1Len = b1.length;
        int b2Len = b2.length;
        byte[] b3= new byte[b1Len+b2Len];
        System.arraycopy(b1, 0, b3, 0, b1Len);
        System.arraycopy(b2, 0, b3, b1Len, b2Len);
        return b3;
  }

}
