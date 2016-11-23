import xxl.core.functions.Function;
import xxl.core.io.Buffer;
import xxl.core.io.LRUBuffer;
import java.util.ArrayDeque;


/**
 * Created by Mohsen on 18-Nov-16.
 */
public class FSRLRUBuffer<O,I,E> extends LRUBuffer<O,I,E> {

    private ArrayDeque<Slot> queue;
    int counter=0;
    int notInBuffer=0;

    public FSRLRUBuffer(int capacity) {
        super(capacity);
        queue= new ArrayDeque<>(capacity);
    }

    public int GetCounter(){
        return counter;
    }
    public double getFSR() {
        if (counter != 0)
            return (double) notInBuffer / (double) counter;
        else
            return 0;
    }
    public Buffer.Slot fix(O owner, I id, Function<? super I,? extends E> obtain)   throws IllegalStateException
    {
        Slot result = (Slot) super.fix( owner , id ,  obtain) ;
        counter++;
        return result ;
    }

    @Override
    protected Buffer.Slot victim() {
        notInBuffer++;
        return super.victim();
    }
}
