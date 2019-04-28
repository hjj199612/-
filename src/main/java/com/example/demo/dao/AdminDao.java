package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin,Integer>{
	//AdministratorDao用于实现管理员登录
    public Admin findByAccountAndPassword(String account, String password);
    public Admin findAdminById(Integer id);
}
