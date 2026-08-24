package project.project.services;

import project.project.dtos.locals.CreateLocalRequest;
import project.project.dtos.locals.LocalResponse;
import project.project.repositories.entities.LocalEntity;
import project.project.repositories.jpa.LocalJpaRepository;


@Service
public class LocalServive {
    

    private final LocalJpaRepository localJpaRepository;


    public LocalServive (LocalJpaRepository localJpaRepository) {
        this.localJpaRepository = localJpaRepository;
    }


    @Transactional
    public LocalResponse register (CreateLocalRequest request){

    if (localJpaRepository.findByLogoUrl(request.logoUrl()).isPresent()) {
        throw new ConflictException("El local ya está registrado.");
    }

        LocalEntity entity = new LocalEntity();
        entity.setName(request.name());
        entity.setCategory(request.categories());
        entity.setDescription(request.description());
        entity.setAddress(request.address());
        entity.setOpeningHours(request.openingHours());
        entity.setLogoUrl(request.logoUrl());
        entity.setContacts(request.contacts());
        entity.setPaymentMethods((request.methodPayment()));
        entity.setServices(request.services());
        entity.setOtherPaymentDetail(request.otherPaymentDetail());
        entity.setStatus("ACTIVE");
        
        entity = localJpaRepository.save(entity)

        return entity;
    }


}
