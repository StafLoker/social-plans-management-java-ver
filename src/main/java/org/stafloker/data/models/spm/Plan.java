package org.stafloker.data.models.spm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.stafloker.data.models.User;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Plan {
    private static final int MIN_CAPACITY = 1;
    private static final int TIME_DISPLACEMENT = 20;

    @NotNull
    @EqualsAndHashCode.Include
    private Long id;
    @NotNull
    private User owner;
    @NotBlank
    private String name;
    @NotNull
    private LocalDateTime date;
    @NotBlank
    private String meetingPlace;
    @Min(value = MIN_CAPACITY, message = "Minimum capacity is " + MIN_CAPACITY)
    private Integer capacity;
    @Builder.Default
    private List<User> subscribersList = new LinkedList<>();
    @Builder.Default
    private List<Activity> activitiesList = new LinkedList<>();

    public void addSubscriber(User user) {
        if (!Objects.isNull(this.capacity)) {
            if (Objects.equals(this.subscribersList.size(), this.capacity)) {
                throw new InvalidAttributeException("Capacity full, cannot add user: " + user.getName());
            }
        }
        this.subscribersList.add(user);
    }

    public Integer availableSpots() {
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

    public Double price(User user) {
        return this.activitiesList.stream().mapToDouble(activity -> activity.getPrice(user.getAge())).sum();
    }

    public Integer duration() {
        return this.activitiesList.stream().mapToInt(activity -> activity.getDuration() + TIME_DISPLACEMENT).sum();
    }
}