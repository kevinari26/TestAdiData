package com.test.adidata.service.fitness;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.adidata.model.entity.AppAccountEntity;
import com.test.adidata.model.respository.AppAccountRepo;
import com.test.adidata.model.rest.fitness.RestAccountOtp;
import com.test.adidata.util.CommonUtil;
import com.test.adidata.util.Constant;
import com.test.adidata.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {
    // prop
    @Value("${otpExpired}")
    Long otpExpired;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ResponseHelper responseHelper;

    // repo
    @Autowired
    AppAccountRepo appAccountRepo;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    public Object test(JsonNode body) {
        return null;
    }

    @Transactional
    public Object addAccount(JsonNode body) {
        try {
            // validasi email
            AppAccountEntity existAppAccountEntity = appAccountRepo.findByEmailAndStatus(body.get("email").asText(), Constant.ACTIVE);
            if (existAppAccountEntity != null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E004", null, null);
            }

            // save
            AppAccountEntity appAccountEntity = new AppAccountEntity();
            objectMapper.updateValue(appAccountEntity, body);
            appAccountEntity.setAppAccountUuid(CommonUtil.generateUuid());
            appAccountEntity.setOtp(CommonUtil.generateOtp());
            appAccountEntity.setStatus(Constant.INACTIVE);
            appAccountEntity.setOtpCreatedDate(CommonUtil.currentTime());
            appAccountRepo.save(appAccountEntity);

            // simulasi send email berisi uuid account dan otp
            RestAccountOtp restAccountOtp = new RestAccountOtp();
            restAccountOtp.setAccountUuid(appAccountEntity.getAppAccountUuid());
            restAccountOtp.setAccountOtp(appAccountEntity.getOtp());

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, restAccountOtp);
        }
        catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "E002", null, null);
        }
    }

    @Transactional
    public Object confirmOtp(RestAccountOtp body) {
        try {
            // search
            AppAccountEntity existAppAccountEntity = appAccountRepo.findByAppAccountUuidAndStatus(body.getAccountUuid(), Constant.INACTIVE);
            if (existAppAccountEntity == null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }
            // validasi otp expired
            if (CommonUtil.currentTime().getTime() - existAppAccountEntity.getOtpCreatedDate().getTime() > otpExpired) {
                appAccountRepo.delete(existAppAccountEntity);
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }
            // validasi otp
            if (!existAppAccountEntity.getOtp().equals(body.getAccountOtp())) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E001", null, null);
            }

            // update
            existAppAccountEntity.setOtp(null);
            existAppAccountEntity.setStatus(Constant.ACTIVE);
            appAccountRepo.save(existAppAccountEntity);

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, null);
        }
        catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "E002", null, null);
        }
    }

    @Transactional
    public Object inquiryAccount(JsonNode body) {
        try {
            Map<String, Object> response = new HashMap<>();

            // search
            AppAccountEntity existAppAccountEntity = appAccountRepo.findByEmail(body.get("email").asText());
            if (existAppAccountEntity == null) {
                response.put("status", Constant.NOT_REGISTERED);
            }
            else {
                response.put("accountUuid", existAppAccountEntity.getAppAccountUuid());
                response.put("status", existAppAccountEntity.getStatus());
            }

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, response);
        }
        catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "E002", null, null);
        }
    }



    @Transactional
    public Object resetPassword(JsonNode body) {
        try {
            // search
            AppAccountEntity existAppAccountEntity = appAccountRepo.findByEmailAndStatus(body.get("email").asText(), Constant.ACTIVE);
            if (existAppAccountEntity == null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }

            existAppAccountEntity.setTempPassword(body.get("newPassword").asText());
            existAppAccountEntity.setOtp(CommonUtil.generateOtp());
            existAppAccountEntity.setOtpCreatedDate(CommonUtil.currentTime());

            // simulasi send email berisi uuid account dan otp
            RestAccountOtp restAccountOtp = new RestAccountOtp();
            restAccountOtp.setAccountUuid(existAppAccountEntity.getAppAccountUuid());
            restAccountOtp.setAccountOtp(existAppAccountEntity.getOtp());

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, restAccountOtp);
        }
        catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "E002", null, null);
        }
    }

    @Transactional
    public Object confirmOtpReset(RestAccountOtp body) {
        try {
            // search
            AppAccountEntity existAppAccountEntity = appAccountRepo.findByAppAccountUuidAndStatus(body.getAccountUuid(), Constant.ACTIVE);
            if (existAppAccountEntity == null) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }
            // validasi otp expired
            if (CommonUtil.currentTime().getTime() - existAppAccountEntity.getOtpCreatedDate().getTime() > otpExpired) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", null, null);
            }
            // validasi otp
            if (!existAppAccountEntity.getOtp().equals(body.getAccountOtp())) {
                return responseHelper.setRestResponseMessage(HttpStatus.OK, "E001", null, null);
            }

            // update
            existAppAccountEntity.setOtp(null);
            existAppAccountEntity.setPassword(existAppAccountEntity.getTempPassword());
            existAppAccountEntity.setTempPassword(null);
            appAccountRepo.save(existAppAccountEntity);

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, null);
        }
        catch (Exception e) {
            CommonUtil.errorLog(log, e);
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "E002", null, null);
        }
    }
}
