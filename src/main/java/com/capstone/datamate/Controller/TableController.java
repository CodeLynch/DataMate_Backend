package com.capstone.datamate.Controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.datamate.Entity.TableEntity;
import com.capstone.datamate.Service.TableService;

@CrossOrigin("https://data-mate-frontend.vercel.app/")
@RestController
public class TableController {
    
    @Autowired
    TableService tserv;

    @PostMapping("/postTable")
    public TableEntity postTable(@RequestBody TableEntity tbl){
        return tserv.postTable(tbl);
    }

    @GetMapping("/getTable")
    public TableEntity getTable(@RequestParam String tblName){
        return tserv.getTable(tblName);
    }

    @GetMapping("/getTableByDB")
    public List<TableEntity> getDBTables(@RequestParam int dbId){
        return tserv.getTablesByDB(dbId);
    }

    @GetMapping("/getTableByName")
    public TableEntity getTableByName(@RequestParam String tblName){
        return tserv.getTable(tblName);
    }
    @GetMapping("/getTableData")
    public List<Object[]> getTblData(@RequestParam String tblName) throws SQLException{
        return tserv.executeNativeQuery(tblName);
    }

    
}
