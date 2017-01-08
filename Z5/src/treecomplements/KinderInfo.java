package treecomplements;

/**
 * Created by qwert on 07/01/2017.
 */
public class KinderInfo {
    public String name;
    public String geschenk;
    public int x;
    public int y;

    public KinderInfo(String name, String geschenk, int x, int y) {
        this.name = name;
        this.geschenk = geschenk;
        this.x = x;
        this.y = y;
    }
    public boolean isLeft(Node n) {
        return (n.splitX && this.x < n.splitPosition) || (!n.splitX && this.y < n.splitPosition);
    }

    @Override
    public String toString(){
        return "Name: " + name + " Geschenk: " + geschenk + " x: " + x + " :y " + y;
    }
}
