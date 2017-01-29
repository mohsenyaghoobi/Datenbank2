import xxl.core.indexStructures.BPlusIndexedSet;
import xxl.core.indexStructures.builder.BPlusTree.BPlusTreeBuilder;
import xxl.core.indexStructures.builder.Builders;
import xxl.core.relational.schema.Schema;
import xxl.core.relational.schema.Schemas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BPlusTreeIndexedSet {
    public BPlusIndexedSet<Relation> bplusindexedSet;

    public BPlusTreeIndexedSet(String schemaname, String attributeName1, String attributeName2, String path) throws SQLException {
        Schema schemas = Schemas.createSchema(schemaname);
        schemas.addNChar(attributeName1, 20);
        schemas.addNChar(attributeName2, 20);
        BPlusTreeBuilder builder = new BPlusTreeBuilder(Builders.createBPlusTree.Tuples(schemas));
        bplusindexedSet = builder.create();

        RelationCursor R = new RelationCursor(path);
        List<Relation> relations = new ArrayList<Relation>();
        while (R.hasNextObject()) {
            Relation r = R.nextObject();
            relations.add(r);
            if (bplusindexedSet.add(r)) {
                log("new entry is added to tree");
            } else {
                log("error by adding new entry");
            }
        }
        log(bplusindexedSet);
    }

    private void log(Object o) {
        System.out.println(o);
    }
}
