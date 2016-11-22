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
        Slot result  = null;
        try {
            result = (Slot) super.fix(owner, id, obtain);
        }catch (IllegalStateException e){
            handleSizeOverflow();
        }

        System.out.println("size am:::" + am.size());
        System.out.println("size a1:::" + a1.size());

        if(!am.isEmpty() && am.contains(result)){				// if X in Am
            am.remove(result);
            am.add(result);
        }else if(!a1.isEmpty() && a1.contains(result)){			// if X in A1
            a1.remove(result);
            am.add(result);
        }else{													// if X new
            if(this.fixedSlots<=this.size){
                //Do nothing
            }
            else {
                handleSizeOverflow();
            }
            a1.add(result);
        }

        return result;
    }

    @Override
    protected Slot victim() {
        if (a1.size() > kin) {
            return a1.poll();
        } else {
            return am.poll();
        }
    }

    public void Print() {
        System.out.println("size of a1:::" + a1.size());
        System.out.println("A1 Queue:");
        for (Slot t : a1) {
            System.out.print(t.toString());
        }
        System.out.println("size of am:::" + am.size());
        System.out.println("Am Queue:");
        for (Slot t : am) {
            System.out.print(t.toString());
        }
    }

    public void End() {
        /*System.out.println("Finish!!!");*/
        while (a1.size() >= 0) {
            System.out.println("Finish!!!");
            am.addFirst(a1.getLast());
            a1.removeLast();
        }
    }

}
