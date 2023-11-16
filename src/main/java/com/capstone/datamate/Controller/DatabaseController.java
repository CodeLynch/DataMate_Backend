package com.capstone.datamate.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.datamate.Entity.DatabaseEntity;
import com.capstone.datamate.Service.DatabaseService;

@CrossOrigin("https://data-mate-frontend.vercel.app/")
@RestController
public class DatabaseController {
    
    @Autowired
    DatabaseService dbServ;

    @PostMapping("/postDB")
    public DatabaseEntity postDB(@RequestBody DatabaseEntity db){
        return dbServ.postDatabase(db);
    }

    @GetMapping("/getDB")
    public DatabaseEntity getDB(@RequestParam int dbId){
        return dbServ.getDatabase(dbId);
    }

    @GetMapping("/getDBByNameAndID")
    public DatabaseEntity getDB(@RequestParam String dbName, @RequestParam int userid){
        return dbServ.getDatabaseByNameAndUserId(dbName, userid);
    }

    @GetMapping("/getUserDBs/{userId}")
    public List<DatabaseEntity> getUserDBs(@PathVariable int userId){
        return dbServ.getDatabaseByUser(userId);
    }
    


    @DeleteMapping("/deleteDB")
    public String deleteDB(@RequestParam int dbId){
        return dbServ.deleteDB(dbId);
    }
}
