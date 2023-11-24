package com.capstone.datamate.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.datamate.Entity.UserEntity;
import com.capstone.datamate.Entity.FileActivityLogEntity;
import com.capstone.datamate.Repository.FileActivityLogRepository;

@Service
public class FileActivityLogService {
	
	@Autowired
    FileActivityLogRepository logRepository;

    
    public FileActivityLogEntity logFileActivity(String file, UserEntity user, String activity) {
        FileActivityLogEntity log = new FileActivityLogEntity(file, user, activity);
        return logRepository.save(log);
    }
    
    
    public List<FileActivityLogEntity> getFileActivityLogsByUserId(int userId) {
        return logRepository.findByUserUserId(userId);
    }

}
