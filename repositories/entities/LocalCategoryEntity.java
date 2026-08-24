package project.project.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import project.project.modals.CategoryType;
import project.project.model.enums.MethodPayment;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "local_category")
public class LocalContactEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 30)
    private CategoryType type;

    @Column(name = "details", length = 200)
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id", nullable = false)
    private LocalEntity local;
}
