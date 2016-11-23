import xxl.core.io.Buffer;
import xxl.core.functions.Function;

/**
 * Created by Mohsen on 11-Nov-16.
 */
public abstract class FSRBuffer<O, I, E> extends Buffer<O, I, E> {
    int counter = 0;
    int notInBuffer = 0;

    public FSRBuffer(int capacity) {
        super(capacity);
    }

    public double getFSR() {
        if (counter != 0)
            return (double) notInBuffer / (double) counter;
        else
            return 0;
    }

    @Override
    protected Slot victim() {
        return null;
    }
    public void inscreaseNotInBuffer(){
        notInBuffer++;
    }
    protected Buffer.Slot fix(O owner, I id, Function<? super I, ? extends E> obtain) throws IllegalStateException {
        Slot result = (Slot) super.fix(owner, id, obtain);
        counter++;
        return result;
    }

}
