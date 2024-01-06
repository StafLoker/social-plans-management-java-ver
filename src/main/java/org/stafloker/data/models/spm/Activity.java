package org.stafloker.data.models.spm;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "activities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class Activity {
    private static final int MIN_CAPACITY = 1;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
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
