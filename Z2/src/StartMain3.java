import xxl.core.FSRLRUBuffer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mohsen on 18-Nov-16.
 */
public class StartMain3 {
    public static void main(String[] args) {
        FSRLRUBuffer f1= new FSRLRUBuffer(5);
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
        ArrayList<String> arrayList = new ArrayList<String>(java.util.Arrays.asList("A","B","A","A","B",
                "A","A","C","D","B","A","C","E","D"));
        Iterator iterator = arrayList.iterator();
        String owner="mohsen";
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
            System.out.println("insert "+str);
            f1.fix(owner, str,func2);
        }
        System.out.println("Not In:"+f1.GetnotInBuffer());
        System.out.println("Total:"+f1.GetCounter());
    }
}
