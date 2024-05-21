package ke.co.neta.health.health.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ke.co.neta.health.health.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByRoleName(String roleName);
}
