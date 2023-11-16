package com.capstone.datamate.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.datamate.Message.ResponseMessage;
import com.capstone.datamate.Message.ResponseTemplate;
import com.capstone.datamate.Service.TemplateService;

@CrossOrigin("https://data-mate-frontend.vercel.app/")
@RestController
public class TemplateController {
  @Autowired
  TemplateService tempServ;

  @PostMapping("/downloadTemplate/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
       tempServ.store(file);
        String message = file.getOriginalFilename() + " successfully uploaded!" ;
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    } catch (Exception e) {
        String message = file.getOriginalFilename() + " could not be uploaded!" + e.getMessage();
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
    }
  }

//  @GetMapping("/downloadTemplate/{id}")
//  public ResponseEntity<byte[]> downloadFile(@PathVariable int id) {
//    TemplateEntity temp = tempServ.getTemplate(id);
//    return ResponseEntity.ok()
//        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + temp.getTemplateName() + "\"")
//        .body(temp.getData());
//  }
  
  @GetMapping("/downloadTemplate/{id}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable int id) {
      ResponseEntity<byte[]> response = tempServ.downloadFile(id);
      if (response.getStatusCode() == HttpStatus.OK) {
          return response;
      } else {
          return ResponseEntity.notFound().build();
      }
  }


        //get list of templates
      @GetMapping("/templates")
      public ResponseEntity<List<ResponseTemplate>> getListFiles() {
        List<ResponseTemplate> temps = tempServ.getAllTemplates().map(dbTemplate -> {
          return new ResponseTemplate(
              dbTemplate.getTemplateId(),
              dbTemplate.getTemplateName()
              );
        }).collect(Collectors.toList());
    
        return ResponseEntity.status(HttpStatus.OK).body(temps);
      }
      
      // get recent downloads
      @GetMapping("/recentDownloads")
      public ResponseEntity<List<ResponseTemplate>> getRecentDownloads(
              @RequestParam(defaultValue = "5") int limit) {
          List<ResponseTemplate> recentDownloads = tempServ.getRecentDownloads(limit)
                  .stream()
                  .map(dbTemplate -> new ResponseTemplate(
                          dbTemplate.getTemplateId(),
                          dbTemplate.getTemplateName()
                  ))
                  .collect(Collectors.toList());

          return ResponseEntity.status(HttpStatus.OK).body(recentDownloads);
      }
}
