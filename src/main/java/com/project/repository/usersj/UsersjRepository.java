package com.project.repository.usersj;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.models.Usersj;

public interface UsersjRepository extends JpaRepository<Usersj,Integer> {
    Usersj findByUsername(String username);
}



