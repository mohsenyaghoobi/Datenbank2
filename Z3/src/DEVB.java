import java.util.ArrayList;
import java.util.Iterator;

public class DEVB {

    // to encode Arraylist of with DiffEn
    public ArrayList<Integer> encodeDiff(ArrayList<Integer> numbers){
        ArrayList<Integer> list = new ArrayList<>();
        Iterator<Integer> iter = numbers.iterator();
        int temp=0;
        int addNumber=0;
        while (iter.hasNext()){
            addNumber = iter.next() - temp;
            list.add(addNumber);
            temp = addNumber+temp;
        }
        return list;
    }

    // to decode Arraylist of with DiffEn
    public ArrayList<Integer> decodeDiff(ArrayList<Integer> numbers){
        ArrayList<Integer> list = new ArrayList<>();
        Iterator<Integer> iter = numbers.iterator();
        int temp=0;
        int addNumber=0;
        while (iter.hasNext()){
            addNumber = iter.next() + temp;
            list.add(addNumber);
            temp = addNumber;
        }
        return list;
    }

    // to encode number Variable Byte
    public ArrayList<Byte> VBEncode(int number) {
        ArrayList<Byte> bytes = new ArrayList<>();

        while (true) {
            byte t = (byte) (number % 128);
            Prepend(bytes, t);
            if (number < 128)
                break;
            number = number / 128;
        }
        int g=bytes.get(bytes.size()-1)+128;
        bytes.set(bytes.size()-1,(byte)((byte)g));

        return bytes;
    }

    // to encode ArrayList<Integer> numbers Variable Byte
    public ArrayList<Byte> encodeVB (ArrayList < Integer > numbers) {
        ArrayList<Byte> bytestream = new ArrayList<>();
        Iterator<Integer> iter = numbers.iterator();
        while (iter.hasNext()) {
            ArrayList<Byte> b = VBEncode(iter.next());
            Extend(bytestream, b);
        }
        return bytestream;
    }
    // to decode Arraylist<Byte> numbers Variable Byte
    public ArrayList<Integer> decodeVB(ArrayList<Byte> numbers ){
        ArrayList<Integer> ALNumbers=new ArrayList<> ();
        int n=0;
        for (byte b:numbers) {
            if (b>=0 && b < 128) {
                n = 128 * n + b;
            } else {
                int t=128+(128-Math.abs(b));
                n = 128* n + (t - 128);
                ALNumbers.add(n);
                n = 0;
            }

        }
        return ALNumbers;
    }


    public void Prepend(ArrayList<Byte> list, byte element)
    {
        list.add(0,element);
    }

    public void Extend(ArrayList<Byte> b1,ArrayList<Byte> b2){
        for(Byte h : b2)
        {
            b1.add(h);
        }
    }

}
