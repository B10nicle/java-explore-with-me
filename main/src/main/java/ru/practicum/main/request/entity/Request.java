package ru.practicum.main.request.entity;

import ru.practicum.main.request.enums.RequestStatus;
import org.hibernate.Hibernate;
import lombok.*;

import java.time.LocalDateTime;
import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.*;
import static javax.persistence.EnumType.*;

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
@Table(name = "requests", schema = "public")
public class Request {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(STRING)
    @Column(name = "status", length = 140)
    private RequestStatus status;

    @Column(name = "event")
    private Long event;

    @Column(name = "requester")
    private Long requester;

    @Column(name = "created")
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var request = (Request) o;
        return getId() != null && Objects.equals(getId(), request.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
