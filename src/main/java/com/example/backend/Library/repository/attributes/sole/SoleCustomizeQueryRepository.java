package com.example.backend.Library.repository.attributes.sole;

import com.example.backend.Library.model.entity.attributes.Sole;
import com.example.backend.Library.repository.attributes.AttributeCustomizeQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SoleCustomizeQueryRepository extends AttributeCustomizeQueryRepository<Sole> {

    @Override
    protected String getEntityName() {
        return "Sole";
    }

    @Override
    protected Class<Sole> getEntityClass() {
        return Sole.class;
    }

}
