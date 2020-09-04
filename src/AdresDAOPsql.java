import dbconnection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private Connection connection;

    @Override
    public boolean save(Adres adres) {
        try {
            this.connection = DBConnection.getConnection();

            int id = adres.getId();
            String postcode = adres.getPostcode();
            String huisnummer = adres.getHuisnummer();
            String straat = adres.getStraat();
            String woonplaats = adres.getWoonplaats();
            int reiziger_id = adres.getReiziger_id();

            Statement stmt = connection.createStatement();

            stmt.executeUpdate("INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (" + id + ", '" + postcode + "', '" + huisnummer + "', '" + straat + "', '" + woonplaats + "', " + reiziger_id + ")");

            connection.close();
            stmt.close();

            return true;
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            this.connection = DBConnection.getConnection();

            int id = adres.getId();
            String postcode = adres.getPostcode();
            String huisnummer = adres.getHuisnummer();
            String straat = adres.getStraat();
            String woonplaats = adres.getWoonplaats();
            int reiziger_id = adres.getReiziger_id();

            Statement stmt = connection.createStatement();

            stmt.executeUpdate("UPDATE adres SET adres_id = " + id + ", postcode = '" + postcode + "', huisnummer = '" + huisnummer + "', straat = '" + straat + "', woonplaats = '" + woonplaats + "', reiziger_id = " + reiziger_id + " WHERE adres_id = " + id);

            connection.close();
            stmt.close();

            return true;
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            this.connection = DBConnection.getConnection();

            int id = adres.getId();

            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM adres WHERE adres_id = " + id);

            connection.close();
            stmt.close();

            return true;
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return false;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            this.connection = DBConnection.getConnection();

            String query = "SELECT * FROM adres WHERE reiziger_id = " + reiziger.getId();

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                String postcode = rs.getString(2);
                String huisnummer = rs.getString(3);
                String straat = rs.getString(4);
                String woonplaats = rs.getString(5);
                int reiziger_id = rs.getInt(6);

                Adres a = new Adres(id, postcode, huisnummer, straat, woonplaats, reiziger_id);

                connection.close();
                ps.close();
                rs.close();

                return a;
            }
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        try {
            this.connection = DBConnection.getConnection();

            String query = "SELECT * FROM adres";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List<Adres> lijst = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt(1);
                String postcode = rs.getString(2);
                String huisnummer = rs.getString(3);
                String straat = rs.getString(4);
                String woonplaats = rs.getString(5);
                int reiziger_id = rs.getInt(6);

                Adres a = new Adres(id, postcode, huisnummer, straat, woonplaats, reiziger_id);

                lijst.add(a);
            }

            connection.close();
            ps.close();
            rs.close();

            return lijst;
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
