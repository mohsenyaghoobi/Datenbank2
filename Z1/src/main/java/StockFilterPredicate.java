import xxl.core.predicates.AbstractPredicate;


public class StockFilterPredicate extends AbstractPredicate<StockEntry> {
    @Override
    public boolean invoke(StockEntry argument) {
        if (argument.getKurswert() > 50)
            return true;
        else
            return false;
    }
}
