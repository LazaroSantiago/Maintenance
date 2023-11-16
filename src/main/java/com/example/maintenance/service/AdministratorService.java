package com.example.maintenance.service;

import com.example.maintenance.repository.AdministratorRepository;
import com.example.maintenance.dto.ScooterDTO;
import com.example.maintenance.entity.Administrator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("AdministratorService")
public class AdministratorService implements BaseService<Administrator> {
    @Autowired
    private AdministratorRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<Administrator> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Administrator findById(Long id) throws Exception {
        try{
            Optional<Administrator> result = repository.findById(id);
            return result.get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            if (repository.existsById(id)) {
                repository.deleteById(id);
                return true;
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Administrator save(Administrator entity) throws Exception {
        try {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            return this.repository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<ScooterDTO> getReport(){
        return null;
    }

    @Transactional
    public List<ScooterDTO> getReportWithStops(){
        return null;
    }
}
