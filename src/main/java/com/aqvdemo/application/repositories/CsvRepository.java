package com.aqvdemo.application.repositories;

import com.aqvdemo.application.objects.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface CsvRepository extends JpaRepository<CsvFile, Long> {
 
    CsvFile findOne(long fileid);
 
}