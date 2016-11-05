
public class StockEntry {
    private int id;
    private double kurswert;
    private String name, zeitstempel;

    public StockEntry(int id, String name, String zeitstempel, double kurswert) {
        this.id = id;

        this.name = name;
        this.zeitstempel = zeitstempel;
        this.kurswert = kurswert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getKurswert() {
        return kurswert;
    }

    public void setKurswert(double kurswert) {
        this.kurswert = kurswert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(String zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    @Override
    public String toString(){
        return id + " " + name + " " + zeitstempel + " " + kurswert;
    }
}
