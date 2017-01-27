import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xxl.core.indexStructures.IndexedSet;
import xxl.core.indexStructures.Tree;
import xxl.core.indexStructures.builder.BPlusTree.BPlusConfiguration.Creator;
import xxl.core.indexStructures.builder.Builders.BPlusCreatorFacade;
import xxl.core.util.FileUtils;
import xxl.core.indexStructures.builder.Builders;
import xxl.core.indexStructures.builder.BPlusTree.BPlusTreeBuilder;
import xxl.core.relational.schema.Schema;
import xxl.core.relational.schema.Schemas;

public class Companies{
	IndexedSet set;
	
	public Companies() throws SQLException {
		set = BPlusCreatorFacade.Tuples(Schemas.createSchema("Relation").addNChar("entry", 20)).getBuilder().create();
		RelationCursor R = new RelationCursor("company_country.tsv");
		List<Relation> relations = new ArrayList<>();
		while(R.hasNextObject())
			relations.add(R.nextObject());
		
	    set.addAll(relations);
	    System.out.println(set); // [0, 1, 2, 3, ..., 99998, 99999]

	    try {
	      final String tempPath = FileUtils.mkTempDir();

	      IndexedSet diskSet =
	          Builders.createBPlusTree.Integer("Table").storeAt(tempPath)
	              .getBuilder().create();

	      diskSet.addAll(relations);
	      System.out.println(diskSet); // [0, 1, 2, 3, ..., 99998, 99999]

	      diskSet.save();
	    }
	    catch(Exception e){
	    	
	    }
	}

}
