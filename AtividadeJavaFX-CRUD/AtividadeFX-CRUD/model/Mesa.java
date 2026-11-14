package model;

import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;

@DatabaseTable(tableName="mesa")
public class Mesa{
    @DatabaseField(generatedId = true)
    private int id;
    
    @DatabaseField(dataType=DataType.STRING)
    private String status;
    
    public void  setId(int id){
        this.id = id;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public int getId(){
        return this.id;
    }
    public String getStatus(){
        return this.status;
    }
}