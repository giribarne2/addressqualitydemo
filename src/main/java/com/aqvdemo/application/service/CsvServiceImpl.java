package com.aqvdemo.application.service;
 
import com.aqvdemo.application.objects.*;
import com.aqvdemo.application.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
 
 
@Service("csvService")
@Transactional
public class CsvServiceImpl implements CsvService{
 
    @Autowired
    private CsvRepository csvRepository;
 
    public CsvFile findById(long fileid) {
        return csvRepository.findOne(fileid);
    }
 
    public void saveFile(CsvFile csvFile) {
    	csvRepository.save(csvFile);
    }
}