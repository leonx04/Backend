package com.example.backend.Library.repository.products.productVariant;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.request.products.ProductVariantParamRequest;
import com.example.backend.Library.model.entity.products.ProductVariant;
import com.example.backend.Library.repository.QueryBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public  class ProductVariantCustomizeQueryRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    private static final String WILDCARD_LIKE_FORMAT = "%%%s%%"; // Cho tìm kiếm có ký tự đại diện ở cả đầu và cuối
    private static final String STARTS_WITH_LIKE_FORMAT = "%s%%"; // Cho tìm kiếm bắt đầu bằng chuỗi
    private static final String ENDS_WITH_LIKE_FORMAT = "%%%s"; // Cho tìm kiếm kết thúc bằng chuỗi
    private   String getEntityName(){
        return "ProductVariant";
    };

    private  Class<ProductVariant> getEntityClass(){
        return ProductVariant.class;
    };


    public List<ProductVariant> getWithPagination(ProductVariantParamRequest param) {
        String sql = buildSelectQuery("SELECT entity FROM " + getEntityName() + " entity",param);
        TypedQuery<ProductVariant> query = createQueryWithPagination(param, sql);
        return query.getResultList();
    }
    public PageableResponse getPageData(ProductVariantParamRequest param) {
        long totalElements = getTotalElements(param);
        List<ProductVariant> content = getWithPagination(param);
        return createPageableResponse(param, totalElements, content);
    }

    private String buildSelectQuery(String sqlQuery ,ProductVariantParamRequest param) {
        QueryBuilder queryBuilder = new QueryBuilder(sqlQuery);
        addConditionsToQueryBuilder(queryBuilder, param);
        queryBuilder.addOrder(param.getSortBy(), param.getSortDir());
        return queryBuilder.build();
    }

    // Tạo câu truy vấn COUNT
    private String buildCountQuery(ProductVariantParamRequest param) {
        QueryBuilder queryBuilder = new QueryBuilder("SELECT COUNT(entity) FROM " + getEntityName() + " entity");
        addConditionsToQueryBuilder(queryBuilder, param);
        return queryBuilder.build();
    }

    // Tạo TypedQuery và  thiết lập tham số và phân trang
    private TypedQuery<ProductVariant> createQueryWithPagination(ProductVariantParamRequest param, String sql) {
        TypedQuery<ProductVariant> query = entityManager.createQuery(sql, getEntityClass());
        setParameters(query, param);
        setPagination(query, param);
        return query;
    }

    // Tạo TypedQuery cho COUNT
    private TypedQuery<Long> createCountQuery(ProductVariantParamRequest param) {
        TypedQuery<Long> query = entityManager.createQuery(buildCountQuery(param), Long.class);
        setParameters(query, param);
        return query;
    }

    private void addConditionsToQueryBuilder(QueryBuilder queryBuilder, ProductVariantParamRequest param) {
        if (StringUtils.hasLength(param.getSearchBySKU())) {
            queryBuilder.addCondition("entity.name LIKE :searchBySKU");
        }
        if (param.getSizeId() != null) {
            queryBuilder.addCondition("entity.size.id = :sizeId");
        }
        if (param.getColorId() != null) {
            queryBuilder.addCondition("entity.color.id = :colorId");
        }
        if (param.getPromotionId() != null) {
            queryBuilder.addCondition("entity.promotion.id = :promotionId");
        }
        // Điều kiện lọc theo khoảng giá
        if (param.getMinPrice() != null) {
            queryBuilder.addCondition("entity.price >= :minPrice");
        }
        if (param.getMaxPrice() != null) {
            queryBuilder.addCondition("entity.price <= :maxPrice");
        }
        if (param.getStatus() != null) {
            queryBuilder.addCondition("entity.status = :status");
        }
    }

    private <Q> void setParameters(TypedQuery<Q> query, ProductVariantParamRequest param) {
        if (StringUtils.hasLength(param.getSearchBySKU())) {
            query.setParameter("searchBySKU", String.format(STARTS_WITH_LIKE_FORMAT, param.getSearchBySKU()));
            System.out.println(query.getParameter("searchBySKU"));
        }
        if (param.getSizeId() != null) {
            query.setParameter("sizeId", param.getSizeId());
        }

        if (param.getColorId() != null) {
            query.setParameter("colorId", param.getColorId());
        }

        if (param.getPromotionId() != null) {
            query.setParameter("promotionId", param.getPromotionId());
        }

        if (param.getMinPrice() != null) {
            query.setParameter("minPrice", param.getMinPrice());
        }

        if (param.getMaxPrice() != null) {
            query.setParameter("maxPrice", param.getMaxPrice());
        }

        if (param.getStatus() != null) {
            query.setParameter("status", param.getStatus().getValue());
        }
    }

    private void setPagination(TypedQuery<ProductVariant> query, ProductVariantParamRequest param) {
        query.setFirstResult((param.getPageNo() - 1) * param.getPageSize());
        query.setMaxResults(param.getPageSize());
    }

    public long getTotalElements(ProductVariantParamRequest param) {
        TypedQuery<Long> countQuery = createCountQuery(param);
        return countQuery.getSingleResult();
    }

    // Tạo PageableResponse
    public PageableResponse createPageableResponse(ProductVariantParamRequest param, long totalElements, List<ProductVariant> content) {
        return PageableResponse.builder()
                .totalElements(totalElements)
                .pageSize(param.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / param.getPageSize()))
                .pageNo(param.getPageNo())
                .content(content)
                .build();
    }
}
