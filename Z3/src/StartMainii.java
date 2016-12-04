import java.util.ArrayList;

/**
 * Created by Mohsen on 02-Dec-16.
 */
public class StartMainii {
    public static void main(String[] args) {
        DEVB t = new DEVB();
        int [] list = {1,7,56,134,256,268,384,472,512,648};
        byte [] list1= new byte[list.length];
        int [] list2 = new int[list.length];


        list1 = t.encodeVB(list);
        System.out.print("Encoded: [  ");
        for (int i=0; i<list1.length ; i++){
            System.out.print(list1[i] + "  ");
        }
        System.out.println("  ]");
        System.out.println("--------------------------------------------------------------" );
        list2 = t.decodeVB(list1);
        System.out.print("Decoded: [  ");
        for (int i=0; i<list2.length ; i++){
            System.out.print(list2[i] + "  ");
        }
        System.out.println("  ]");
    }
/*
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
*/
    }


