import xxl.core.io.Buffer;
import java.util.ArrayDeque;
import xxl.core.functions.Function;
/**
 * Created by Mohsen on 18-Nov-16.
 */
public class TwoQueueBuffer<O,I,E> extends FSRBuffer<O,I,E> {

    private ArrayDeque<Slot> a1in;
    private ArrayDeque<Slot> a1out;
    private ArrayDeque<Slot> am;
    private int kin=0;
    private int kout=0;
    private int sizea1in;
    private int sizea1out;
    private int sizeam;

    public TwoQueueBuffer(int capacity,int kin,int kout) {
        super(capacity);
        this.kin=kin;
        this.kout=kout;
        //initialisation
        sizea1in=kin;
        sizea1out= ((int)((double)(capacity)/(100-kout)*kout));
        sizeam =capacity;
        // Size of a1in && a1out && am
        a1in = new ArrayDeque<Slot>(sizea1in);
        a1out = new ArrayDeque<Slot>(sizea1out);
        am = new ArrayDeque<Slot>(sizeam);
        System.out.println("sizeam    :::"+sizeam);
        System.out.println("sizea1in  :::"+sizea1in);
        System.out.println("sizea1out :::"+sizea1out);


    }
    protected Buffer.Slot fix(O owner,I id,Function<? super I,? extends E> obtain)   throws IllegalStateException
    {
        Slot result = ( Slot ) super.fix( owner , id , obtain ) ;
        System.out.println("size am    :::"+am.size());
        System.out.println("size a1in  :::"+a1in.size());
        System.out.println("size a1out :::"+a1out.size());


        if (a1in.contains(result)){
            a1in.remove(result);
            if (a1out.size() < sizeam) {
                a1out.addFirst(result);
            }
            else
            {
                if (am.size() < sizeam){
                    am.addFirst(a1out.getLast());
                    a1out.removeLast();
                }
                else
                {
                    am.removeLast();
                    am.addFirst(a1out.getLast());
                    a1out.removeLast();
                }
            }
        }
        else if (a1out.contains(result))
        {
            a1out.remove(result);
            if (am.size() < sizeam){
                am.addFirst(result);
            }
            else
            {
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
            if (a1in.size() < sizea1in){
                a1in.addFirst(result);
                if (am.size() < sizeam){
                    am.addLast(result);
                }
                else
                {
                    am.removeLast();
                    am.addLast(result);
                }
            }
            else
            {
                if(a1out.contains(a1in.getLast())){
                    a1in.removeLast();
                    a1in.addFirst(result);

                }
                else
                {
                    if (a1out.size() < sizea1out) {
                        a1out.addFirst(a1in.getLast());
                        a1in.removeLast();
                        a1in.addFirst(result);
                    }
                    else
                    {
                        if (am.size() < sizeam){
                            am.addFirst(a1out.getLast());
                            a1out.removeLast();
                            a1out.addFirst(a1in.getLast());
                            a1in.removeLast();
                            if (am.size() < sizeam){
                                am.addLast(result);
                            }
                            else
                            {
                                am.removeLast();
                                am.addLast(result);
                            }
                        }
                        else
                        {
                            am.removeLast();
                            am.addFirst(a1out.getLast());
                            a1out.removeLast();
                            a1out.addFirst(a1in.getLast());
                            a1in.removeLast();
                        }

                    }
                }
            }
        }

               return result;
            }
    @Override
    protected Slot victim() {
        System.out.println("Victim is Running");
        return am.getLast();
    }
    public void End(){
        /*System.out.println("Finish!!!");*/
        while (a1in.size()>=0){
            System.out.println("Finish!!!");
            am.addFirst(a1in.getLast());
            a1in.removeLast();
        }
    }
}
