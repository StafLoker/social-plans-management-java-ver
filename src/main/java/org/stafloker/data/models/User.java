package org.stafloker.data.models;

import lombok.*;
import org.stafloker.data.models.exceptions.InvalidAttributeException;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {
    private static final int MIN_AGE = 14;
    private static final int MAX_AGE = 100;
    private static final int MIN_LONG_PASSWORD = 3;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue
    @Column
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String mobile;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Integer age;


    public void setPassword(String password) {
        if (password.length() < MIN_LONG_PASSWORD) {
            throw new InvalidAttributeException("Password has less than three characters: " + password);
        }
        this.password = password;
    }

    public void setAge(Integer age) {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new InvalidAttributeException("Age is not in your range [" + MIN_AGE + "," + MAX_AGE + "]: " + age);
        }
        this.age = age;
    }
}
