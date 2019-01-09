package com.gold.service.impl;

import com.gold.model.Role;
import com.gold.repository.RoleRepository;
import com.gold.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public Role findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void addEntity(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void removeEntity(Long id) {
        roleRepository.deleteById(id);
    }

//    TODO
    @Override
    public void updateEntity(Long id, Role role) {
       Role oldRole = findById(id);

        if (oldRole != null) {

        }
    }
}
