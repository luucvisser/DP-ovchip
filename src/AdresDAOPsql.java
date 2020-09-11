import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    protected Connection connection;

    public AdresDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        int id = adres.getId();
        String postcode = adres.getPostcode();
        String huisnummer = adres.getHuisnummer();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();
        int reiziger_id = adres.getReiziger().getId();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (" + id + ", '" + postcode + "', '" + huisnummer + "', '" + straat + "', '" + woonplaats + "', " + reiziger_id + ")");

        stmt.close();

        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        int id = adres.getId();
        String postcode = adres.getPostcode();
        String huisnummer = adres.getHuisnummer();
        String straat = adres.getStraat();
        String woonplaats = adres.getWoonplaats();
        int reiziger_id = adres.getReiziger().getId();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("UPDATE adres SET adres_id = " + id + ", postcode = '" + postcode + "', huisnummer = '" + huisnummer + "', straat = '" + straat + "', woonplaats = '" + woonplaats + "', reiziger_id = " + reiziger_id + " WHERE adres_id = " + id);

        stmt.close();

        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        int id = adres.getId();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("DELETE FROM adres WHERE adres_id = " + id);

        stmt.close();

        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM adres WHERE reiziger_id = " + reiziger.getId();

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt(1);
            String postcode = rs.getString(2);
            String huisnummer = rs.getString(3);
            String straat = rs.getString(4);
            String woonplaats = rs.getString(5);

            Adres a = new Adres(id, postcode, huisnummer, straat, woonplaats, reiziger);

            ps.close();
            rs.close();

            return a;
        }
        else {
            return null;
        }
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        String query = "SELECT * FROM adres";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Adres> adressen = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt(1);
            String postcode = rs.getString(2);
            String huisnummer = rs.getString(3);
            String straat = rs.getString(4);
            String woonplaats = rs.getString(5);
            int reiziger_id = rs.getInt(6);

            ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
            List<Reiziger> lijst = rdao.findAll();
            for (var r : lijst) {
                if (r.getId() == reiziger_id) {
                    Adres a = new Adres(id, postcode, huisnummer, straat, woonplaats, r);
                    adressen.add(a);
                }
            }
        }

        ps.close();
        rs.close();

        return adressen;
    }
}
