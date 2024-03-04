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
    String addNote(Model model, @ModelAttribute("noteModel") NoteModel noteModel){

        System.out.println("Debug Note Controller Notemodel: " +noteModel.getNotetitle() + "Inhalt:" +noteModel.getNotedescription());

        //TODO add checks
        noteService.createNote(noteModel);

        //DEBUG

        List<NoteModel> allNotes = noteService.getAllUserNotes();

        if (allNotes.isEmpty()){
            System.out.println("NoteListe ist leer get funktion überprüfen!");
        }

        for (NoteModel note: allNotes
             ) {
            System.out.println("Debug Notes Controller: Bezeichnung:" + note.getNotetitle() + " Inhalt: " +note.getNotedescription() );

        }

        model.addAttribute("allNotes", noteService.getAllUserNotes());


        return "home";
    }

    @GetMapping("/home/note")
    String getNote(@ModelAttribute("noteModel") NoteModel noteModel, Model model, @RequestParam(value = "deleteNoteID", required = false) String deleteNoteID){

        //Edit Note

        if(deleteNoteID != null){
            System.out.println("Debug deleteNoteID: " + deleteNoteID);
            noteService.deleteNote(deleteNoteID);
        }

        model.addAttribute("allNotes", noteService.getAllUserNotes());
        //TODO abfragen ob erfolgreich oder nicht und entsprechend der result Page anzeigen

        return "home";
    }

}



