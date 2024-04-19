package mx.edu.utez.mexprotec.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.mexprotec.models.adoption.Adoption;
import mx.edu.utez.mexprotec.models.animals.ApprovalStatus;
import mx.edu.utez.mexprotec.models.processed.Processed;
import mx.edu.utez.mexprotec.models.users.Users;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProcessedDto {

    private UUID id;
    private Adoption adoption;
    private Users moderator;
    private ApprovalStatus approvalStatus;
    private String moderatorComment;

    public Processed getProcessed(){
        return new Processed(
                getId(),
                getAdoption(),
                getModerator(),
                getApprovalStatus(),
                getModeratorComment()
        );
    }
}
