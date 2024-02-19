package com.test.adidata.model.respository;

import com.test.adidata.model.entity.AppAccountTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppAccountTokenRepo extends JpaRepository<AppAccountTokenEntity, String> {

}
