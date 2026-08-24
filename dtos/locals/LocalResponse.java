package project.project.dtos.locals;

public record LocalResponse( 

        String id,
        String name,
        String description,
        String address,
        String logoUrl,
        String openingHours,
        String services,

        // List categories using enums
        List<String> categories,

        // List contacts with object simple
        List<ContactRequest> contacts,

        // List methodPayment using enums
        List<String> methodPayment,

        // Optional field if "OTHER" payment method is selected
        String otherPaymentDetail,

        String status
) {}    

