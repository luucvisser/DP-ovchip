import dbconnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class select {
    public static void main(String[] args) {
        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM reiziger";

            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            System.out.println("Alle reizigers:");
            while (rs.next()) {
                String voorletter = rs.getString(2);
                String tussenvoegsel = rs.getString(3);
                String achternaam = rs.getString(4);
                String geboortedatum = rs.getString(5);

                if (tussenvoegsel == null) {
                    tussenvoegsel = "";
                }
                else {
                    tussenvoegsel += " ";
                }

                System.out.println(" " + voorletter + ". " + tussenvoegsel + achternaam + " (" + geboortedatum + ")");

                connection.close();
                ps.close();
                rs.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
