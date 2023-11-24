package com.capstone.datamate.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.datamate.Entity.FileActivityLogEntity;
import com.capstone.datamate.Entity.FileEntity;
import com.capstone.datamate.Entity.UserEntity;
import com.capstone.datamate.Repository.FileActivityLogRepository;
import com.capstone.datamate.Repository.FileRepository;
import com.capstone.datamate.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileServiceImpl implements FileService {

  @Autowired
  FileRepository fileRepo;
  
  @Autowired
  FileActivityLogRepository logRepository;
  

  @Autowired
  UserRepository userRepo;
 //creating the uploads path    
 private final Path root = Paths.get("uploads");

 //initializing of the path
  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  
  //updated save file in db that handle duplicate files
  public FileEntity store(MultipartFile file, int userId) throws IOException {
      String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
      String fileName = originalFileName;
      int fileCount = 1;

      while (fileRepo.existsByFileName(fileName)) {
          fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                  + " (" + fileCount + ")" + originalFileName.substring(originalFileName.lastIndexOf('.'));
          fileCount++;
      }
      UserEntity user = userRepo.findById(userId).orElse(null);

      if (user != null) {
          FileEntity fileEntity = new FileEntity(fileName, file.getSize(), file.getBytes());
          fileEntity.setUser(user);
          FileEntity savedFile = fileRepo.save(fileEntity);
          
          String activity = fileEntity.getFileName() + " uploaded";
          FileActivityLogEntity log = new FileActivityLogEntity(savedFile.getFileName(), user, activity);
          logRepository.save(log);
          
          return savedFile;
      } else {
          throw new EntityNotFoundException("User with ID " + userId + " not found");
      }

  }



  //fetch files not deleted and is uploaded by the user
//  public List<FileEntity> getFilesByUserId(int userId) {
//	    return fileRepo.findFilesByUserIdAndIsNotDeleted(userId);
//	}
  
  //updated
  public List<FileEntity> getFilesByUserId(int userId) {
	    return fileRepo.findFilesByUserUserIdAndIsdeletedFalse(userId);
	}



  //update file in db
  public FileEntity updateFile(int id, MultipartFile file) throws IOException {
    FileEntity fileToUpdate = fileRepo.findById(id).get();
    if(fileToUpdate != null){
      String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      fileToUpdate.setFileName(fileName);
      fileToUpdate.setFileSize(file.getSize());
      fileToUpdate.setData(file.getBytes());
      return fileRepo.save(fileToUpdate);
    }
      return fileToUpdate;
  }

  //download file data
  public FileEntity getFile(int id) {
    return fileRepo.findById(id).get();
  }
  
  //retrieve a list of all file
  public Stream<FileEntity> getAllFiles() {
    return fileRepo.findAll().stream();
  }

  //retireve deleted files
  public List<FileEntity> getDeletedFiles(){
    return fileRepo.findByIsdeleted(true);
  }

//  //permanently remove file
//	public void permanentlyDeleteFile(int id) {
//	   Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);
//	
//	   if (optionalFileEntity.isPresent()) {
//	       FileEntity fileEntity = optionalFileEntity.get();
//	       fileRepo.delete(fileEntity);
//	   } else {
//	       throw new EntityNotFoundException("File with id " + id + " not found");
//	   }
//	}
  
	public void permanentlyDeleteFile(int id) {
	  Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);
	
	  if (optionalFileEntity.isPresent()) {
	      FileEntity fileEntity = optionalFileEntity.get();
	
	      if (fileEntity.getIsdeleted()) {
	          UserEntity user = fileEntity.getUser();
	
	          String activity = fileEntity.getFileName() + " deleted";
	          FileActivityLogEntity log = new FileActivityLogEntity(fileEntity.getFileName() , user, activity);
	          logRepository.save(log);
	
	          fileRepo.delete(fileEntity);
	      } else {
	          throw new EntityNotFoundException("File with id " + id + " is not marked as deleted and cannot be permanently deleted.");
	      }
	  } else {
	      throw new EntityNotFoundException("File with id " + id + " not found");
	  }
	}


	// restore file by id
//	public FileEntity restoreFile(int id) {
//	   Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);
//	
//	   if (optionalFileEntity.isPresent()) {
//	       FileEntity fileEntity = optionalFileEntity.get();
//	
//	       fileEntity.setIsdeleted(false);
//	
//	       return fileRepo.save(fileEntity);
//	   } else {
//	       throw new EntityNotFoundException("File with id " + id + " not found");
//	   }
//	}
	
	public FileEntity restoreFile(int id) {
	   Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);

	   if (optionalFileEntity.isPresent()) {
	       FileEntity fileEntity = optionalFileEntity.get();

	       if (fileEntity.getIsdeleted()) {
	           fileEntity.setIsdeleted(false);

	           UserEntity user = fileEntity.getUser();
	           
	           String activity = "Restored " + fileEntity.getFileName();
	           
	           FileActivityLogEntity log = new FileActivityLogEntity(fileEntity.getFileName(), user, activity);
	           logRepository.save(log);

	           return fileRepo.save(fileEntity);
	       } else {
	           throw new EntityNotFoundException("File with id " + id + " was not deleted and cannot be restored.");
	       }
	   } else {
	       throw new EntityNotFoundException("File with id " + id + " not found");
	   }
	}


	
	// get deleted files by user id
	public List<FileEntity> getDeletedFilesById(int userId) {
	    return fileRepo.findDeletedFilesByUserId(userId);
	}

//  @Override
//  public void deleteFile(int id) {
//      Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);
//
//      if (optionalFileEntity.isPresent()) {
//          FileEntity fileEntity = optionalFileEntity.get();
//
//          // Set the isdeleted flag to true
//          fileEntity.setIsdeleted(true);
//
//          // Update the entity in the database
//          fileRepo.save(fileEntity);
//      } else {
//          throw new EntityNotFoundException("File with id " + id + " not found");
//      }
//  }
	
	// temporarily delete file by moving it to bin
	@Override
	public void deleteFile(int id) {
	   Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);

	   if (optionalFileEntity.isPresent()) {
	       FileEntity fileEntity = optionalFileEntity.get();

	       if (!fileEntity.getIsdeleted()) {
	           fileEntity.setIsdeleted(true);

	           UserEntity user = fileEntity.getUser();
	           	
	           String activity = fileEntity.getFileName() + " was moved to bin";
	           FileActivityLogEntity log = new FileActivityLogEntity(fileEntity.getFileName(), user, activity);
	           logRepository.save(log);

	           fileRepo.save(fileEntity);
	       } else {
	           throw new EntityNotFoundException("File with id " + id + " is already deleted.");
	       }
	   } else {
	       throw new EntityNotFoundException("File with id " + id + " not found");
	   }
	}


}