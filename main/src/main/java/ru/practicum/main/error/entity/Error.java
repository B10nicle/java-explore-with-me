package ru.practicum.main.error.entity;

import lombok.*;

import java.util.Objects;

/**
 * @author Oleg Khilko
 */

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String status;
    private String reason;
    private String message;
    private String timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        var error = (Error) o;

        if (!Objects.equals(status, error.status)) return false;
        if (!Objects.equals(reason, error.reason)) return false;
        if (!Objects.equals(message, error.message)) return false;
        return Objects.equals(timestamp, error.timestamp);
    }

    @Override
    public int hashCode() {
        int result = status != null ? status.hashCode() : 0;
        result = 31 * result + (reason != null ? reason.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
