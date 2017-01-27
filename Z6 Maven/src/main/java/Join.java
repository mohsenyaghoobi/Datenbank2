import xxl.core.cursors.AbstractCursor;


public class Join extends AbstractCursor<String>{
	RelationCursor R;
	RelationCursor S;
	Relation currentObject = null;
	public Join(RelationCursor r, RelationCursor s){
		this.R = r;
		this.S = s;
	}
	String next = null;
	boolean n = true;
	boolean found = false;

	@Override
	protected boolean hasNextObject() {
		if(n && R!=null && S!=null){
			n = false;
			while(R.hasNextObject()){
			    if(!found)
                    currentObject= R.nextObject();

				while(S.hasNextObject() && currentObject!=null){
					Relation s = S.nextObject();
					if(s!=null){
						next = currentObject.satisfy(s);
						if(next != null){
							found = true;
						    return true;
						}
					}
				}
				if(R.hasNextObject()&& found)
				    S.reset();
                found = false;
			}
		}
		return next != null;
	}
	@Override
	protected String nextObject() {
		//hasNextObject();
		n = true;
		return next;
	}



}
