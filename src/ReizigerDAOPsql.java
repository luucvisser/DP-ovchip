import dbconnection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    private Connection connection;

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            this.connection = DBConnection.getConnection();

            int id = reiziger.getId();
            String voorletters = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();

            Statement stmt = connection.createStatement();

            stmt.executeUpdate("INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (" + id + ", '" + voorletters + "', '" + tussenvoegsel + "', '" + achternaam + "', '" + geboortedatum + "')");

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
    public boolean update(Reiziger reiziger) {
        try {
            this.connection = DBConnection.getConnection();

            int id = reiziger.getId();
            String voorletters = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();

            Statement stmt = connection.createStatement();

            stmt.executeUpdate("UPDATE reiziger SET reiziger_id = " + id + ", voorletters = '" + voorletters + "', tussenvoegsel = '" + tussenvoegsel + "', achternaam = '" + achternaam + "', geboortedatum = '" + geboortedatum + "' WHERE reiziger_id = " + id);

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
    public boolean delete(Reiziger reiziger) {
        try {
            this.connection = DBConnection.getConnection();

            int id = reiziger.getId();

            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM reiziger WHERE reiziger_id = " + id);

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
    public Reiziger findById(int id) {
        try {
            this.connection = DBConnection.getConnection();

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

                connection.close();
                ps.close();
                rs.close();

                return r;
            }
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            this.connection = DBConnection.getConnection();

            String query = "SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "'";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List<Reiziger> lijst = new ArrayList<>();

            while (rs.next()) {
                int reiziger_id = rs.getInt(1);
                String voorletter = rs.getString(2);
                String tussenvoegsel = rs.getString(3);
                String achternaam = rs.getString(4);
                Date geboortedatum = rs.getDate(5);

                Reiziger r = new Reiziger(reiziger_id, voorletter, tussenvoegsel, achternaam, geboortedatum);

                lijst.add(r);
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

    @Override
    public List<Reiziger> findAll() {
        try {
            this.connection = DBConnection.getConnection();

            String query = "SELECT * FROM reiziger";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            List<Reiziger> lijst = new ArrayList<>();

            while (rs.next()) {
                int reiziger_id = rs.getInt(1);
                String voorletter = rs.getString(2);
                String tussenvoegsel = rs.getString(3);
                String achternaam = rs.getString(4);
                Date geboortedatum = rs.getDate(5);

                Reiziger r = new Reiziger(reiziger_id, voorletter, tussenvoegsel, achternaam, geboortedatum);

                lijst.add(r);
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
