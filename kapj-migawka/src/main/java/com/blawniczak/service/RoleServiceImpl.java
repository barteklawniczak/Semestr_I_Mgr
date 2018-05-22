package com.blawniczak.service;

import com.blawniczak.domain.Role;
import com.blawniczak.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(String name) { return roleRepository.findByName(name); }
}
