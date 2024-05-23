package org.example.worlddbspringmvc.model.respositories;

//import io.swagger.v3.oas.annotations.Hidden;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

//@Hidden
public interface CountryEntityRepository extends JpaRepository<CountryEntity, String> {

    public CountryEntity findByName(String name);

}