package com.test.adidata.service.authentication;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.adidata.model.entity.AppAccountEntity;
import com.test.adidata.model.respository.AppAccountRepo;
import com.test.adidata.util.Constant;
import com.test.adidata.util.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    // service
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    ResponseHelper responseHelper;
    // repo
    @Autowired
    AppAccountRepo appAccountRepo;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppAccountEntity appAccountEntity = appAccountRepo.findByEmailAndStatus(username, Constant.ACTIVE);

        if (appAccountEntity != null) {
            return new User(username, passwordEncoder.encode(appAccountEntity.getPassword()),
                    new ArrayList<>());
        }
		else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

    public UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {
        AppAccountEntity appAccountEntity = appAccountRepo.findByEmailAndStatus(username, Constant.ACTIVE);

        if (appAccountEntity != null) {
            if (!appAccountEntity.getPassword().equals(password)) {
                throw new BadCredentialsException("bad credentials");
            }
            return new User(username, passwordEncoder.encode(appAccountEntity.getPassword()),
                    new ArrayList<>());
        }
        else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}