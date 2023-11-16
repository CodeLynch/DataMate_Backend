package com.capstone.datamate.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capstone.datamate.Entity.FileEntity;
import com.capstone.datamate.Message.ResponseFile;
import com.capstone.datamate.Service.FileService;

import jakarta.persistence.EntityNotFoundException;


@CrossOrigin("https://data-mate-frontend.vercel.app/")
@RestController
public class FileController {
    @Autowired
  FileService fileService;

   //updated uploaded by user
    @PostMapping("/upload")
    public FileEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") int userId) {
        try {
            // Ensure the userId is valid and authenticated

            FileEntity uploadedFile = fileService.store(file, userId);
            return uploadedFile;
        } catch (Exception e) {
            System.out.println("Upload Error");
            return null;
        }
    }


  @PutMapping("/updateFile/{id}")
  public String updateFile(@PathVariable int id, @RequestParam("file") MultipartFile file){
    try{
       FileEntity resFile =  fileService.updateFile(id, file);
       if(resFile != null){
          System.out.println("File Update:" + resFile.getFileName());
          return "Update Successful!";
        }else{
          return "Update Failed!";
        }
       }catch(Exception e){
        System.out.println("Update Error");
        return "Update Failed!";
       }
    }
     

  @GetMapping("/downloadFile/{id}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable int id) {
    FileEntity file = fileService.getFile(id);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
        .body(file.getData());
  }

  @GetMapping("/file")
  public FileEntity getFile(@RequestParam int id) {
    FileEntity file = fileService.getFile(id);
    if(file != null){
      return file;
    }else{
      System.out.println("File not found!");
      return null;
    }
  }


  @GetMapping("/files")
  public ResponseEntity<List<ResponseFile>> getListFiles() {
      List<ResponseFile> files = fileService.getAllFiles()
              .filter(dbFile -> !dbFile.getIsdeleted()) // Filter out deleted files
              .map(dbFile -> {
                  String fileDownloadUri = ServletUriComponentsBuilder
                          .fromCurrentContextPath()
                          .path("/files/")
                          .path(dbFile.getFileId()+"")
                          .toUriString();

                  return new ResponseFile(
                          dbFile.getFileId(),
                          dbFile.getFileName(),
                          dbFile.getFileSize(),
                          dbFile.getUploadDate(),
                          dbFile.getLatestDateModified(),
                          dbFile.getIsdeleted(),
                          fileDownloadUri
                  );
              }).collect(Collectors.toList());

      return ResponseEntity.status(HttpStatus.OK).body(files);
  }
  
  // get all deleted files
  @GetMapping("/getAllDeletedFiles")
  public List<FileEntity> getDeletedFiles(){
	  return fileService.getDeletedFiles();
  }
  
  // get deleted files by user id
  @GetMapping("/deletedFilesByUserId")
  public List<FileEntity> getDeletedFilesByUserId(@RequestParam int userId) {
      return fileService.getDeletedFilesById(userId);
  }
  
  // restore file by id
  @PutMapping("/restoreFile/{id}")
  public ResponseEntity<String> restoreFile(@PathVariable int id) {
      try {
          FileEntity restoredFile = fileService.restoreFile(id);
          if (restoredFile != null) {
              return ResponseEntity.ok("File restored successfully");
          } else {
              return ResponseEntity.notFound().build();
          }
      } catch (EntityNotFoundException e) {
          return ResponseEntity.notFound().build();
      }
  }


  @DeleteMapping("/deleteFilePermanent/{id}")
  public ResponseEntity<String> deleteFilePermanent(@PathVariable int id){
      try {
          fileService.permanentlyDeleteFile(id);
          return ResponseEntity.ok("File deleted permanently");
      } catch (EntityNotFoundException e) {
          return ResponseEntity.notFound().build();
      }
  }
  
  @DeleteMapping("/deleteFile/{id}")
  public ResponseEntity<String> deleteFile(@PathVariable int id) {
      FileEntity fileEntity = fileService.getFile(id);
      if (fileEntity == null) {
          return ResponseEntity.notFound().build();
      }

      // Delete the file entity from the database
      fileService.deleteFile(id);

      return ResponseEntity.ok("File deleted successfully");
  }

//  @GetMapping("/filesByUserId")
//  public List<FileEntity> getFilesByUserId(@RequestParam int userId) {
//      return fileService.getFilesByUserId(userId);
//  }
  @GetMapping("/filesByUserId/{userId}")
  public List<FileEntity> getFilesByUserId(@PathVariable int userId) {
      return fileService.getFilesByUserId(userId);
  }

  
}
