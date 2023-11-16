package com.example.maintenance.repository;

import com.example.maintenance.entity.Administrator;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("AdministratorRepository")
public interface AdministratorRepository extends BaseRepository<Administrator, Long> {
    Optional<Administrator> findByName(String username);
}
