package org.stafloker.data.models.spm;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Activity {
    private static final int MIN_CAPACITY = 1;

    @EqualsAndHashCode.Include
    @NotNull
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Integer duration;
    @NotNull
    private Double price;
    @Min(value = MIN_CAPACITY, message = "Minimum capacity is " + MIN_CAPACITY)
    private Integer capacity;

    public Double getPrice(Integer age) {
        return this.price;
    }
}
