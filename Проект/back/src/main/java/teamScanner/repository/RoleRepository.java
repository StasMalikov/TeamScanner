package teamScanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teamScanner.model.Role;


public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
