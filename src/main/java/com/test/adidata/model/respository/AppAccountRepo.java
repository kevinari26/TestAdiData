package com.test.adidata.model.respository;

import com.test.adidata.model.entity.AppAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppAccountRepo extends JpaRepository<AppAccountEntity, String> {
    AppAccountEntity findByAppAccountUuidAndStatus (String uuid, String status);
    AppAccountEntity findByEmailAndStatus (String email, String status);
    AppAccountEntity findByEmailAndPasswordAndStatus (String email, String passwor, String status);
    AppAccountEntity findByEmail (String email);
}
