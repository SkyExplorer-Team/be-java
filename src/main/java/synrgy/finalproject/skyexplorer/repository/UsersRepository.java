package synrgy.finalproject.skyexplorer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synrgy.finalproject.skyexplorer.model.entity.Users;

import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    Users findByEmail(String email);

    Users findByResetPasswordToken(String resetPasswordToken);
}
