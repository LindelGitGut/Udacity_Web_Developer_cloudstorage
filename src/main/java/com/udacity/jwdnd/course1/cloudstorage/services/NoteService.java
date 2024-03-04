package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    UserService userService;
    NoteMapper noteMapper;

    public NoteService(UserService userService, NoteMapper noteMapper) {
        this.userService = userService;
        this.noteMapper = noteMapper;
    }

    Integer createNote(NoteModel note){
        return noteMapper.createNote(note);
    }

    Integer changeNote(NoteModel note){
        return noteMapper.updateNote(note);
    }

    List<NoteModel> getAllUserNotes(){
        return noteMapper.getAllUserNotes(userService.getCurrentUser().getUserid().toString());
    }

    NoteModel getNote(String noteid){
        return noteMapper.getNote(noteid, userService.getCurrentUser().getUserid().toString());
    }

    boolean deleteNote(String noteid){
        try {
            noteMapper.deleteNote(userService.getCurrentUser().getUserid().toString(), noteid);
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

}
