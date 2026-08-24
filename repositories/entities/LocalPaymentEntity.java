package project.project.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import project.project.model.enums.MethodPayment;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "local_payment_methods")
public class LocalPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "method", nullable = false, length = 30)
    private MethodPayment method;

    @Column(name = "details", length = 200)
    private String details; // Ej: "Solo Visa y Mastercard"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_id", nullable = false)
    private LocalEntity local;
}
