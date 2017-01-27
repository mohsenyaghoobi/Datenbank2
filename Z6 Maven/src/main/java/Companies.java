import xxl.core.indexStructures.IndexedSet;
import xxl.core.indexStructures.builder.Builders;
import xxl.core.indexStructures.builder.Builders.BPlusCreatorFacade;
import xxl.core.relational.schema.Schemas;
import xxl.core.util.FileUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Companies{
	IndexedSet set;
	
	public Companies() throws SQLException {
		set = BPlusCreatorFacade.Tuples(Schemas.createSchema("Relation").addNChar("entry", 20)).getBuilder().create();
		RelationCursor R = new RelationCursor("company_country.tsv");
		List<Relation> relations = new ArrayList<Relation>();
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
