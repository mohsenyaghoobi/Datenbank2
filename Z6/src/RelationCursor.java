import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.util.ArrayList;

import xxl.core.cursors.AbstractCursor;

public class RelationCursor extends AbstractCursor<Relation> {

    FileInputStream fis = null;
	String path= "";
    public RelationCursor(String path) {
        this.path = path;
		File file = new File(path);
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            System.out.println("Error by reading file " + path);
        }
    }
    Relation r = null;
	boolean n = true;
	@Override
	protected boolean hasNextObject() {
		if(n){
			try {
				int temp = fis.read();
				char b = (char) temp;
				ArrayList<String> relation = new ArrayList<String>();
				relation.add("");
				while(temp!=-1 && b != '\n' && b != '\r'){
					if((b == ' '|| b == '\t') && !relation.get(relation.size()-1).equals(""))
						relation.add("");
					else relation.set(relation.size()-1, relation.get(relation.size()-1) + b);
					b = (char)fis.read();
				}
				if(temp==-1)
					return false;
				fis.mark(1);
				if(b == '\r' && (char)fis.read()!='\n')
					fis.reset();
				if(relation.get(0) != "")
					r = new Relation(relation);
				else r = null;
				n = false;
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected Relation nextObject() {
		boolean next = hasNextObject();
		n = true;
		return next?r:null;
	}
	@Override
	public void reset() throws UnsupportedOperationException {
		File file = new File(path);
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error by reading file " + path);
		}
	}

	public void close(){
		try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("close not succsessful");
		}
	}
}
