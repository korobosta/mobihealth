package ke.co.neta.health.health.repositories;

 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ke.co.neta.health.health.models.User;
 
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    public User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = ?1")
    public User findByPhoneNumber(String phoneNumber);
 
}
