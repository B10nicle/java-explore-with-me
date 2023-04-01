package ru.practicum.main.category.dto;

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
public class SavedCategoryDto {
    @NotBlank
    private String name;
}
