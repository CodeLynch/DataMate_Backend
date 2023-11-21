package com.capstone.datamate.Entity;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_Table")
public class TableEntity {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tableId;
    
    @Column
    private String tableName;
    
    @ManyToOne(targetEntity = DatabaseEntity.class, cascade=CascadeType.MERGE)
    @JoinColumn(name = "databaseId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DatabaseEntity database;
    
    @ManyToOne(targetEntity = UserEntity.class, cascade=CascadeType.MERGE)
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @Column
    private List<String> columns;

    
    public TableEntity() {
    }

    

 


    public TableEntity(int tableId, String tableName, DatabaseEntity database, UserEntity user, List<String> columns) {
        this.tableId = tableId;
        this.tableName = tableName;
        this.database = database;
        this.user = user;
        this.columns = columns;
    }






    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DatabaseEntity getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseEntity database) {
        this.database = database;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }



    

    
}
