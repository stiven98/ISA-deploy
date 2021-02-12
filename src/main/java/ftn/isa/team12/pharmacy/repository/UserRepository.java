package ftn.isa.team12.pharmacy.repository;
import ftn.isa.team12.pharmacy.domain.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select s from User s where s.loginInfo.email= ?1")
    User findUserByEmail (String email);
    User findByUserId(UUID id);


}
