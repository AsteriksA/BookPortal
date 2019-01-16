package com.gold.service.impl;

import com.gold.dto.RoleDto;
import com.gold.model.Role;
import com.gold.repository.RoleRepository;
import com.gold.service.interfaces.RoleService;
import com.gold.util.EntityUtils;
import com.gold.util.MapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    private MapperUtils mapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, MapperUtils mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RoleDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> mapper.convertToDto(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoleDto findByName(String name) {
        Role role = roleRepository.findByName(name);
        return mapper.convertToDto(role, RoleDto.class);
    }

    @Override
    public RoleDto findById(Long id) {
        Role role = getEntity(id);
        return mapper.convertToDto(role, RoleDto.class);
    }

    @Override
    @Transactional
    public void add(RoleDto role) {
        Role entity = mapper.convertToEntity(role, Role.class);
        roleRepository.save(entity);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        roleRepository.deleteById(id);
    }

//    TODO
    @Override
    public void update(Long id, RoleDto role) {
        Role entity = getEntity(id);
        EntityUtils.checkNull(entity);
        mapper.convertToEntity(role, entity);
        roleRepository.save(entity);
    }

    private Role getEntity(Long id) {
        return roleRepository.findById(id).orElse(null);
    }


}
