package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller

public class HomeController {

    UserService userService;
    FileService fileService;

    NoteService noteService;
    public HomeController(UserService userService, FileService fileService, NoteService noteService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
    }

    //handling File Upload
    @PostMapping
    @RequestMapping("/home")
    String fileUpload(@RequestParam("fileUpload") MultipartFile file, Model model,  @ModelAttribute("noteModel") NoteModel noteModel) throws IOException {
        model.addAttribute("allNotes", noteService.getAllUserNotes());
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


    @GetMapping("/home")
    String getHomepage(Model model, @RequestParam(value = "deletefile", required = false) String deleteFileID,  @ModelAttribute("noteModel") NoteModel noteModel) {
        model.addAttribute("allNotes", noteService.getAllUserNotes());

        if (deleteFileID != null) {
            //TODO Add cheack if File was Successfull or unsucessfull deleted, Foreward to Result Page

            System.out.println("Debug deleteFile Controller getriggert");
            fileService.removeFile(deleteFileID);
            model.addAttribute("allFiles", fileService.getCurretUserFiles());
        }


        model.addAttribute("allFiles", fileService.getCurretUserFiles());
        return "home";
    }

    @GetMapping("/home/download")

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

    //NOTES

    @PostMapping("/home/note")
    String addNote(Model model, @ModelAttribute("noteModel") NoteModel noteModel, @RequestParam(value = "edit", required = false) boolean edit){


        if (noteModel.getNoteid() == null){
            //Create new Note
            noteService.createNote(noteModel);
        }
        else {
            //Modify Note
            System.out.println("Controller Note ID vorhanden: " + noteModel.getNoteid());
            if (noteService.changeNote(noteModel)==0){
                System.out.println("Note wurde nicht richtig aktuallisiert");

                //TODO return errormsg
            }
            else {
                //TODO return successmsg
            }
        }
        model.addAttribute("allNotes", noteService.getAllUserNotes());
        return "home";
    }

    @GetMapping("/home/note")
    String getNote(@ModelAttribute("noteModel") NoteModel noteModel, Model model, @RequestParam(value = "deleteNoteID", required = false) String deleteNoteID){

        //DELETE Note if Null
        if(deleteNoteID != null){
          //  System.out.println("Debug deleteNoteID: " + deleteNoteID);
            if(noteService.deleteNote(deleteNoteID) == 0){
                model.addAttribute("error", "Could not Delete Note, please try again!");
            }
            else{
                model.addAttribute("success", "Note Successfully deleted!");
                //TODO return successmsg
                }
            }

        model.addAttribute("allNotes", noteService.getAllUserNotes());


        return "home";
    }

}



