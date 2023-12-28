package org.stafloker.data.models.spm;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.stafloker.data.models.User;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "plans")
public class Plan {
    private static final int MIN_CAPACITY = 1;
    private static final int TIME_DISPLACEMENT = 20;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    @Column
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn
    private User owner;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column(nullable = false)
    private String meetingPlace;
    @Column
    private Integer capacity;
    @ManyToMany
    @JoinTable(
            name = "plan_subscribers",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> subscribersList;
    @ManyToMany
    @JoinTable(
            name = "plan_activities",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activitiesList;

    public void addSubscriber(User user) {
        if (!Objects.isNull(this.capacity)) {
            if (Objects.equals(this.subscribersList.size(), this.capacity)) {
                throw new InvalidAttributeException("Capacity full, cannot add user: " + user.getName());
            }
        }
        this.subscribersList.add(user);
    }

    public Integer getAvailableSpots() {
        if (Objects.isNull(this.capacity)) {
            return null;
        }
        return this.capacity - this.subscribersList.size();
    }

    public void addActivity(Activity activity) {
        if (this.activitiesList.contains(activity)) {
            throw new InvalidAttributeException("Activity with ID: " + activity.getId() + " already exists in the activities list.");
        }
        if (!Objects.isNull(activity.getCapacity()) && !Objects.isNull(this.capacity)) {
            if (activity.getCapacity() < this.capacity) {
                this.capacity = activity.getCapacity();
            }
        }
        this.activitiesList.add(activity);
    }

    public Double getPrice(User user) {
        return this.activitiesList.stream().mapToDouble(activity -> activity.getPrice(user.getAge())).sum();
    }

    public Integer getDuration() {
        return this.activitiesList.stream().mapToInt(activity -> activity.getDuration() + TIME_DISPLACEMENT).sum();
    }

    public void setCapacity(Integer capacity) {
        if (capacity < MIN_CAPACITY) {
            throw new InvalidAttributeException("Minimum capacity is " + MIN_CAPACITY + ": " + capacity);
        }
        this.capacity = capacity;
    }
}