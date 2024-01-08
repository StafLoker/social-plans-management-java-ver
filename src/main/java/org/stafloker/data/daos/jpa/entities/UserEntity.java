package org.stafloker.data.daos.jpa.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.stafloker.data.models.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    private String mobile;
    @Column(nullable = false)
    private String password;
    private Integer age;

    public UserEntity(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public User toUser() {
        User user = new User();
        BeanUtils.copyProperties(this, user);
        return user;
    }
}
