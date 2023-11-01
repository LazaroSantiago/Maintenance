package repository;

import entity.Administrator;
import org.springframework.stereotype.Repository;

@Repository("AdministratorRepository")
public interface AdministratorRepository extends BaseRepository<Administrator, Long> {
}
