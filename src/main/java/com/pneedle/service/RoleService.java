package com.pneedle.service;

import com.pneedle.model.Role;
import com.pneedle.model.User;
import com.pneedle.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("roleService")
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(@Qualifier("roleRepository") RoleRepository repository){
        this.roleRepository = repository;
    }

    public Role findByRole(String role){
        return roleRepository.findByRole(role);
    }

    public boolean hasRole(User user, String srole){
        Role role = roleRepository.findByRole(srole);
        for (Role r : user.getRoles()){
            if (r.equals(role)) return true;
        }
        return false;
    }
}
