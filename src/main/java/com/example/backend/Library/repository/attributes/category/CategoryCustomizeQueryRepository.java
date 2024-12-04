package com.example.backend.Library.repository.attributes.category;

import com.example.backend.Library.model.entity.attributes.Brand;
import com.example.backend.Library.model.entity.attributes.Category;
import com.example.backend.Library.repository.attributes.AttributeCustomizeQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryCustomizeQueryRepository extends AttributeCustomizeQueryRepository<Category> {

    @Override
    protected String getEntityName() {
        return "Category";
    }

    @Override
    protected Class<Category> getEntityClass() {
        return Category.class;
    }

}
