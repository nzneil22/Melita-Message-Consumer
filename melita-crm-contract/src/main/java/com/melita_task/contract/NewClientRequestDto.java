package com.melita_task.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewClientRequestDto {

    @Valid
    private FullNameDto fullName;

    @Valid
    private InstallationAddressDto installationAddress;

}
