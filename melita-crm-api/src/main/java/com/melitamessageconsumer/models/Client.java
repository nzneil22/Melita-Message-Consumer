package com.melitamessageconsumer.models;

import com.melita_task.contract.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@Builder
@Table(name="clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private final UUID id = UUID.randomUUID();

    @Valid
    @NotNull
    @Embedded
    private FullName fullName;

    @Valid
    @NotNull
    @Embedded
    private InstallationAddress installationAddress;

    private ClientStatus status = ClientStatus.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders;

}
