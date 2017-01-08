package aufgabe4.bdtree;

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
    /**
     * Check if this object should be on the left or right side of a node
     * @param n node to compare with this object
     * @return true if this object should be on the left side of a node, otherwise false
     * **/
    public boolean isLeft(Node n) {
        return (n.splitX && this.x < n.splitPosition) || (!n.splitX && this.y < n.splitPosition);
    }

    @Override
    public String toString(){
        return "Name: " + name + " Geschenk: " + geschenk + " x: " + x + " :y " + y;
    }
}
