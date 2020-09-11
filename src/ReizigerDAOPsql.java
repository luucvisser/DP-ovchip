import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    protected Connection connection;

    public ReizigerDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = reiziger.getTussenvoegsel();
        String achternaam = reiziger.getAchternaam();
        Date geboortedatum = reiziger.getGeboortedatum();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (" + id + ", '" + voorletters + "', '" + tussenvoegsel + "', '" + achternaam + "', '" + geboortedatum + "')");


        AdresDAOPsql adao = new AdresDAOPsql(connection);
        Adres adres = adao.findByReiziger(reiziger);
        reiziger.setAdres(adres);

        OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
        List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(reiziger);
        for (OVChipkaart ov : OVChipkaarten) {
            reiziger.setOVChipkaarten(ov);
        }

        stmt.close();

        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        String voorletters = reiziger.getVoorletters();
        String tussenvoegsel = reiziger.getTussenvoegsel();
        String achternaam = reiziger.getAchternaam();
        Date geboortedatum = reiziger.getGeboortedatum();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("UPDATE reiziger SET reiziger_id = " + id + ", voorletters = '" + voorletters + "', tussenvoegsel = '" + tussenvoegsel + "', achternaam = '" + achternaam + "', geboortedatum = '" + geboortedatum + "' WHERE reiziger_id = " + id);

        AdresDAOPsql adao = new AdresDAOPsql(connection);
        Adres adres = adao.findByReiziger(reiziger);
        reiziger.setAdres(adres);

        OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
        List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(reiziger);
        for (OVChipkaart ov : OVChipkaarten) {
            reiziger.setOVChipkaarten(ov);
        }

        stmt.close();

        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("DELETE FROM reiziger WHERE reiziger_id = " + id);

        stmt.close();

        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        String query = "SELECT * FROM reiziger WHERE reiziger_id = " + id;

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int reiziger_id = rs.getInt(1);
            String voorletter = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);

            Reiziger r = new Reiziger(reiziger_id, voorletter, tussenvoegsel, achternaam, geboortedatum);

            AdresDAOPsql adao = new AdresDAOPsql(connection);
            Adres adres = adao.findByReiziger(r);
            r.setAdres(adres);

            OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
            List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(r);
            for (OVChipkaart ov : OVChipkaarten) {
                r.setOVChipkaarten(ov);
            }

            ps.close();
            rs.close();

            return r;
        }
        else {
                return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        String query = "SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "'";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Reiziger> lijst = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String voorletter = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);

            Reiziger r = new Reiziger(id, voorletter, tussenvoegsel, achternaam, geboortedatum);

            AdresDAOPsql adao = new AdresDAOPsql(connection);
            Adres adres = adao.findByReiziger(r);
            r.setAdres(adres);

            OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
            List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(r);
            for (OVChipkaart ov : OVChipkaarten) {
                r.setOVChipkaarten(ov);
            }

            lijst.add(r);
        }

        ps.close();
        rs.close();

        return lijst;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        String query = "SELECT * FROM reiziger";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Reiziger> lijst = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String voorletter = rs.getString(2);
            String tussenvoegsel = rs.getString(3);
            String achternaam = rs.getString(4);
            Date geboortedatum = rs.getDate(5);

            Reiziger r = new Reiziger(id, voorletter, tussenvoegsel, achternaam, geboortedatum);

            AdresDAOPsql adao = new AdresDAOPsql(connection);
            Adres adres = adao.findByReiziger(r);
            r.setAdres(adres);

            OVChipkaartDAOPsql ovdao = new OVChipkaartDAOPsql(connection);
            List<OVChipkaart> OVChipkaarten = ovdao.findByReiziger(r);
            for (OVChipkaart ov : OVChipkaarten) {
                r.setOVChipkaarten(ov);
            }

            lijst.add(r);
        }

        ps.close();
        rs.close();

        return lijst;
    }
}
