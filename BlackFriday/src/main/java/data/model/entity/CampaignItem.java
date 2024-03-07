package data.model.entity;

import data.model.entity.base.EntityBase;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "campaign_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignItem extends EntityBase {
    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @Column(name = "discount_percentage", scale = 4, nullable = false)
    private double discountPercentage;
}