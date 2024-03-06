package data.model.entity;

import data.model.entity.base.EntityBase;
import data.model.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status extends EntityBase {
    @Column(name = "status", unique = true, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
}