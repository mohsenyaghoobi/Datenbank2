import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        //Aufgabe a)
        RelationCursor r = new RelationCursor("company_country.tsv");

        RelationCursor s = new RelationCursor("person_company.tsv");

        Join join = new Join(r, s);

        while (join.hasNextObject())
            System.out.println(join.nextObject());
        r.close();
        s.close();
        // TODO Aufgabe b und c)
        Companies c = new Companies();


    }


}
