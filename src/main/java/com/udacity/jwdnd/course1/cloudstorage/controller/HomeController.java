package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller

public class HomeController {

    UserService userService;
    FileService fileService;

    public HomeController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    //handling File Upload
    @PostMapping
    @RequestMapping("/home")
    String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {

        if (file != null) {
            // Fileupload
            String filename = file.getOriginalFilename();
            String contentype = file.getContentType();
            String filesize = "" + file.getSize();
            Integer userid = userService.getCurrentUser().getUserid();
            byte[] BLOB = file.getBytes();

            if (fileService.createFile(new FileModel(null, filename, contentype, filesize, userid, BLOB)) != null) {

                model.addAttribute("allFiles", fileService.getCurretUserFiles());
                model.addAttribute("success", true);
                return "result";
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errormsg", "File with same name already exist, please try again with other Filename");
                return "result";
            }
        }


        //TODO Remove Home as placeholder
        return "home";
    }
    // End Fileupload


    @GetMapping
    @RequestMapping("/home")
    String getHomepage(Model model, @RequestParam(value = "deletefile", required = false) String deleteFileID) {


        if (deleteFileID != null) {
            //TODO Add cheack if File was Successfull or unsucessfull deleted, Foreward to Result Page

            System.out.println("Debug deleteFile Controller getriggert");
            fileService.removeFile(deleteFileID);
            model.addAttribute("allFiles", fileService.getCurretUserFiles());
        }


        model.addAttribute("allFiles", fileService.getCurretUserFiles());
        return "home";
    }

    @GetMapping
    @RequestMapping("/home/download")
    ResponseEntity getDownload(@RequestParam(value = "downloadfile", required = false) String downloadFileID) {

        if (downloadFileID != null) {

            System.out.println("Debug downloadfile getriggert");
            FileModel downloadfile = fileService.getFile(downloadFileID);
            if (downloadfile == null) {
                System.out.println("Debug Downloadfile, File konnte nicht gefunden werden");
                return ResponseEntity.notFound().build();
            } else {
                System.out.println("Debug Download File, File gefunden");
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(downloadfile.getContenttype()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadfile.getFilename() + "\"")
                        .body(new ByteArrayResource(downloadfile.getFiledata()));
            }
        }

        return ResponseEntity.notFound().build();
    }

}



