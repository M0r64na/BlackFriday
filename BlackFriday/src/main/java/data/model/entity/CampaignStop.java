package data.model.entity;

import data.model.entity.base.EntityBase;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stopped_campaigns")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class CampaignStop extends EntityBase {
    @ManyToOne
    @JoinColumn(name = "campaign_id", referencedColumnName = "id", nullable = false)
    @NonNull
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "stopped_by", referencedColumnName = "id", nullable = false)
    @NonNull
    private User stoppedBy;
    @Column(name = "stopped_on", nullable = false)
    private LocalDateTime stoppedOn = LocalDateTime.now();
}