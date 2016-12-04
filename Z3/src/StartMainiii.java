import java.util.ArrayList;

/**
 * Created by Mohsen on 02-Dec-16.
 */
public class StartMainiii {
    public static void main(String[] args) {
        int g1;
        int g2;
        DEVB t = new DEVB();
        int [] list = {1,7,56,134,256,268,384,472,512,648};
        int [] list1 = new int[list.length];
        int [] list2 = new int[list.length];
        byte[] list3= new byte[list.length];
        int [] list4 = new int[list.length];
        list1 = t.encodeDiff(list);
        System.out.print("i)");
        System.out.println("***Differential Encoding***");
        System.out.print("Encoded: [  ");
        for (int i=0; i<list1.length ; i++){
            System.out.print(list1[i] + "  ");
        }
        System.out.println("  ]");
        System.out.println("--------------------------------------------------------------" );
        list2 = t.decodeDiff(list1);
        System.out.print("Decoded: [  ");
        for (int i=0; i<list2.length ; i++){
            System.out.print(list2[i] + "  ");
        }
        System.out.println("  ]");
        System.out.print("ii)");
        System.out.println("***Variable Bytes Encoding***");
        list3 = t.encodeVB(list);
        System.out.print("Encoded: [  ");
        for (int i=0; i<list3.length ; i++){
            System.out.print(list3[i] + "  ");
        }
        System.out.println("  ]");
        System.out.println("--------------------------------------------------------------" );
        list4 = t.decodeVB(list3);
        System.out.print("Decoded: [  ");
        for (int i=0; i<list4.length ; i++){
            System.out.print(list4[i] + "  ");
        }
        System.out.println("  ]");
        g1 = list1.length*4;
        g2 = list3.length*1;
        System.out.println("Die Größe der Differential Encoding:::"+ g1);
        System.out.println("Die Größe der Variable Bytes Encoding:::"+ g2);
        System.out.println("Unserer Meinung nach ist VBencoding besser als Diffencoding, weil VBencoding weniger Bytes verbraucht ");

    }

}


