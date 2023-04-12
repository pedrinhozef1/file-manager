package br.com.project.image.manager.infrastructure.jpa;

import br.com.project.image.manager.domain.model.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRepository extends CrudRepository<File, Long> {
    File findByFileName(String fileName);
    void deleteByFileName(String fileName);
}
