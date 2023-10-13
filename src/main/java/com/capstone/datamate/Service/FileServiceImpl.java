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
import org.springframework.util.StringUtils;
import com.capstone.datamate.Entity.FileEntity;
import com.capstone.datamate.Entity.UserEntity;
import com.capstone.datamate.Repository.FileRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class FileServiceImpl implements FileService {

  @Autowired
  FileRepository fileRepo;
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

  //save file in db
//  public FileEntity store(MultipartFile file) throws IOException {
//    String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//    FileEntity File = new FileEntity(fileName, file.getSize(), file.getBytes());
//
//    return fileRepo.save(File);
//  }
  
  //updated for duplicate files
  public FileEntity store(MultipartFile file) throws IOException {
	    String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
	    String fileName = getUniqueFileName(originalFileName);
	    FileEntity fileEntity = new FileEntity(fileName, file.getSize(), file.getBytes());

	    return fileRepo.save(fileEntity);
	}

	private String getUniqueFileName(String originalFileName) {
	    String fileName = originalFileName;
	    int counter = 1;
	    while (fileRepo.existsByFileName(fileName)) {
	        fileName = originalFileName + "(" + counter + ")";
	        counter++;
	    }
	    return fileName;
	}


  //fetch files not deleted and is uploaded by the user
  public List<FileEntity> getFilesByUserId(int userId) {
	    return fileRepo.findFilesByUserIdAndIsNotDeleted(userId);
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

  //permanently remove file
	public void permanentlyDeleteFile(int id) {
	   Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);
	
	   if (optionalFileEntity.isPresent()) {
	       FileEntity fileEntity = optionalFileEntity.get();
	       fileRepo.delete(fileEntity);
	   } else {
	       throw new EntityNotFoundException("File with id " + id + " not found");
	   }
	}

	// restore file by id
	public FileEntity restoreFile(int id) {
	   Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);
	
	   if (optionalFileEntity.isPresent()) {
	       FileEntity fileEntity = optionalFileEntity.get();
	
	       fileEntity.setIsdeleted(false);
	
	       return fileRepo.save(fileEntity);
	   } else {
	       throw new EntityNotFoundException("File with id " + id + " not found");
	   }
	}
	
	// get deleted files by user id
	public List<FileEntity> getDeletedFilesById(int userId) {
	    return fileRepo.findDeletedFilesByUserId(userId);
	}

  @Override
  public void deleteFile(int id) {
      Optional<FileEntity> optionalFileEntity = fileRepo.findById(id);

      if (optionalFileEntity.isPresent()) {
          FileEntity fileEntity = optionalFileEntity.get();

          // Set the isdeleted flag to true
          fileEntity.setIsdeleted(true);

          // Update the entity in the database
          fileRepo.save(fileEntity);
      } else {
          throw new EntityNotFoundException("File with id " + id + " not found");
      }
  }

}