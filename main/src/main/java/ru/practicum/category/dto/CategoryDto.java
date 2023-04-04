package ru.practicum.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    @NotBlank
    private String name;
}
