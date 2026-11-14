package view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class Mesa{
    private SimpleIntegerProperty id;
    private SimpleStringProperty status;
    
    public Mesa(int id, String status){
        this.id = new SimpleIntegerProperty(id);
        this.status = new SimpleStringProperty(status);
    }
    public int getId() {
    return this.id.get();
    }
    public void setId(int id) {
        this.id.set(id);
    }
    public String getStatus() {
        return this.status.get();
    }
    public void setStatus(String status) {
        this.status.set(status);
    }
}