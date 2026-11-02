import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import com.j256.ormlite.table.TableUtils;
import java.util.List;
import java.util.ArrayList;

public class MesaRepository{
    private static Database database;
    private static Dao<Mesa, Integer> dao;
    private List<Mesa> loadedMesas;
    private Mesa loadedMesa;
    
    public MesaRepository(Database database){
        MesaRepository.setDatabase(database);
        loadedMesas = new ArrayList<Mesa>();
        
    }
    public static void setDatabase(Database database){
        MesaRepository.database = database;
        try{
            dao = DaoManager.createDao(database.getConnection(), Mesa.class);
            TableUtils.createTableIfNotExists(database.getConnection(), Mesa.class);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    public Mesa create(Mesa mesa) {
        int nrows = 0;
        try {
            nrows = dao.create(mesa);
            if ( nrows == 0 )
                throw new SQLException("Error: object not saved");
            this.loadedMesa = mesa;
            loadedMesas.add(mesa);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return mesa;
    }
    public Mesa loadFromId(int id) {
        try {
            this.loadedMesa = dao.queryForId(id);
            if (this.loadedMesa != null)
                this.loadedMesas.add(this.loadedMesa);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedMesa;
    }    
    
    public List<Mesa> loadAll() {
        try {
            this.loadedMesas = dao.queryForAll();

            if (this.loadedMesas.size() != 0)
                this.loadedMesa = this.loadedMesas.get(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return this.loadedMesas;
    }
}