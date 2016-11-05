import java.io.Serializable;

/**
 * Created by Mohsen on 28-Oct-16.
 */

                                            //to serialize the File
public class StockEntry  implements Serializable{
    private int id;
    private int namelenght;
    private String name;
    private String zeitStempel;
    private double kursWert;
    public StockEntry()
    {

    }
    public StockEntry(int id , String name ,String zeitStempel,double kursWert)
    {
        this.id=id;
        this.name =name;
        this.zeitStempel =zeitStempel;
        this.kursWert =kursWert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZeitStempel() {
        return zeitStempel;
    }

    public void setZeitStempel(String zeitStempel) {
        this.zeitStempel = zeitStempel;
    }

    public double getKursWert() {
        return kursWert;
    }

    public void setKursWert(double kursWert) {
        this.kursWert = kursWert;
    }

    public void Print()
    {
        System.out.println( id +"_"+ name+ "_"+ zeitStempel + "_" + kursWert);
    }
}
