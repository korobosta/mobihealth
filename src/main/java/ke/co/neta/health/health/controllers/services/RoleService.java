package ke.co.neta.health.health.controllers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ke.co.neta.health.health.models.Role;
import ke.co.neta.health.health.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Role> getRolesByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}

