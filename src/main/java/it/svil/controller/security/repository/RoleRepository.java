package it.svil.controller.security.repository;

import it.svil.controller.security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
}
