package com.capstone.datamate.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.datamate.Entity.FileActivityLogEntity;
import com.capstone.datamate.Entity.FileEntity;
import com.capstone.datamate.Entity.UserEntity;
import com.capstone.datamate.Service.FileActivityLogService;
import com.capstone.datamate.Service.FileService;
import com.capstone.datamate.Service.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@CrossOrigin("https://data-mate-frontend.vercel.app/")
public class FileActivityLogController {
	
	@Autowired
   FileActivityLogService logService;
	
	@Autowired
	UserService userve;
	
	@Autowired
	FileService fileService;
	
	
	@PostMapping("/postFileLog")
	public FileActivityLogEntity logFileActivity(@RequestParam("fileId") int fileId, @RequestParam("userId") int userId, @RequestParam("activity") String activity) {
	    FileEntity file = fileService.getFile(fileId);
	    Optional<UserEntity> userOptional = userve.findByUserId(userId);
	    
	    if (file == null) {
	        throw new EntityNotFoundException("File with id " + fileId + " not found");
	    }

	    UserEntity user = userOptional.orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));

	    FileActivityLogEntity log = logService.logFileActivity(file.getFileName(), user, activity);

	    return log;
	}
	
	
	@GetMapping("/getFileActivityLogsByUserId")
    public List<FileActivityLogEntity> getFileActivityLogsByUserId(@RequestParam("userId") int userId) {
        List<FileActivityLogEntity> logs = logService.getFileActivityLogsByUserId(userId);
        return logs;
    }


}
