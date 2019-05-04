package com.example.demo.dao;

import java.util.ArrayList;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Graduate;

@Repository
public interface ApplyDao extends JpaRepository<Apply,Integer>{
	
	public Apply findApplyByGraduateidAndJobid(Integer graduateid,Integer jobid);
    public Apply findApplyJobById(Integer id);
    public ArrayList<Apply> findApplysByRecruit(Integer recruit,Sort sort);	
    public ArrayList<Apply> findAllByGraduateid(Integer graduateid,Sort sort);	
    @Query(value = "select distinct jobid from Apply where recruit =:recruit and refuse is null")
    public ArrayList<Integer> findAllByRecruit(@Param("recruit")Integer recruit,Sort sort);	
    public ArrayList<Apply> findApplysByJobid(Integer Jobid,Sort sort);
	public ArrayList<Apply> findApplyListByGraduateidAndJobid(Integer graduateid, Integer jobid);
	@Query(value = "select distinct graduateid from Apply where jobid =:jobid")
	public ArrayList<Integer> findAllByJobid(@Param("jobid")Integer jobid, Sort sort);
	@Modifying
	@Query(value = "delete from Apply where graduateid=:graduateid and jobid =:jobid")
	public void deleteByGraduateidAndJobid(@Param("graduateid")Integer graduateid, @Param("jobid")Integer jobid);
	@Query(value = "select distinct graduateid from Apply where jobid =:jobid and refuse is null")
	public ArrayList<Integer> findAllByJobidAndRefuseIsNull(@Param("jobid")Integer jobid,Sort sort);

}
