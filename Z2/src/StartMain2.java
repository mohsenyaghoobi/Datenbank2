import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Created by Mohsen on 18-Nov-16.
 */
public class StartMain2 {
    public static void main(String[] args) {
        TwoQueueBuffer t1= new TwoQueueBuffer(4,3,50);

        xxl.core.functions.Function func2= new xxl.core.functions.Function() {
            @Override
            public Object invoke(List arguments) {
                return null;
            }

            @Override
            public Object invoke() {
                return null;
            }

            @Override
            public Object invoke(Object argument) {
                return null;
            }

            @Override
            public Object invoke(Object argument0, Object argument1) {
                return null;
            }
        };
        ArrayList<String> arrayList = new ArrayList<String>(java.util.Arrays.asList("A","B","A","A","B","A","A","C","D","B","A","C","E","D"));
        Iterator iterator = arrayList.iterator();
        String owner="Mohsen";

        while (true) {
            if (iterator.hasNext()) {
                String str = (String) iterator.next();
                System.out.println("insert " + str);
                t1.fix(owner, str, func2);
            }
            else
            {
                t1.End();
                break;
            }
        }
        System.out.println("Not In:"+t1.GetNotInBuffer());
        System.out.println("Total:"+t1.GetFSR());
    }
}
