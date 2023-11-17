package com.example.maintenance.service;

import com.example.maintenance.dto.ScooterDTO;
import com.example.maintenance.repository.AdministratorRepository;
import com.example.maintenance.entity.Administrator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("AdministratorService")
public class AdministratorService implements BaseService<Administrator> {
    @Autowired
    private RestTemplate restTemplate;

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
        try {
            Optional<Administrator> result = repository.findById(id);
            return result.get();
        } catch (Exception e) {
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
    public List<ScooterDTO> getReportWithoutStops() throws Exception {
        String url = "http://localhost:8082/scooter/report/stopYes";
        return getScooterDTOs(url);
    }

    @Transactional
    public List<ScooterDTO> getReportWithStops() throws Exception {
        String url = "http://localhost:8082/scooter/report/stopNo";
        return getScooterDTOs(url);
    }

    private List<ScooterDTO> getScooterDTOs(String url) throws Exception {
        ResponseEntity<ScooterDTO[]> responseEntity = restTemplate.getForEntity(url, ScooterDTO[].class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            ScooterDTO[] scooterArray = responseEntity.getBody();
            assert scooterArray != null;
            return Arrays.asList(scooterArray);
        } else {
            throw new Exception("Error al obtener datos del microservicio.");
        }
    }

}
