package project.project.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import project.project.model.enums.ContactType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "local_contacts")
public class LocalContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 30)
    private ContactType type;

    @Column(name = "value", nullable = false, length = 200)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id", nullable = false)
    private LocalEntity local;
}
