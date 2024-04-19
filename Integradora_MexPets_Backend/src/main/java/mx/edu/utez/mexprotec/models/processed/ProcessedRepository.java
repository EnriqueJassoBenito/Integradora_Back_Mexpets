package mx.edu.utez.mexprotec.models.processed;

import mx.edu.utez.mexprotec.models.animals.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessedRepository extends JpaRepository<Processed, UUID>{

    Optional<Processed> findById(UUID id);
    Processed getById(UUID id);

    List<Processed> findByApprovalStatus(ApprovalStatus status);


}
