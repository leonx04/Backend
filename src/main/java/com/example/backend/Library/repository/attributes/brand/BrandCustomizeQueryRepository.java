package com.example.backend.Library.repository.attributes.brand;

import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.repository.attributes.AttributeCustomizeQueryRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public class BrandCustomizeQueryRepository extends AttributeCustomizeQueryRepository<Brand> {

    @Override
    protected String getEntityName() {
        return "Brand";
    }

    @Override
    protected Class<Brand> getEntityClass() {
        return Brand.class;
    }
}