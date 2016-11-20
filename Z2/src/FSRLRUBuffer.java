package xxl.core;

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
    public int GetnotInBuffer(){
        return notInBuffer;
    }
    public int GetCounter(){
        return counter;
    }

    public Buffer.Slot fix(O owner, I id, Function<? super I,? extends E> obtain)   throws IllegalStateException
    {
        Slot result = (Slot) super.fix( owner , id ,  obtain) ;
        counter++;
        if (queue.contains(result)) {
            queue.remove(result);
            queue.addFirst(result);
        }
        else if (queue.size() <capacity()){
            notInBuffer++;
            queue.addFirst(result);
            }
        else {
            notInBuffer++;
            queue.removeLast();
            queue.addFirst(result);

        }
        return result ;
    }
}
