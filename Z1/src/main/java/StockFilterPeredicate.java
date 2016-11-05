import xxl.core.predicates.AbstractPredicate;

/**
 * Created by Mohsen on 04-Nov-16.
 */
public class StockFilterPeredicate extends AbstractPredicate<StockEntry> {



    @Override
    public boolean invoke(StockEntry arg) {
        if (arg.getKursWert()>50)
            return true;
        else
            return false;
    }


}
