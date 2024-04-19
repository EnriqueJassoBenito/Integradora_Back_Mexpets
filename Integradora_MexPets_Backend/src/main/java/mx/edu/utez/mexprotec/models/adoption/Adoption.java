package mx.edu.utez.mexprotec.models.adoption;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.animals.Animals;
import mx.edu.utez.mexprotec.models.image.adoption.AdoptionImage;
import mx.edu.utez.mexprotec.models.users.Users;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="adoption")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Adoption {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_animals", nullable = false)
    private Animals animal;

    @ManyToOne
    @JoinColumn(name = "id_users", nullable = false )
    private Users adopter;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "adoption", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<AdoptionImage> images;

    @Column(name = "status", columnDefinition = "boolean default false")
    private Boolean status;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
