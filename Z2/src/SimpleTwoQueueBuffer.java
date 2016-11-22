import xxl.core.io.Buffer;

import java.util.ArrayDeque;

import xxl.core.functions.Function;

/**
 * Created by Mohsen on 18-Nov-16.
 */
public class SimpleTwoQueueBuffer<O, I, E> extends FSRBuffer<O, I, E> {
    private ArrayDeque<Slot> a1;
    private ArrayDeque<Slot> am;
    private int kin;
    private int sizea1;
    private int sizeam;

    public SimpleTwoQueueBuffer(int capacity, int kin) {
        super(capacity);
        this.kin = kin;
        sizea1 = ((int) ((double) (capacity) / (100 - kin) * kin));
        sizeam = capacity;
        a1 = new ArrayDeque<Slot>(sizea1);
        am = new ArrayDeque<Slot>(sizeam);
        System.out.println("sizeam:::" + sizeam);
        System.out.println("sizea1:::" + sizea1);
    }

    @Override
    protected Buffer.Slot fix(O owner, I id, Function<? super I, ? extends E> obtain) throws IllegalStateException {
        Slot result  = (Slot) super.fix(owner, id, obtain);

        log(size());

        if(!am.isEmpty() && am.contains(result)){				// if X in Am
            am.remove(result);
            am.add(result);
        }else if(!a1.isEmpty() && a1.contains(result)){			// if X in A1
            a1.remove(result);
            am.add(result);
        }else{													// if X new
            if(size()<=capacity()){
                //Do nothing
            }
            else {

            }
            a1.add(result);
        }
        return result;
    }

    @Override
    protected Slot victim() {
        notInBuffer++;
        if (a1.size() > kin) {
            Slot victim = a1.poll();
            log("Victim: " );
            return victim;
        } else {
            Slot victim  = am.poll();
            log("Victim: " + victim);
            return victim;
        }
    }

    private void log(Object o){
        System.out.println(o);
    }

}
