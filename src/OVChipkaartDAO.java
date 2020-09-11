import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ov) throws SQLException;

    boolean update(OVChipkaart ov) throws SQLException;

    boolean delete(OVChipkaart ov) throws SQLException;

    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;

    List<OVChipkaart> findAll() throws SQLException;
}
