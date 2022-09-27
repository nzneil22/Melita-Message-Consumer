package com.melitamessageconsumer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class FullName {

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;
}