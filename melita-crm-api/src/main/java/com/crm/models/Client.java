package com.crm.models;

import com.melita_task.api.models.FullNameUpdate;
import com.melita_task.api.models.InstallationAddressUpdate;
import com.melita_task.api.models.requests.UpdateClientRequest;
import com.melita_task.contract.enums.ClientStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;


@Data
@Entity
@Table(name="clients")
@NoArgsConstructor(force = true)
@ToString(exclude = "orders")
public class Client{

    @Id
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @Valid
    @NotNull
    @Embedded
    private FullName fullName;

    @Valid
    @NotNull
    @Embedded
    private InstallationAddress installationAddress;

    @Enumerated(value = EnumType.STRING)
    private ClientStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders;

    public Client(final FullName fullName,
                  final InstallationAddress installationAddress){

        this.id = UUID.randomUUID();
        this.fullName = fullName;
        this.installationAddress = installationAddress;

        this.status = ClientStatus.ACTIVE;
        this.orders = new ArrayList<>();
    }

    public void updateClient(final FullNameUpdate fullName){
        if(nonNull(fullName.getFirstName()))  this.fullName.setFirstName(fullName.getFirstName());
        if(nonNull(fullName.getMiddleName())) this.fullName.setMiddleName(fullName.getMiddleName());
        if(nonNull(fullName.getLastName()))   this.fullName.setLastName(fullName.getLastName());
    }

    public void updateClient(final InstallationAddressUpdate installationAddress){
        this.installationAddress.setIsland(installationAddress.getIsland());
        this.installationAddress.setTown(installationAddress.getTown());
        this.installationAddress.setStreet(installationAddress.getStreet());
        this.installationAddress.setBuilding(installationAddress.getBuilding());
    }

    public void update(final UpdateClientRequest request){
        request.getFullName().ifPresent(this::updateClient);
        request.getInstallationAddress().ifPresent(this::updateClient);
    }


}