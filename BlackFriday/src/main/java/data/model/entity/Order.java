package data.model.entity;

import data.model.entity.base.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Order extends EntityBase {
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @NonNull
    private Status status;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @NonNull
    private User createdBy;
    @OneToMany(mappedBy = "order", targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    private Set<OrderItem> items = new HashSet<>();
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn = LocalDateTime.now();
}