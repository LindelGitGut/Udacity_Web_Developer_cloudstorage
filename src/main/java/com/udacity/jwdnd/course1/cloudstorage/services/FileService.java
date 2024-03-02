package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer createFile(FileModel file){
       return fileMapper.createFile(file);
    }

    public FileModel getFile(String fileid){
       return fileMapper.getFile(fileid);
    }

    public Integer changeFile(FileModel file){
        return fileMapper.updateFile(file);
    }

    public Integer removeFile(FileModel file){
        return fileMapper.removeFile(file);
    }
}
