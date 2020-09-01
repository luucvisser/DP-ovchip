import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql();
        testReizigerDAO(rdao);
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();


        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Update een reiziger en persisteer deze in de database
        sietske.setTussenvoegsel("de");
        System.out.println("[Test] Voor ReizigerDAO.update() :  " + rdao.findById(77));
        rdao.update(sietske);
        System.out.println("       Na ReizigerDAO.update()   :  " + rdao.findById(77));


        // Verwijder een reiziger en persisteer deze in de database
        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");


        // Haal een reiziger aan de hand van zijn ID op uit de database
        Reiziger reiziger = rdao.findById(1);
        System.out.println("[Test] ReizigerDAO.findById() geeft de volgende reiziger:");

        System.out.println(reiziger);

        System.out.println();


        // Haal alle reizigers met een specifieke geboortedatum op uit de database
        List<Reiziger> reizigers2 = rdao.findByGbdatum("2002-12-03");
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r2 : reizigers2) {
            System.out.println(r2);
        }
        System.out.println();
    }
}
