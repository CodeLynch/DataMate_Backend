package com.capstone.datamate.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.datamate.Entity.DatabaseEntity;
import com.capstone.datamate.Repository.DatabaseRepository;

@Service
public class DatabaseService {
    
    @Autowired
    DatabaseRepository dbrepo;

    public DatabaseEntity postDatabase(DatabaseEntity db){
        return dbrepo.save(db);
    }

    public DatabaseEntity getDatabase(int id){
        return dbrepo.findById(id).get();
    }

    public DatabaseEntity getDatabaseByNameAndUserId(String name, int id){
        return dbrepo.findByDatabaseNameAndUserUserId(name, id);
    }

    //fetch
    public List<DatabaseEntity> getDatabaseByUser(int userId){
        return dbrepo.findByUserUserId(userId);
    }
    



    public String deleteDB(int id) {
		String msg;
		if(dbrepo.findById(id) != null) {
            DatabaseEntity db = dbrepo.findById(id).get();
            dbrepo.delete(db);
			msg = "Database ID number " + id + " deleted successfully!";
		}else {
			msg = "Database ID number " + id + " is NOT found!";
		}
        return msg;
    }

}
