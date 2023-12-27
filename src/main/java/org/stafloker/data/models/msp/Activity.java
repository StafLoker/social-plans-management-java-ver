package org.stafloker.data.models.msp;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "activities")
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
}
