package com.example.demo.dao;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Schoolman;

@Repository
public interface SchoolmanDao extends JpaRepository<Schoolman,Integer>{
	//AdministratorDao用于实现管理员登录
    public Schoolman findByUsernameAndPassword(String username, String password);
    public ArrayList<Schoolman> findSchoolmenByUsername(String username);	
    public Schoolman findSchoolmanById(Integer id);
    public ArrayList<Schoolman> findSchoolmenByUptimeIsNotNullAndYesornoIsNull(Sort sort);
}
