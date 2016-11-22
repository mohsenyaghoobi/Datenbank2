import xxl.core.functions.Function;

import java.util.*;

/**
 * Created by Mohsen on 18-Nov-16.
 */
public class StartMain1 {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String owner = "owner", refString = "ABAABAACDBACED";
        for (int idx = 0; idx < refString.length(); ++idx){
            list.add(refString.charAt(idx)+"");
        }
        Iterator<String> iterator = list.iterator();
        SimpleTwoQueueBuffer Simple2Qbuffer = new SimpleTwoQueueBuffer<>(4, 25);
        iterator = list.iterator();
        log("Simple 2Q (kin=25%)");
        run2Q(owner, iterator, Simple2Qbuffer);
        log("FSR: " + Simple2Qbuffer.getFSR());
        log("\n");

    }
    private static void log(Object aMessage){
        System.out.println(aMessage);
    }
    private static void run2Q(String owner,
                              Iterator<String> iterator,
                              FSRBuffer<String, String, String> Simple2Qbuffer){
        while (iterator.hasNext()) {
            String i = (String)iterator.next();
            System.out.println("insert "+i);
            Simple2Qbuffer.update(owner, i, i, new Function<Object,Object>(){

                @Override
                public Object invoke(java.util.List<? extends Object> arguments) {
                    // TODO Auto-generated method stub
                    return null;
                }

                @Override
                public Object invoke() {
                    // TODO Auto-generated method stub
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
                }}, true);
        }
    }
}
