package com.example.elite.repository;

import com.example.elite.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.management.relation.RoleNotFoundException;

public interface RoleRepository extends JpaRepository<Role,Integer> {
  public Role findByRoleName(String name);
}
