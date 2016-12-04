import java.util.Arrays;

public class EncodingTest {
    public static void main(String arg[]) {
        int[] numbers = new int[]{1, 7, 56, 134, 256, 268, 384, 472, 512, 648};
        DEVB devb = new DEVB();
        //i. Diff Encoding
        int[] diff = devb.encodeDiff(numbers);

        //i. VB
        byte[] vb = devb.encodeVB(numbers);

        //i. diff and VB
        byte[] diff_vb = devb.encodeVB(diff);

        log("Differential Encode Size: " + diff.length * 4);
        log("Differential Encode: " + Arrays.toString(diff));
        log("Differential Decode: " + Arrays.toString(devb.decodeDiff(diff)));
        log("##########################");
        log("VB Encode Size: " + vb.length);
        log("VB Encode: " + Arrays.toString(vb));
        log("VB Decode: " + Arrays.toString(devb.decodeVB(vb)));
        log("##########################");
        log("Differential VB Encode Size: " + diff_vb.length);
        log("Differential VB Encode: " + Arrays.toString(diff_vb));
        log("Differential VB Decode" + Arrays.toString(devb.decodeDiff(devb.decodeVB(diff_vb))));
        log("##########################");
        log("Unserer Meinung nach ist VBencoding besser als Differencoding, weil VBencoding weniger Bytes verbraucht. ");
        log("Und am besten is VB nach Differential Encoding, es spart noch mehr Speicher als VB allein");
    }

    private static void log(Object o) {
        System.out.println(o);
    }

}


