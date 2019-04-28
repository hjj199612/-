package com.example.demo.dao;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.School;

@Repository
public interface SchoolDao extends JpaRepository<School,Integer>{
    public ArrayList<School> findSchoolBySchool(String school);	
}
