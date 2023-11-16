package com.capstone.datamate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.datamate.Config.JdbcTemplateImpl;
import com.capstone.datamate.Entity.SqlRequestEntity;

@CrossOrigin("https://data-mate-frontend.vercel.app/")
@RestController
public class JDBCController {
    @Autowired
    JdbcTemplateImpl jdbc;
    // @PostMapping("/convert")
    // public void executeSQL(@RequestParam String tblName, @RequestParam String vals, @RequestParam int op){
    //     jdbc.executeSQL(tblName, vals, op);
    // }

    @PostMapping("/convert")
    public void executeSQL(@RequestBody SqlRequestEntity sql){
        jdbc.executeSQL(sql.getTblName(), sql.getVals(), sql.getOperation());
    }
}
