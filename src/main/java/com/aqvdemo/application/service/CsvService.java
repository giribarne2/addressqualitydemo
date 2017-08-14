package com.aqvdemo.application.service;
 
import com.aqvdemo.application.objects.*;
 
public interface CsvService {
 
    CsvFile findById(long fileid);
 
    void saveFile(CsvFile csvFile);
}