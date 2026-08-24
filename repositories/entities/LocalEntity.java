package project.project.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import project.project.modals.MethodPayment;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locals")
public class LocalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany (mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocalCategoryEntity> category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "opening_hours", columnDefinition = "TEXT")
    private String openingHours;

    @Column(name = "logo_url", length = 200)
    private String logoUrl;

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocalContactEntity> contacts;

    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LocalPaymentEntity> paymentMethods;

    @Column(name = "other_payment_detail", length = 200)
    private String otherPaymentDetail;

    @Column(name = "services", columnDefinition = "TEXT")
    private String services;

    @Column(name = "status", nullable = false, length = 30)
    private String status = "ACTIVE";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity owner;
}
