package org.stafloker.data.models.msp;

import lombok.*;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "activities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Activity {
    private static final int MIN_CAPACITY = 1;

    @Id
    @GeneratedValue
    @Column
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Integer duration;
    @Column(nullable = false, precision = 2)
    private Double price;
    @Column
    private Integer capacity;

    public Double getPrice(Integer age) {
        return this.price;
    }

    public void setCapacity(Integer capacity) {
        if (capacity < MIN_CAPACITY) {
            throw new InvalidAttributeException("Minimum capacity is " + MIN_CAPACITY + ": " + capacity);
        }
        this.capacity = capacity;

    }
}
