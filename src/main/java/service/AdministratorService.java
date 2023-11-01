package service;

import entity.Administrator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.AdministratorRepository;

import java.util.List;
import java.util.Optional;

@Service("AdministratorService")
public class AdministratorService implements BaseService<Administrator> {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    @Transactional
    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    @Override
    @Transactional
    public Administrator findById(Long id) throws Exception {
        try{
            Optional<Administrator> result = administratorRepository.findById(id);
            return result.get();
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(Long id) throws Exception {
        try {
            if (administratorRepository.existsById(id)) {
                administratorRepository.deleteById(id);
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
            return this.administratorRepository.save(entity);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
