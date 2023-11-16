package com.capstone.datamate.Repository;

import java.util.List;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capstone.datamate.Entity.FileEntity;

@Repository
public interface FileRepository extends JpaRepository<FileEntity,Integer>{
    public List<FileEntity> findByIsdeleted (boolean isdeleted);
    boolean existsByFileName(String fileName);
    List<FileEntity> findFilesByUserUserIdAndIsdeletedFalse(int userId);
    
    // get deleted files by user id
    @Query("SELECT f FROM FileEntity f WHERE f.user.id = :userId AND f.isdeleted = true")
    List<FileEntity> findDeletedFilesByUserId(int userId);

}

