package data.model.entity;

import data.model.entity.base.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Product extends EntityBase {
    @Column(unique = true, nullable = false)
    @NonNull
    private String name;
    @Lob
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    @NonNull
    private String description;
    @Column(name = "number_in_stock", nullable = false)
    @NonNull
    private int numberInStock;
    @Column(name = "min_price", nullable = false)
    @NonNull
    private BigDecimal minPrice;
    @Column(name = "current_price", nullable = false)
    @NonNull
    private BigDecimal currPrice;
    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "last_modified_by", referencedColumnName = "id")
    private User lastModifiedBy;
    @Column(name = "last_modifies_on", nullable = false)
    private LocalDateTime lastModifiedOn = LocalDateTime.now();
}