package mx.edu.utez.mexprotec.models.logs;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<Logs, String> {
    List<Logs> findAllByOrderByCreatedAt();
    Page<Logs> findAllByOrderByCreatedAt(Pageable pageable);
}
