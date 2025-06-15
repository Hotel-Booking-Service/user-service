package com.hbs.userservice.model;

import com.hbs.userservice.validation.annotation.ValidTimezone;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language = Language.ZH;

    @Column(nullable = false)
    @ValidTimezone
    private String timezone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.ZAR;

    @Column
    @Enumerated(EnumType.STRING)
    private Notification notification = Notification.EMAIL;

}
