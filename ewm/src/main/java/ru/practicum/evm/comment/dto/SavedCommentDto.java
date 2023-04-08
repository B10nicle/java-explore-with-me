package ru.practicum.evm.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavedCommentDto {

    @NotBlank
    @Size(min = 5, max = 500)
    private String text;
}
