package daopsql;

import dao.OVChipkaartDAO;
import domein.OVChipkaart;
import domein.Product;
import domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection connection;
    private ReizigerDAOPsql rdao;
    private ProductDAOPsql pdao;

    // Constructor (krijgt de database connectie mee zodat die overal in de file gebruikt kan worden)
    public OVChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    // Slaat het meegegeven object op in de database
    @Override
    public boolean save(OVChipkaart ov) throws SQLException {
        int nummer = ov.getNummer();
        Date geldig_tot = ov.getGeldig_tot();
        int klasse = ov.getKlasse();
        Double saldo = ov.getSaldo();
        int reiziger_id = ov.getReiziger().getId();

        // Slaat de gegevens op in de database
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (" + nummer + ", '" + geldig_tot + "', " + klasse + ", " + saldo + ", " + reiziger_id + ")");

        // Slaat de relaties van de OV chipkaart op
        if (!ov.getProducten().isEmpty()) {
            for (Product product : ov.getProducten()) {
                java.util.Date vandaag = Calendar.getInstance().getTime();
                stmt.executeUpdate("INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer, status, last_update) VALUES (" + ov.getNummer() + ", " + product.getNummer() + ", 'actief', '" + vandaag + "')");
            }
        }

        stmt.close();

        return true;
    }

    // Update het meegegeven object in de database
    @Override
    public boolean update(OVChipkaart ov) throws SQLException {
        int nummer = ov.getNummer();
        Date geldig_tot = ov.getGeldig_tot();
        int klasse = ov.getKlasse();
        Double saldo = ov.getSaldo();
        int reiziger_id = ov.getReiziger().getId();

        // Update de database met de nieuwe gegevens
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE ov_chipkaart SET geldig_tot = '" + geldig_tot + "', klasse = " + klasse + ", saldo = " + saldo + ", reiziger_id = " + reiziger_id + " WHERE kaart_nummer = " + nummer);

        // Update de OV Chipkaarten met het aangepaste product
        if (!ov.getProducten().isEmpty()) {
            for (Product p : ov.getProducten()) {
                for (OVChipkaart OVChipkaart : p.getOVChipkaarten()) {
                    if (ov.getNummer() == OVChipkaart.getNummer()) {
                        p.removeOVChipkaart(OVChipkaart);
                        p.setOVChipkaarten(ov);
                    }
                }
            }
        }

        stmt.close();

        return true;
    }

    // Verwijderd het meegegeven object uit de database
    @Override
    public boolean delete(OVChipkaart ov) throws SQLException {
        int nummer = ov.getNummer();

        // Maak een statement
        Statement stmt = connection.createStatement();

        // Verwijderd alle relaties van de OV chipkaart
        if (!ov.getProducten().isEmpty()) {
            for (Product p : ov.getProducten()) {
                stmt.executeUpdate("DELETE FROM ov_chipkaart_product WHERE kaart_nummer = " + nummer + " AND product_nummer = " + p.getNummer());
            }
        }

        // Verwijder de OV chipkaart zelf
        stmt.executeUpdate("DELETE FROM ov_chipkaart WHERE kaart_nummer = " + nummer);

        stmt.close();

        return true;
    }

    // Vindt een OV chipkaart aan de hand van een reiziger
    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId();

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<OVChipkaart> OVChipkaarten = new ArrayList<>();

        // Als de select wat opbrengt
        while (rs.next()) {
            int nummer = rs.getInt(1);
            Date geldig_tot = rs.getDate(2);
            int klasse = rs.getInt(3);
            Double saldo = rs.getDouble(4);

            // Zet de OV chipkaart in een OVChipkaart object
            OVChipkaart ov = new OVChipkaart(nummer, geldig_tot, klasse, saldo, reiziger);

            // Set de producten van de OV chipkaart
            List<Product> producten = pdao.findByOVChipkaart(ov);
            if (!producten.isEmpty()) {
                for (Product p : producten) {
                    ov.setProducten(p);
                }
            }

            OVChipkaarten.add(ov);
        }

        ps.close();
        rs.close();

        return OVChipkaarten;
    }

    // Haalt alle OV Chipkaarten op uit de database
    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        // Maakt een query
        String query = "SELECT * FROM ov_chipkaart";

        // Voert de query uit op de database
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<OVChipkaart> OVChipkaarten = new ArrayList<>();

        // Als de select wat opbrengt
        while (rs.next()) {
            int nummer = rs.getInt(1);
            Date geldig_tot = rs.getDate(2);
            int klasse = rs.getInt(3);
            Double saldo = rs.getDouble(4);
            int reiziger_id = rs.getInt(5);

            // Zoekt een reiziger aan de hand van zijn ID en zet hem in een lijst
            // Je hebt namelijk een reiziger object nodig om een OV Chipkaart object aan te maken
            List<Reiziger> lijst = rdao.findAll();
            for (var r : lijst) {
                if (r.getId() == reiziger_id) {
                    // Zet de OV chipkaart in een object
                    OVChipkaart ov = new OVChipkaart(nummer, geldig_tot, klasse, saldo, r);

                    // Set de producten van de OV chipkaart
                    List<Product> producten = pdao.findByOVChipkaart(ov);
                    if (!producten.isEmpty()) {
                        for (Product p : producten) {
                            ov.setProducten(p);
                        }
                    }

                    OVChipkaarten.add(ov);
                }
            }
        }

        ps.close();
        rs.close();

        return OVChipkaarten;
    }

    public void setRdao(ReizigerDAOPsql rdao) {
        this.rdao = rdao;
    }

    public void setPdao(ProductDAOPsql pdao) {
        this.pdao = pdao;
    }
}
