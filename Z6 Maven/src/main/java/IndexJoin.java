import xxl.core.cursors.AbstractCursor;
import xxl.core.relational.tuples.ArrayTuple;

import java.util.Iterator;

public class IndexJoin extends AbstractCursor<String> {
    Iterator R;
    Iterator S;
    BPlusTreeIndexedSet r;
    BPlusTreeIndexedSet s;


    ArrayTuple currentObject = null;

    public IndexJoin(BPlusTreeIndexedSet r, BPlusTreeIndexedSet s) {
        this.R = r.bplusindexedSet.getIndexStructure().query();
        this.S = r.bplusindexedSet.getIndexStructure().query();
        this.r = r;
        this.s = s;
    }

    Relation next = null;
    boolean n = true;
    boolean found = false;

    @Override
    protected boolean hasNextObject() {
        next = null;
        if (n && R != null && S != null) {
            n = false;
            while (R.hasNext()) {
                if (!found) {
                    currentObject = (ArrayTuple) R.next();
                }

                while (S.hasNext() && currentObject != null) {
                    Object[] s2 = ((ArrayTuple) S.next()).toArray();
                    if (s != null) {
                        for (Object r : currentObject.toArray()) {
                            for (Object s : s2) {
                                if (r.equals(s)) {
                                    next = new Relation(currentObject.toArray(), s2);
                                }
                            }
                        }
                        if (next != null && next.entries.size()>2) {
                            found = true;
                            return true;
                        }
                        else
                            next = null;
                    }
                }
                if (R.hasNext() && !S.hasNext()) {
                    S = r.bplusindexedSet.getIndexStructure().query();
                }
                found = false;
            }
        }
        return next != null;
    }

    @Override
    protected String nextObject() {
        n = true;
        return next.toString();
    }


}
