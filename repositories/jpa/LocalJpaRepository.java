package project.project.repositories.jpa;

import java.util.Optional;

import project.project.repositories.entities.LocalEntity;

@Repository
public interface LocalJpaRepository extends JpaRepository<LocalEntity, UUID>{
    Optional<LocalEntity> findByName (String name);
    //Categoy
    //contacto
    Optional<LocalEntity> findByLogoUrl (String logoUrl);
}