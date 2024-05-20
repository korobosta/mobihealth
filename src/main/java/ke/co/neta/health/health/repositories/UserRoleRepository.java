package ke.co.neta.health.health.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ke.co.neta.health.health.models.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
