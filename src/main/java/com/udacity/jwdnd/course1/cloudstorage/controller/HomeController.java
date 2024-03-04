package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    UserService userService;
    FileService fileService;

    public HomeController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    //handling File Upload
    @PostMapping
    String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {

        // Fileupload
        String filename = file.getOriginalFilename();
        String contentype = file.getContentType();
        String filesize = "" + file.getSize();
        Integer userid = userService.getCurrentUser().getUserid();
        byte[] BLOB = file.getBytes();

        if(fileService.createFile(new FileModel(null,filename,contentype,filesize,userid,BLOB)) != null){

            model.addAttribute("allFiles", fileService.getCurretUserFiles());
            model.addAttribute("success", true);
            return "result";
        }
        else {model.addAttribute("error", true);
        model.addAttribute("errormsg", "File with same name already exist, please try again with other Filename");
        return "result";}
    }


    @GetMapping
    String getHomepage(Model model){

        model.addAttribute("allFiles", fileService.getCurretUserFiles());
        return "home";
    }

}
