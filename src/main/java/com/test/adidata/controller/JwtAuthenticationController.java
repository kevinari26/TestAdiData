package com.test.adidata.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.test.adidata.model.entity.AppAccountTokenEntity;
import com.test.adidata.model.respository.AppAccountRepo;
import com.test.adidata.model.respository.AppAccountTokenRepo;
import com.test.adidata.service.authentication.AuthenticationService;
import com.test.adidata.service.authentication.JwtTokenUtil;
import com.test.adidata.service.authentication.JwtUserDetailsService;
import com.test.adidata.util.CommonUtil;
import com.test.adidata.util.ResponseHelper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@RestController
@CrossOrigin
@RequestMapping("/fitness/authenticate")
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;


	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object createAuthenticationToken(@RequestBody JsonNode body) throws Exception {
		return authenticationService.login(body);
	}



}
