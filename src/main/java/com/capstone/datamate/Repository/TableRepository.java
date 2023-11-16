package com.capstone.datamate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.datamate.Entity.TableEntity;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Integer>{
    public TableEntity findByTableName(String tblName);
    public List<TableEntity> findByDatabaseDatabaseId (int dbId);
}
