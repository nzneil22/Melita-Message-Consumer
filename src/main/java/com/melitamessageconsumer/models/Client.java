package com.melitamessageconsumer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {

    @Id
    @NotNull
    private String id;

    @Valid
    @NotNull
    @Embedded
    private FullName fullName;

    @Valid
    @NotNull
    @Embedded
    private InstallationAddress installationAddress;

    @NotNull
    private Boolean active = true;

}
