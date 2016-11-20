import xxl.core.io.Buffer;
import java.util.ArrayDeque;
import xxl.core.functions.Function;
/**
 * Created by Mohsen on 18-Nov-16.
 */
public class SimpleTwoQueueBuffer<O,I,E> extends FSRBuffer<O,I,E> {
    private ArrayDeque<Slot> a1;
    private ArrayDeque<Slot> am;
    private int kin = 0;
    private int sizea1;
    private int sizeam;

    public SimpleTwoQueueBuffer(int capacity, int kin) {
        super(capacity);
        this.kin = kin;
        sizea1 = ((int) ((double) (capacity) / (100 - kin) * kin));
        sizeam = capacity;
        a1 = new ArrayDeque<Slot>(sizea1);
        am = new ArrayDeque<Slot>(sizeam);
        System.out.println("sizeam:::"+sizeam);
        System.out.println("sizea1:::"+sizea1);

    }

    @Override
    protected Buffer.Slot fix(O owner, I id, Function<? super I, ? extends E> obtain) throws IllegalStateException {
        Slot result = (Slot) super.fix(owner, id, obtain);
        System.out.println("size am:::"+am.size());
        System.out.println("size a1:::"+a1.size());

        if (a1.contains(result)){
            a1.remove(result);
            if (am.size() < sizeam) {
                if (am.contains(result)){
                    am.remove(result);
                    am.addFirst(result);
                }else
                    am.addFirst(result);
            }
            else
            {
                if (am.contains(result)){
                    am.remove(result);
                    am.addFirst(result);
                }else
                    am.removeLast();
                    am.addFirst(result);
            }
        }
        else if (am.contains(result)){
            am.remove(result);
            am.addFirst(result);
        }
        else
        {
            if (a1.size() < sizea1){
                IncreaseNotInBuffer();
                a1.addFirst(result);
                if (am.size() < sizeam ){
                    am.addLast(result);
                }
                else
                {
                    am.remove(victim());
                    am.addLast(result);
                }
            }
            else
            {
                if (am.size() < sizeam) {
                    if (am.contains(a1.getLast())) {
                        am.remove(a1.getLast());
                        am.addFirst(a1.getLast());
                        a1.removeLast();
                        a1.addFirst(result);
                        am.addLast(result);
                    } else {
                        am.addFirst(a1.getLast());
                        a1.removeLast();
                        a1.addFirst(result);
                    }
                }
                else
                {
                    am.removeLast();
                    if (am.contains(a1.getLast())) {
                        am.remove(a1.getLast());
                        am.addFirst(a1.getLast());
                        a1.removeLast();
                        a1.addFirst(result);
                        am.addLast(result);
                    } else {
                        am.addFirst(a1.getLast());
                        a1.removeLast();
                        a1.addFirst(result);
                    }
                }
            }
        }
       return result ;
    }



    @Override
    protected Slot victim() {
        System.out.print("Victim is Running");
        return am.getLast();

    }
    public void Print()
    {
        System.out.println("size of a1:::"+a1.size());
        System.out.println("A1 Queue:");
        for (Slot t : a1)
        {
            System.out.print(t.toString());
        }
        System.out.println("size of am:::"+am.size());
        System.out.println("Am Queue:");
        for (Slot t : am)
        {
            System.out.print(t.toString());
        }
    }
    public void End(){
        /*System.out.println("Finish!!!");*/
        while (a1.size()>=0){
            System.out.println("Finish!!!");
            am.addFirst(a1.getLast());
            a1.removeLast();
        }
    }

}
