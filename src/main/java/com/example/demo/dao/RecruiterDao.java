package com.example.demo.dao;

import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Recruiter;

@Repository
public interface RecruiterDao extends JpaRepository<Recruiter,Integer>{
	
    public Recruiter findByUsernameAndPassword(String username, String password);
    public Recruiter findRecruiterById(Integer id);
    public ArrayList<Recruiter> findRecruitersByUsername(String username);	
	public ArrayList<Recruiter> findRecruitersByUptimeIsNotNullAndYesornoIsNull(Sort sort);
}
