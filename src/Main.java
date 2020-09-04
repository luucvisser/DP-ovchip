import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        ReizigerDAOPsql rdao = new ReizigerDAOPsql();
        testReizigerDAO(rdao);

        AdresDAOPsql adao = new AdresDAOPsql();
        testAdresDAO(adao);
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }


        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("\n[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
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


        // Haal alle reizigers met een specifieke geboortedatum op uit de database
        List<Reiziger> reizigers2 = rdao.findByGbdatum("2002-12-03");
        System.out.println("\n[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r2 : reizigers2) {
            System.out.println(r2);
        }
        System.out.println();
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Reiziger om mee te testen (geen test)
        ReizigerDAOPsql rdao = new ReizigerDAOPsql();
        String gbdatum = "1981-03-14";
        Reiziger testReiziger = new Reiziger(6, "T", "van", "Hier", java.sql.Date.valueOf(gbdatum));
        rdao.save(testReiziger);


        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }


        // Maak een nieuw adres aan en persisteer deze in de database
        Adres testAdres = new Adres(6, "2113CT", "15", "Heidelberglaan", "Utrecht", 6);
        System.out.print("\n[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(testAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");


        // Update een adres en persisteer deze in de database
        testAdres.setHuisnummer("20");
        System.out.println("[Test] Voor AdresDAO.update() :  " + adao.findByReiziger(testReiziger));
        adao.update(testAdres);
        System.out.println("       Na AdresDAO.update()   :  " + adao.findByReiziger(testReiziger));


        // Haal een adres aan de hand van een reiziger op uit de database
        Adres adres = adao.findByReiziger(testReiziger);
        System.out.println("\n[Test] AdresDAO.findByReiziger() geeft de volgende reiziger:");
        System.out.println(adres);


        // Verwijder een adres en persisteer deze in de database
        System.out.print("\n[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() ");
        adao.delete(testAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen");


        // Verwijder de reiziger om mee te testen (zodat de tests vaker gerund kunnen worden)
        rdao.delete(testReiziger);
    }
}
