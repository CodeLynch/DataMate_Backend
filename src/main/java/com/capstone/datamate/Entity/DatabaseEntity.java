package com.capstone.datamate.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_database")
public class DatabaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int databaseId;
    
    @Column
    private String databaseName;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    UserEntity user;

    public DatabaseEntity(){}

    public DatabaseEntity(int databaseId, String databaseName, UserEntity user) {
        this.databaseId = databaseId;
        this.databaseName = databaseName;
        this.user = user;
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
