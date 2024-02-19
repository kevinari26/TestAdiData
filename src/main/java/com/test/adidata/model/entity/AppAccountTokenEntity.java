package com.test.adidata.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Setter
@Getter
@Entity
@Table(name = "APP_ACCOUNT_TOKEN")
public class AppAccountTokenEntity {
    @Id
    private String appAccountTokenUuid;

    private String token;
}
