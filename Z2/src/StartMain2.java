import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mohsen on 18-Nov-16.
 */
public class StartMain2 {
    public static void main(String[] args) {
        TwoQueueBuffer t1 = new TwoQueueBuffer(4);

        xxl.core.functions.Function func2 = new xxl.core.functions.Function() {
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
                System.out.println("flush "+argument);
                return argument;
            }

            @Override
            public Object invoke(Object argument0, Object argument1) {
                System.out.println("flush "+argument0);
                return argument0;
            }
        };
        ArrayList<String> arrayList = new ArrayList<String>(java.util.Arrays.asList("A", "B", "A", "A", "B", "A", "A", "C", "D", "B", "A", "C", "E", "D"));
        Iterator iterator = arrayList.iterator();
        String owner = "Mohsen";

        while (iterator.hasNext())
        {
            String str = (String) iterator.next();
            System.out.println("insert " + str);
            t1.update(owner, str, str, func2, true);
        }

        System.out.println("Total:" + t1.getFSR());
    }
}
