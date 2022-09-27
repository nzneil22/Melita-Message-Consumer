package com.melitamessageconsumer.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Builder
@Table(name="orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@AllArgsConstructor
public class Order {

    @Id
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @NotNull
    private Integer serviceId;

    @NotNull
    private LobTypes lobType;

    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private Date installationDateAndTime;

    private OrderStatus status = OrderStatus.CREATED;

}
