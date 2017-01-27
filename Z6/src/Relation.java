import java.util.ArrayList;
import java.util.Iterator;
import xxl.core.indexStructures.Entry;


public class Relation extends Entry implements Iterable<String>, Comparable<Relation>{
	ArrayList<String> entries;


	public Relation(ArrayList<String> entries) {
		this.entries = new ArrayList<String>();
		for(String entry : entries)
			if(!this.entries.contains(entry))
				this.entries.add(entry);

	}
	public Relation(ArrayList<String> entries, ArrayList<String> second) {
		this(entries);
		for(String entry : second)
			if(!this.entries.contains(entry))
				this.entries.add(entry);
	}
	
	public int length(){
		return entries.size();
	}
	
	public String get(int index){
		return entries.get(index);
	}
	
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {

			int index = 0;
			@Override
			public String next() {
				return entries.get(index++);
			}
			
			@Override
			public boolean hasNext() {
				return index<length();
			}
		};
	}

	public String satisfy(Relation s2) {
		
		for (String r : this) {
			for (String s : s2) {
				if(r.equals(s)){
					Relation result = new Relation(entries, s2.entries);
					return result.toString();
				}
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		String result = '\n' + "";
		for(int i=0; i<this.length(); i++)
			result += result.contains(this.get(i))?"":(this.get(i) + (i==length()-1?"":", ")); 
		return result;
	}
	@Override
	public int compareTo(Relation o) {
		if(length()!=o.length())
			return length()>o.length()?3:-3;
		else{
			for(int index=0; index<length(); index++){
				if(entries.get(index).length()!=o.entries.get(index).length())
					return entries.get(index).length()>o.entries.get(index).length()?2:-2;
				else if(entries.get(index).compareTo(o.entries.get(index))!=0)
					return entries.get(index).compareTo(o.entries.get(index));
			}
		}
		return 0;
	}
}
