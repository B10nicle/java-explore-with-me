package ru.practicum.compilation.entity;

import ru.practicum.event.entity.Event;
import org.hibernate.Hibernate;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

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
@Table(name = "compilations", schema = "public")
public class Compilation {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "title", length = 140)
    private String title;

    @Column(name = "pinned")
    private Boolean pinned;

    @ManyToMany
    @JoinTable(name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    @OrderBy("eventDate")
    @ToString.Exclude
    private Set<Event> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var compilation = (Compilation) o;
        return getId() != null && Objects.equals(getId(), compilation.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
