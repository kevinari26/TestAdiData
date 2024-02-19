package com.test.adidata.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Setter
@Getter
@Entity
@Table(name = "APP_ACCOUNT_SUBSCRIPTION")
public class AppAccountSubscriptionEntity {
    @Id
    private String appAccountSubscriptionUuid;
    private String appAccountUuid;

    private String packageName;
    private Long price;
    private Long duration;
    private Long durationLeft;
    private String status;
}
