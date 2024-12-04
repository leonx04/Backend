package com.example.backend.Library.repository.attributes.color;

import com.example.backend.Library.model.entity.attributes.Color;
import com.example.backend.Library.repository.attributes.AttributeCustomizeQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ColorCustomizeQueryRepository extends AttributeCustomizeQueryRepository<Color> {

    @Override
    protected String getEntityName() {
        return "Color";
    }

    @Override
    protected Class<Color> getEntityClass() {
        return Color.class;
    }
}