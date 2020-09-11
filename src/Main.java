import dbconnection.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection();

        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        testReizigerDAO(rdao);

        AdresDAOPsql adao = new AdresDAOPsql(connection);
        testAdresDAO(adao, rdao);

        OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
        testOVChipkaartDAO(ovdao, rdao);

        connection.close();
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

    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Reiziger om mee te testen (geen test)
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
        Adres testAdres = new Adres(6, "2113CT", "15", "Heidelberglaan", "Utrecht", testReiziger);
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
        System.out.println("\n[Test] AdresDAO.findByReiziger() geeft het volgende adres:");
        System.out.println(adres);


        // Verwijder een adres en persisteer deze in de database
        System.out.print("\n[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.delete() ");
        adao.delete(testAdres);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen");


        // Verwijder de reiziger om mee te testen (zodat de tests vaker gerund kunnen worden)
        rdao.delete(testReiziger);
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO ovdao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Reiziger om mee te testen (geen test)
        String gbdatum = "1981-03-14";
        Reiziger testReiziger = new Reiziger(6, "T", "van", "Hier", java.sql.Date.valueOf(gbdatum));
        rdao.save(testReiziger);


        // Haal alle OV chipkaarten op uit de database
        List<OVChipkaart> OVChipkaarten = ovdao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OV chipkaarten:");
        for (OVChipkaart ov : OVChipkaarten) {
            System.out.println(ov);
        }


        // Maak een nieuwe OV chipkaart aan en persisteer deze in de database
        String geldig_tot = "2022-06-02";
        OVChipkaart testOV = new OVChipkaart(6, java.sql.Date.valueOf(geldig_tot), 2, 62.0, testReiziger);
        System.out.print("\n[Test] Eerst " + OVChipkaarten.size() + " OV chipkaarten, na OVChipkaartDAO.save() ");
        ovdao.save(testOV);
        OVChipkaarten = ovdao.findAll();
        System.out.println(OVChipkaarten.size() + " OV chipkaarten\n");


        // Update een OV chipkaart en persisteer deze in de database
        testOV.setKlasse(1);
        System.out.println("[Test] Voor OVChipkaartDAO.update() :  " + ovdao.findByReiziger(testReiziger));
        ovdao.update(testOV);
        System.out.println("       Na OVChipkaartDAO.update()   :  " + ovdao.findByReiziger(testReiziger));


        // Haal een OV chipkaart aan de hand van een reiziger op uit de database
        List<OVChipkaart> MijnOVChipkaarten = ovdao.findByReiziger(testReiziger);
        System.out.println("\n[Test] OVChipkaartDAO.findByReiziger() geeft de volgende OV chipkaarten:");
        for (OVChipkaart ov : MijnOVChipkaarten) {
            System.out.println(ov);
        }


        // Verwijder een OV chipkaart en persisteer deze in de database
        System.out.print("\n[Test] Eerst " + OVChipkaarten.size() + " OV chipkaarten, na OVChipkaartDAO.delete() ");
        ovdao.delete(testOV);
        OVChipkaarten = ovdao.findAll();
        System.out.println(OVChipkaarten.size() + " OV chipkaarten");


        // Verwijder de reiziger om mee te testen (zodat de tests vaker gerund kunnen worden)
        rdao.delete(testReiziger);
    }
}
