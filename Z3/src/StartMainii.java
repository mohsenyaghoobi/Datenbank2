import java.util.ArrayList;

/**
 * Created by Mohsen on 02-Dec-16.
 */
public class StartMainii {
    public static void main(String[] args) {
        DEVB t= new DEVB();
        ArrayList<Integer> arrayList= new ArrayList<>();
        ArrayList<Byte> b = new ArrayList<>();
        ArrayList<Integer> c= new ArrayList<>();
        arrayList.add(1);
        arrayList.add(7);
        arrayList.add(56);
        arrayList.add(134);
        arrayList.add(256);
        arrayList.add(268);
        arrayList.add(384);
        arrayList.add(472);
        arrayList.add(512);
        arrayList.add(648);

        b.addAll(t.encodeVB(arrayList));
        for(byte b1:b){
            System.out.println("Encoded Number:::" + b1);
        }
        System.out.println("----------------------------------------------------------------");
        c.addAll(t.decodeVB(b));
        for(int i:c){
            System.out.println("Decoded Number:::" + i);
        }
    }

}
