import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Aufgabe a
        RelationCursor r = new RelationCursor("company_country.tsv");

        RelationCursor s = new RelationCursor("person_company.tsv");

        Join join = new Join(r, s);

        while (join.hasNextObject())
            System.out.println(join.nextObject());
        r.close();
        s.close();

        //Aufgabe b
        BPlusTreeIndexedSet ri = new BPlusTreeIndexedSet("com_cou_realtion", "company", "country", "company_country.tsv");
        BPlusTreeIndexedSet si = new BPlusTreeIndexedSet("per_com_realtion", "person", "company", "person_company.tsv");

        //Aufgabe c
        IndexJoin indexJoin = new IndexJoin(ri, si);
        while (indexJoin.hasNextObject())
            System.out.println(indexJoin.nextObject());

    }


}
