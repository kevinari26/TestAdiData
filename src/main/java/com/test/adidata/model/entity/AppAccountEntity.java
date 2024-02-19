package com.test.adidata.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Setter
@Getter
@Entity
@Table(name = "APP_ACCOUNT")
public class AppAccountEntity {
    @Id
    private String appAccountUuid;

    private String name;
    private String email;
    private String password;
    private String tempPassword;
    private String phoneNo;

    private String ccName;
    private String ccNo;
    private String ccCvv;
    private String ccExpiredDate;

    private String otp;
    private Date otpCreatedDate;
    private String status;
}
