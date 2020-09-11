import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection connection;

    public OVChipkaartDAOPsql(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(OVChipkaart ov) throws SQLException {
        int nummer = ov.getNummer();
        Date geldig_tot = ov.getGeldig_tot();
        int klasse = ov.getKlasse();
        Double saldo = ov.getSaldo();
        int reiziger_id = ov.getReiziger().getId();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (" + nummer + ", '" + geldig_tot + "', " + klasse + ", " + saldo + ", " + reiziger_id + ")");

        stmt.close();

        return true;
    }

    @Override
    public boolean update(OVChipkaart ov) throws SQLException {
        int nummer = ov.getNummer();
        Date geldig_tot = ov.getGeldig_tot();
        int klasse = ov.getKlasse();
        Double saldo = ov.getSaldo();
        int reiziger_id = ov.getReiziger().getId();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("UPDATE ov_chipkaart SET kaart_nummer = " + nummer + ", geldig_tot = '" + geldig_tot + "', klasse = " + klasse + ", saldo = " + saldo + ", reiziger_id = " + reiziger_id + " WHERE kaart_nummer = " + nummer);

        stmt.close();

        return true;
    }

    @Override
    public boolean delete(OVChipkaart ov) throws SQLException {
        int nummer = ov.getNummer();

        Statement stmt = connection.createStatement();

        stmt.executeUpdate("DELETE FROM ov_chipkaart WHERE kaart_nummer = " + nummer);

        stmt.close();

        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId();

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<OVChipkaart> OVChipkaarten = new ArrayList<>();

        while (rs.next()) {
            int nummer = rs.getInt(1);
            Date geldig_tot = rs.getDate(2);
            int klasse = rs.getInt(3);
            Double saldo = rs.getDouble(4);

            OVChipkaart ov = new OVChipkaart(nummer, geldig_tot, klasse, saldo, reiziger);

            OVChipkaarten.add(ov);
        }

        ps.close();
        rs.close();

        return OVChipkaarten;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        String query = "SELECT * FROM ov_chipkaart";

        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<OVChipkaart> OVChipkaarten = new ArrayList<>();

        while (rs.next()) {
            int nummer = rs.getInt(1);
            Date geldig_tot = rs.getDate(2);
            int klasse = rs.getInt(3);
            Double saldo = rs.getDouble(4);
            int reiziger_id = rs.getInt(5);

            ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
            List<Reiziger> lijst = rdao.findAll();
            for (var r : lijst) {
                if (r.getId() == reiziger_id) {
                    OVChipkaart ov = new OVChipkaart(nummer, geldig_tot, klasse, saldo, r);

                    OVChipkaarten.add(ov);
                }
            }
        }

        ps.close();
        rs.close();

        return OVChipkaarten;
    }
}
