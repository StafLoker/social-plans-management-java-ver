package org.stafloker.data.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.stafloker.data.models.validations.AgeConstraint;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    public static final int MIN_AGE = 14;
    public static final int MAX_AGE = 100;
    public static final int MIN_LONG_PASSWORD = 3;

    @NotNull
    @EqualsAndHashCode.Include
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String mobile;
    @NotNull
    @Size(min = MIN_LONG_PASSWORD, message = "The password must be at least 3 characters long")
    private String password;
    @NotNull
    @AgeConstraint
    private Integer age;
}
