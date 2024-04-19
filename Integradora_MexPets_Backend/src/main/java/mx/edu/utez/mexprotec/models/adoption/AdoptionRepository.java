package mx.edu.utez.mexprotec.models.adoption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoption, UUID> {

    @Modifying
    @Query(
            value = "UPDATE adoption SET status = :status WHERE id = :id",
            nativeQuery = true
    )
    int updateStatusById(
            @Param("status") Boolean status,
            @Param("id") UUID id
    );

    List<Adoption> findByStatus(Boolean status);
    Optional<Adoption> findById(UUID id);
    Adoption getById(UUID id);
    @Query("SELECT COUNT(a) FROM Adoption a WHERE DATE(a.creationDate) = :date")
    long countByDate(@Param("date") LocalDate date);

    List<Adoption> findByAdopterId(UUID userId);

    void deleteById(UUID id);
}
