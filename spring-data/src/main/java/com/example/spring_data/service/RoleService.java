package com.example.spring_data.service;

import org.springframework.stereotype.Service;

import com.example.spring_data.exception.RoleNotFoundException;
import com.example.spring_data.model.Role;
import com.example.spring_data.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
    }

}
