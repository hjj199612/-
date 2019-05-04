package com.example.demo.dao;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Job;

@Repository
public interface JobDao extends JpaRepository<Job,Integer>{
	
    public Job findJobById(Integer id);
    public ArrayList<Job> findJobsByRecruit(Integer recruit,Sort sort);	
    public ArrayList<Job> findJobsByNameLike(String inform,Sort sort);	
    public void deleteById(Integer id);
	public ArrayList<Job> findJobsByNameLikeOrCityLike(String inform1,String inform2, Sort sort);
	public Page<Job> findAll(Pageable pageable);
}
