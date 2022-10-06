package com.crm.models;

import com.melita_task.contract.enums.LobTypes;
import com.melita_task.contract.enums.OrderStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @NotNull
    private Integer serviceId;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private LobTypes lobType;

    @NotNull
    private Date installationDateAndTime;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

}
