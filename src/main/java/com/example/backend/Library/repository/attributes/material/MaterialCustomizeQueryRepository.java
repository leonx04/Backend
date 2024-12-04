package com.example.backend.Library.repository.attributes.material;

import com.example.backend.Library.model.entity.attributes.Material;
import com.example.backend.Library.repository.attributes.AttributeCustomizeQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MaterialCustomizeQueryRepository extends AttributeCustomizeQueryRepository<Material> {

    @Override
    protected String getEntityName() {
        return "Material";
    }

    @Override
    protected Class<Material> getEntityClass() {
        return Material.class;
    }

}
