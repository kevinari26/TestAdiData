package com.test.adidata.service.fitness;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.adidata.model.entity.AppAccountEntity;
import com.test.adidata.model.entity.AppAccountSubscriptionEntity;
import com.test.adidata.model.respository.AppAccountRepo;
import com.test.adidata.model.respository.AppAccountSubscriptionRepo;
import com.test.adidata.model.rest.fitness.RestAccountOtp;
import com.test.adidata.service.authentication.JwtTokenUtil;
import com.test.adidata.util.CommonUtil;
import com.test.adidata.util.Constant;
import com.test.adidata.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class SubscriptionService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ResponseHelper responseHelper;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    // repo
    @Autowired
    AppAccountRepo appAccountRepo;
    @Autowired
    AppAccountSubscriptionRepo appAccountSubscriptionRepo;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public Object test(JsonNode body) {
        return null;
    }


    public Object addSubscription(Map<String, String> headers, JsonNode body) {
        try {
            // search
            AppAccountEntity appAccountEntity = appAccountRepo.findByEmailAndStatus(jwtTokenUtil.getUsernameFromToken(headers.get("authorization").substring(7)), Constant.ACTIVE);
            if (appAccountEntity == null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }

            // search
            AppAccountSubscriptionEntity appAccountSubscriptionEntity = appAccountSubscriptionRepo.findByAppAccountUuidAndPackageName(appAccountEntity.getAppAccountUuid(), body.get("package").asText());
            if (appAccountSubscriptionEntity != null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E004", null, null);
            }

            appAccountSubscriptionEntity = new AppAccountSubscriptionEntity();
            appAccountSubscriptionEntity.setAppAccountSubscriptionUuid(CommonUtil.generateUuid());
            appAccountSubscriptionEntity.setAppAccountUuid(appAccountEntity.getAppAccountUuid());
            if (Constant.PACKAGE_1.equals(body.get("package").asText())) {
                appAccountSubscriptionEntity.setPackageName(Constant.PACKAGE_1);
                appAccountSubscriptionEntity.setPrice(100000L);
                appAccountSubscriptionEntity.setDuration(10L);
                appAccountSubscriptionEntity.setDurationLeft(10L);
                appAccountSubscriptionEntity.setStatus(Constant.ACTIVE);
            }
            else {
                appAccountSubscriptionEntity.setPackageName(Constant.PACKAGE_2);
                appAccountSubscriptionEntity.setPrice(200000L);
                appAccountSubscriptionEntity.setDuration(15L);
                appAccountSubscriptionEntity.setDurationLeft(15L);
                appAccountSubscriptionEntity.setStatus(Constant.ACTIVE);
            }
            appAccountSubscriptionRepo.save(appAccountSubscriptionEntity);

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, null);
        }
        catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "E002", null, null);
        }
    }

    @Transactional(readOnly = true)
    public Object updateSubscription(Map<String, String> headers, JsonNode body) {
        try {
            // search
            AppAccountEntity appAccountEntity = appAccountRepo.findByEmailAndStatus(jwtTokenUtil.getUsernameFromToken(headers.get("authorization").substring(7)), Constant.ACTIVE);
            if (appAccountEntity == null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }

            // search
            AppAccountSubscriptionEntity appAccountSubscriptionEntity = appAccountSubscriptionRepo.findByAppAccountUuidAndPackageName(appAccountEntity.getAppAccountUuid(), body.get("package").asText());
            if (appAccountSubscriptionEntity == null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }

            appAccountSubscriptionEntity.setDurationLeft(appAccountSubscriptionEntity.getDurationLeft() + body.get("addDuration").asLong());
            appAccountSubscriptionEntity.setStatus(body.get("status").asText());
            appAccountSubscriptionRepo.save(appAccountSubscriptionEntity);

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, null);
        }
        catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "E002", null, null);
        }
    }
}
