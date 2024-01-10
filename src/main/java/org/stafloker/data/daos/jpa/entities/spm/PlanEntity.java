package org.stafloker.data.daos.jpa.entities.spm;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.stafloker.data.daos.jpa.entities.UserEntity;
import org.stafloker.data.models.spm.Plan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    @NotNull
    private String name;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private String meetingPlace;
    @Column
    private Integer capacity;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "plan_subscribers",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> subscribersList;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "plan_activities",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<ActivityEntity> activitiesList;

    public PlanEntity(Plan plan) {
        BeanUtils.copyProperties(plan, this, "owner", "subscribersList", "activitiesList");
        this.setOwner(new UserEntity(plan.getOwner()));
        this.setSubscribersList(plan.getSubscribers().stream().map(UserEntity::new).collect(Collectors.toList()));
        this.setActivitiesList(plan.getActivities().stream().map(ActivityEntity::new).collect(Collectors.toList()));
    }

    public Plan toPlan() {
        Plan plan = new Plan();
        BeanUtils.copyProperties(this, plan, "owner", "subscribersList", "activitiesList");
        plan.setOwner(this.owner.toUser());
        plan.setSubscribers(this.subscribersList.stream().map(UserEntity::toUser).collect(Collectors.toList()));
        plan.setActivities(this.activitiesList.stream().map(ActivityEntity::toActivity).collect(Collectors.toList()));
        return plan;
    }
}
