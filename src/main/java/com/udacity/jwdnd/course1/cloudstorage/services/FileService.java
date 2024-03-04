package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    FileMapper fileMapper;
    UserService userService;

    public FileService(FileMapper fileMapper, UserService userService) {
        this.fileMapper = fileMapper;
        this.userService = userService;
    }

    public Integer createFile(FileModel file){

        UserModel user = userService.getCurrentUser();
        if(fileMapper.getFileByName(file.getFilename(), user.getUserid().toString())== null){
        return fileMapper.createFile(new FileModel(null,file.getFilename(),file.getContenttype(),file.getFilesize(),user.getUserid(), file.getFiledata()));}
        else return null;
    }

    public FileModel getFile(String fileid){
        UserModel user = userService.getCurrentUser();
        return fileMapper.getFile(fileid, user.getUserid().toString());
    }

    public FileModel getFileByName(String filename){
        UserModel user = userService.getCurrentUser();
        return fileMapper.getFileByName(filename, user.getUserid().toString());
    }

    public Integer changeFile(FileModel file){
        UserModel user = userService.getCurrentUser();
        return fileMapper.updateFile(file, user.getUserid().toString());
    }

    public List<FileModel> getCurretUserFiles(){
        UserModel curretUser = userService.getCurrentUser();
        return this.fileMapper.getAllUserFiles(curretUser.getUserid().toString());
    }
    public Integer removeFile(FileModel file){
        return fileMapper.removeFile(file);
    }
}
