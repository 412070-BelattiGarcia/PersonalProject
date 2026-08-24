package project.project.dtos.locals;

import java.util.List;



public record CreateLocalRequest (
    @NotBlank @Size(min = 3,max = 50) String name,
    @Size (max = 500) String description,
    @NotBlank @Size (min = 5, max = 200) String address,
    @Size (max = 200) String logoUrl,
    @Size (max = 500) String openingHours,
    @Size (max = 500) String services,

    // List categories using enums
    List<String> categories,

    // List contacts with object simple
    List<ContactRequest> contacts,

    // List methodPayment using enums
    List<String> methodPayment,

    // Optional field if "OTHER" payment method is selected
    String otherPaymentDetail

) {}
