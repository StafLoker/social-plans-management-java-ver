package org.stafloker.data.daos.jpa.entities.spm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;
import org.stafloker.data.models.spm.Activity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "activities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private Integer duration;
    @Column(nullable = false, precision = 2)
    private Double price;
    @Column
    private Integer capacity;

    public ActivityEntity(Activity activity) {
        BeanUtils.copyProperties(activity, this);
    }

    public Activity toActivity() {
        Activity activity = new Activity();
        BeanUtils.copyProperties(this, activity);
        return activity;
    }
}
