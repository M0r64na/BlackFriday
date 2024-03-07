package data.model.entity;

import data.model.entity.base.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campaigns")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Campaign extends EntityBase {
    @ManyToOne
    @JoinColumn(name = "started_by", referencedColumnName = "id", nullable = false)
    @NonNull
    private User startedBy;
    @Column(name = "started_on", nullable = false)
    private LocalDateTime startedOn = LocalDateTime.now();
    @OneToMany(mappedBy = "campaign", targetEntity = CampaignItem.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CampaignItem> items = new HashSet<>();
}