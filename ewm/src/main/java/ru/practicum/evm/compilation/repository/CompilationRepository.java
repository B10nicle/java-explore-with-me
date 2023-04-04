package ru.practicum.evm.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.evm.compilation.entity.Compilation;

/**
 * @author Oleg Khilko
 */

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

}
