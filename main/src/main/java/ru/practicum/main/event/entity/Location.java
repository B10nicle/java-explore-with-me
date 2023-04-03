package ru.practicum.main.event.entity;

import org.hibernate.Hibernate;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

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
@Table(name = "locations", schema = "public")
public class Location {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "lat")
    private float lat;

    @Column(name = "lon")
    private float lon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        var location = (Location) o;
        return getId() != null && Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
