package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller

public class HomeController {

    UserService userService;
    FileService fileService;
    NoteService noteService;
    CredentialService credentialService;

    EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping
    @RequestMapping("/home")
    String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model,
                      @ModelAttribute("noteModel") NoteModel noteModel,
                        @ModelAttribute("credentialModel") CredentialModel credentialModel) throws IOException {

        model.addAttribute("allCreds", credentialService.getAllUserCredentials());
        model.addAttribute("allNotes", noteService.getAllUserNotes());
        model.addAttribute("encryptionService", encryptionService);


        //handling File Upload
        if (file != null) {
            // Fileupload
            String filename = file.getOriginalFilename();
            String contentype = file.getContentType();
            String filesize = "" + file.getSize();
            Integer userid = userService.getCurrentUser().getUserid();
            byte[] BLOB = file.getBytes();

            if (fileService.createFile(new FileModel(null, filename, contentype, filesize, userid, BLOB)) != null)

            {
                model.addAttribute("allFiles", fileService.getCurretUserFiles());
                model.addAttribute("success", true);
                return "result";
            } else {
                model.addAttribute("error", true);
                model.addAttribute("errormsg", "File with same name already exist, please try again with other Filename");
                return "result";
            }
        }

        return "home";
    }



    @GetMapping("/home")
    String getHomepage(Model model, @RequestParam(value = "deletefile", required = false) String deleteFileID,
                       @ModelAttribute("noteModel") NoteModel noteModel,
                        @ModelAttribute("credentialModel") CredentialModel credentialModel) {

        model.addAttribute("allNotes", noteService.getAllUserNotes());
        model.addAttribute("allCreds", credentialService.getAllUserCredentials());
        model.addAttribute("encryptionService", encryptionService);
        if (deleteFileID != null) {

            if(fileService.removeFile(deleteFileID) != 0){
                model.addAttribute("allFiles", fileService.getCurretUserFiles());
                model.addAttribute("success", true);
                return "result";
            }

            else{
                model.addAttribute("allFiles", fileService.getCurretUserFiles());
                model.addAttribute("error", true);
                model.addAttribute("errormsg", "Could not delete File, please try again!");
                return "result";
            }
        }

        model.addAttribute("allFiles", fileService.getCurretUserFiles());
        return "home";
    }

//Download Files
    @GetMapping("/home/download")
    ResponseEntity getDownload(@RequestParam(value = "downloadfile", required = false) String downloadFileID) {

        if (downloadFileID != null) {
            FileModel downloadfile = fileService.getFile(downloadFileID);
            if (downloadfile == null) {
                return new ResponseEntity<>("Could not download File, please try again.", HttpStatus.NOT_FOUND);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(downloadfile.getContenttype()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + downloadfile.getFilename() + "\"")
                        .body(new ByteArrayResource(downloadfile.getFiledata()));
            }
        }
         return new ResponseEntity<>("No File ID found in Request, please try again.", HttpStatus.NOT_FOUND);

    }

    //NOTES

    @PostMapping("/home/note")
    String addNote(Model model, @ModelAttribute("noteModel") NoteModel noteModel,
                   @ModelAttribute("credentialModel") CredentialModel credentialModel,
                    RedirectAttributes redirectAttributes
                   ) {


        //Create new Note

        if (noteModel.getNoteid() == null) {
            System.out.println("Creating note...");
           if(noteService.createNote(noteModel) == 0){
               model.addAttribute("error", true);
               model.addAttribute("errormsg", "Error creating note, please try again!");
               return "result";
           }

           else{
               model.addAttribute("success", true);
               model.addAttribute("allNotes", noteService.getAllUserNotes());
               return "result";
           }

        } else {
            //Modify Note
            if (noteService.changeNote(noteModel) == 0) {
                model.addAttribute("error", true);
                model.addAttribute("errormsg", "Could not change Note, please try again!");
                return "result";
            } else {
                model.addAttribute("success", true);
                model.addAttribute("allNotes", noteService.getAllUserNotes());
                return "result";
            }
        }

    }

    @GetMapping("/home/note")
    String getNote(@ModelAttribute("noteModel") NoteModel noteModel,
                   @ModelAttribute("credentialModel") CredentialModel credentialModel,
                   Model model, @RequestParam(value = "deleteNoteID", required = false) String deleteNoteID) {
        //Delete Note

        if (deleteNoteID != null) {
             System.out.println("Debug deleteNoteID: " + deleteNoteID);
            if (noteService.deleteNote(deleteNoteID) == 0) {
                model.addAttribute("error", true);
                model.addAttribute("errormsg", "Could not Delete Note, please try again!");
            } else {
                model.addAttribute("success", true);
            }
        }

        model.addAttribute("allNotes", noteService.getAllUserNotes());
        return "result";
    }

    @GetMapping("/home/credential")
    String deleteCredential(@ModelAttribute("credentialModel") CredentialModel credentialModel,
                          Model model, @RequestParam(value = "deleteCredentialID", required = false) String deleteCredentialID) {

        //Delete Credential

        model.addAttribute("encryptionService", encryptionService);
        System.out.println(deleteCredentialID != null);
        //DELETE Credential if not null
        if (deleteCredentialID != null){
            if(credentialService.deleteCredential(deleteCredentialID) == 0){

                model.addAttribute("error", true);
                model.addAttribute("errormsg", "Error deleting Credential, please try again");
            }
            else {
                model.addAttribute("success", true);
            }
        }

        else {model.addAttribute("error", true);
            model.addAttribute("errormsg", "No Message ID Provided, could not delete Credential");
        }

        return "result";
    }

    @PostMapping("/home/credential")
    String createCredentials(@ModelAttribute("credentialModel") CredentialModel credentialModel,
                          Model model) {
        // add Credential
        model.addAttribute("encryptionService", encryptionService);
        //Change Credential if id is not null
        if (credentialModel.getCredentialid() != null){
            if(credentialService.changeCredential(credentialModel) == 0){
                model.addAttribute("error", true);
                model.addAttribute("errormsg", "Error changing Credential, please try again");}
            else {model.addAttribute("success", "Credential successfully changed!");}
        }

        // Create new Credential
        else if(credentialService.createCredential(credentialModel) == 0){
            model.addAttribute("error", true);
            model.addAttribute("errormsg", "Error Creating Credential, please try again!");
        }
        else {model.addAttribute("success", "Credential successfully created!");}

        return "result";
    }

}



