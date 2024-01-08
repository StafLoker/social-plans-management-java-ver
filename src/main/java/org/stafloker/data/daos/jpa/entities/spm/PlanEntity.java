package org.stafloker.data.daos.jpa.entities.spm;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.stafloker.data.daos.jpa.entities.UserEntity;
import org.stafloker.data.models.spm.Plan;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "plans")
public class PlanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;
    @Column(nullable = false)
    private String name;
    private LocalDateTime date;
    private String meetingPlace;
    @Column
    private Integer capacity;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "plan_subscribers",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Builder.Default
    private List<UserEntity> subscribersList = new LinkedList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "plan_activities",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    @Builder.Default
    private List<ActivityEntity> activitiesList = new LinkedList<>();

    public PlanEntity(Plan plan) {
        BeanUtils.copyProperties(plan, this);
    }

    public Plan toPlan() {
        Plan plan = new Plan();
        BeanUtils.copyProperties(this, plan);
        return plan;
    }
}
