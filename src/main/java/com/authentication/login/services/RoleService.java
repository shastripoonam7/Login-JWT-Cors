package com.authentication.login.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authentication.login.entities.Role;
import com.authentication.login.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	public Role findByRoleId(int id) {
		return roleRepository.findById(id).get();
	}
	
	public List<Role> findAllRoles(){
		return roleRepository.findAll();
	}

}
