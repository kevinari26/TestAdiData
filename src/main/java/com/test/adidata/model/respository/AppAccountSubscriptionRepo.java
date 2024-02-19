package com.test.adidata.model.respository;

import com.test.adidata.model.entity.AppAccountSubscriptionEntity;
import com.test.adidata.model.entity.AppAccountTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AppAccountSubscriptionRepo extends JpaRepository<AppAccountSubscriptionEntity, String> {
    AppAccountSubscriptionEntity findByAppAccountUuidAndPackageName(String appAccountUuid, String packageName);
}
