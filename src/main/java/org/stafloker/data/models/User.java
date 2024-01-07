package org.stafloker.data.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.stafloker.data.models.validations.AgeConstraint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {
    public static final int MIN_AGE = 14;
    public static final int MAX_AGE = 100;
    public static final int MIN_LONG_PASSWORD = 3;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String mobile;
    @Size(min = MIN_LONG_PASSWORD, message = "The password must be at least 3 characters long")
    @Column(nullable = false)
    private String password;
    @AgeConstraint
    @Column(nullable = false)
    private Integer age;
}
