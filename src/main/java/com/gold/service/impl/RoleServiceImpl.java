package com.gold.service.impl;

import com.gold.dto.Role;
import com.gold.model.RoleEntity;
import com.gold.repository.RoleRepository;
import com.gold.service.interfaces.RoleService;
import com.gold.util.EntityUtils;
import com.gold.util.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RoleServiceImpl /*implements RoleService*/ {

//    private final RoleRepository roleRepository;
//    private final EntityMapper mapper;
//
//    @Override
//    public List<Role> findAll() {
//        List<RoleEntity> roleEntities = roleRepository.findAll();
//        return mapper.convertToDto(roleEntities, Role.class);
//    }
//
//    @Override
//    public Role findByName(String name) {
//        RoleEntity roleEntity = roleRepository.findByName(name);
//        return mapper.convertToDto(roleEntity, Role.class);
//    }
//
//    @Override
//    public Role findOne(Long id) {
//        RoleEntity roleEntity = getEntity(id);
//        return mapper.convertToDto(roleEntity, Role.class);
//    }
//
//    @Override
//    @Transactional
//    public void add(Role role) {
//        RoleEntity entity = mapper.convertToEntity(role, RoleEntity.class);
//        roleRepository.save(entity);
//    }
//
//    @Override
//    @Transactional
//    public void remove(Long id) {
//        roleRepository.deleteById(id);
//    }
//
////    TODO
//    @Override
//    public void update(Long id, Role role) {
//        RoleEntity entity = getEntity(id);
//        EntityUtils.isNull(entity);
//        mapper.convertToEntity(role, entity);
//        roleRepository.save(entity);
//    }
//
//    private RoleEntity getEntity(Long id) {
//        return roleRepository.findById(id).orElse(null);
//    }
}
