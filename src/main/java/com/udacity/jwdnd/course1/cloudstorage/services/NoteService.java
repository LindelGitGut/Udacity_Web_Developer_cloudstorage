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

    public Integer createNote(NoteModel note){
        return noteMapper.createNote(new NoteModel(null,note.getNotetitle(),note.getNotedescription(),userService.getCurrentUser().getUserid()));
    }

    public Integer changeNote(NoteModel note){
        note.setUserid(userService.getCurrentUser().getUserid());
        return noteMapper.updateNote(note);
    }

    public List<NoteModel> getAllUserNotes(){
        return noteMapper.getAllUserNotes(userService.getCurrentUser().getUserid().toString());
    }

    public NoteModel getNote(String noteid){
        return noteMapper.getNote(noteid, userService.getCurrentUser().getUserid().toString());
    }

    public Integer deleteNote(String noteid){
            return noteMapper.deleteNote(userService.getCurrentUser().getUserid().toString(), noteid);
    }

}
