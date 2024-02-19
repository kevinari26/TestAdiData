package com.test.adidata.service.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.adidata.controller.JwtAuthenticationController;
import com.test.adidata.model.entity.AppAccountEntity;
import com.test.adidata.model.entity.AppAccountTokenEntity;
import com.test.adidata.model.respository.AppAccountRepo;
import com.test.adidata.model.respository.AppAccountTokenRepo;
import com.test.adidata.util.CommonUtil;
import com.test.adidata.util.Constant;
import com.test.adidata.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Service
public class AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;
    // service
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    ResponseHelper responseHelper;
    @Autowired
    JwtUserDetailsService jwtUserDetailsService;
    // repo
    @Autowired
    AppAccountRepo appAccountRepo;
    @Autowired
    AppAccountTokenRepo appAccountTokenRepo;

    private final Logger log = LoggerFactory.getLogger(this.getClass());


	public Object login(JsonNode body) {
	    try {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsernameAndPassword(body.get("username").asText(), body.get("password").asText());
            String token = jwtTokenUtil.generateToken(userDetails);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.get("username").asText(), body.get("password").asText()));

            // save
            AppAccountTokenEntity appAccountTokenEntity = new AppAccountTokenEntity();
            appAccountTokenEntity.setAppAccountTokenUuid(CommonUtil.generateUuid());
            appAccountTokenEntity.setToken(token);
//            appAccountTokenRepo.save(appAccountTokenEntity);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);

            return responseHelper.setRestResponseMessage(HttpStatus.OK, "S001", null, response);
        }
//        catch (DisabledException e) {
//            return responseHelper.setRestResponseMessage(HttpStatus.OK, "E001", "USER_DISABLED", null);
//        }
	    catch (BadCredentialsException e) {
            return responseHelper.setRestResponseMessage(HttpStatus.OK, "E001", "INVALID_CREDENTIALS", null);
        }
        catch (UsernameNotFoundException e) {
            return responseHelper.setRestResponseMessage(HttpStatus.OK, "E003", "USERNAME_NOT_FOUND", null);
        }
	    catch (Exception e) {
            return responseHelper.setRestResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, "S001", null, null);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println("e1");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("e2");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}