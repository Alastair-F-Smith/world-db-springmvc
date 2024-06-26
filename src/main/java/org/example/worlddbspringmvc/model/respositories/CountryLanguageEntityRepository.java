package org.example.worlddbspringmvc.model.respositories;

//import io.swagger.v3.oas.annotations.Hidden;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.model.entities.CountryLanguageEntity;
import org.example.worlddbspringmvc.model.entities.CountryLanguageEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Hidden
public interface CountryLanguageEntityRepository extends JpaRepository<CountryLanguageEntity, CountryLanguageEntityId> {

}