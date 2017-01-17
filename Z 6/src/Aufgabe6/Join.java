package Aufgabe6;

import xxl.core.cursors.AbstractCursor;
import xxl.core.cursors.Cursor;
import xxl.core.io.converters.Converter;
import xxl.core.util.Pair;


public class Join extends AbstractCursor<String>{
	RelationCursor R;
	RelationCursor S;
	public Join(RelationCursor r, RelationCursor s){
		this.R = r;
		this.S = s;
	}
	String next = null;
	boolean n = true;
	
	@Override
	protected boolean hasNextObject() {
		if(n && R!=null && S!=null){
			n = false;
			while(R.hasNextObject()){
				Relation r = R.nextObject();
				while(S.hasNextObject() && r!=null){
					Relation s = S.nextObject();
					if(s!=null){
						next = r.satisfy(s);
						if(next != null)
							return true;
					}
				}
			}
		}
		return next != null;
	}
	@Override
	protected String nextObject() {
		hasNextObject();
		n = true;
		return next;
	}
	
	

}
