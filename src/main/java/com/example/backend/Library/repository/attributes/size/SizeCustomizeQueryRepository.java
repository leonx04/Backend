package com.example.backend.Library.repository.attributes.size;

import com.example.backend.Library.model.entity.attributes.Size;
import com.example.backend.Library.repository.attributes.AttributeCustomizeQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SizeCustomizeQueryRepository extends AttributeCustomizeQueryRepository<Size> {

    @Override
    protected String getEntityName() {
        return "Size";
    }

    @Override
    protected Class<Size> getEntityClass() {
        return Size.class;
    }
}