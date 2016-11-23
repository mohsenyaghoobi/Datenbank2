import xxl.core.io.Buffer;
import java.util.ArrayDeque;

import xxl.core.functions.Function;

/**
 * Created by Mohsen on 18-Nov-16.
 */
public class TwoQueueBuffer<O, I, E> extends FSRBuffer<O, I, E> {

    private ArrayDeque<Slot> a1in;
    private ArrayDeque<Slot> a1out;
    private ArrayDeque<Slot> am;
    private int kin = 0;
    private int kout = 0;

    public TwoQueueBuffer(int capacity) {
        super(capacity);
        this.kin =(int)((double)capacity*0.25);
        this.kout =(int)((double)capacity*0.5);

        // Size of a1in && a1out && am
        a1in = new ArrayDeque<Slot>();
        a1out = new ArrayDeque<Slot>();
        am = new ArrayDeque<Slot>();
    }
    @Override
    protected Buffer.Slot fix(O owner, I id, Function<? super I, ? extends E> obtain) throws IllegalStateException {
        Slot result = (Slot) super.fix(owner, id, obtain);

        if(!am.isEmpty() && am.contains(result)){					// if X in Am
            am.remove(result);
            am.add(result);
        }else if(!a1out.isEmpty() && a1out.contains(result)){		// if X in A1out
            a1out.remove(result);
            am.add(result);
        }else if(!a1in.isEmpty() && a1in.contains(result)){			// if X in A1in
            //do nothing
        }else {														// if X is new
            a1in.add(result);
            if(a1in.size()>kin){
                a1out.add(a1in.poll());}
        }

        return result;
    }

    @Override
    protected Slot victim() {
        inscreaseNotInBuffer();
        Slot victim;
        if(a1out.size()>kout){
            victim = a1out.poll();
        }else{
            victim = am.poll();
        }
        return victim;
    }
}
