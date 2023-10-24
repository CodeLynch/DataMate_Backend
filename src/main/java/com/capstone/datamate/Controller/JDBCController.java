package com.capstone.datamate.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.datamate.Config.JdbcTemplateImpl;

@CrossOrigin("http://localhost:3000/")
@RestController
public class JDBCController {
    @Autowired
    JdbcTemplateImpl jdbc;
    @PostMapping("/convert")
    public void executeSQL(@RequestParam String sql){
        jdbc.executeSQL(sql);
    }
}
