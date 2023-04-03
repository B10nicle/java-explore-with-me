package ru.practicum.server.hit.entity;

import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.Hibernate;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.util.Objects;

import static ru.practicum.server.hit.utils.Patterns.*;
import static javax.persistence.GenerationType.*;

/**
 * @author Oleg Khilko
 */

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hits", schema = "public")
public class Hit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String uri;
    private String app;
    private String ip;

    @DateTimeFormat(pattern = DATE_PATTERN)
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Hit that = (Hit) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
