package com.project.repository;

import com.project.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ole-espen.lundsor on 08/05/2017.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
}
