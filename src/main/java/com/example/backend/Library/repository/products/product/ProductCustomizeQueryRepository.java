package com.example.backend.Library.repository.products.product;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.request.products.product.ProductParamRequest;
import com.example.backend.Library.model.entity.products.Product;
import com.example.backend.Library.repository.QueryBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public  class ProductCustomizeQueryRepository{

    @PersistenceContext
    protected EntityManager entityManager;

    private static final String WILDCARD_LIKE_FORMAT = "%%%s%%"; // Cho tìm kiếm có ký tự đại diện ở cả đầu và cuối
    private static final String STARTS_WITH_LIKE_FORMAT = "%s%%"; // Cho tìm kiếm bắt đầu bằng chuỗi
    private static final String ENDS_WITH_LIKE_FORMAT = "%%%s"; // Cho tìm kiếm kết thúc bằng chuỗi
    private   String getEntityName(){
        return "Product";
    };

    private  Class<Product> getEntityClass(){
        return Product.class;
    };


    public List<Product> getWithPagination(ProductParamRequest param) {
        String sql = buildSelectQuery("SELECT entity FROM " + getEntityName() + " entity",param);
        TypedQuery<Product> query = createQueryWithPagination(param, sql);
        return query.getResultList();
    }
    public PageableResponse getPageData(ProductParamRequest param) {
        long totalElements = getTotalElements(param);
        List<Product> content = getWithPagination(param);
        return createPageableResponse(param, totalElements, content);
    }

    private String buildSelectQuery(String sqlQuery ,ProductParamRequest param) {
        QueryBuilder queryBuilder = new QueryBuilder(sqlQuery);
        addConditionsToQueryBuilder(queryBuilder, param);
        queryBuilder.addOrder(param.getSortBy(), param.getSortDir());
        return queryBuilder.build();
    }

    // Tạo câu truy vấn COUNT
    private String buildCountQuery(ProductParamRequest param) {
        QueryBuilder queryBuilder = new QueryBuilder("SELECT COUNT(entity) FROM " + getEntityName() + " entity");
        addConditionsToQueryBuilder(queryBuilder, param);
        return queryBuilder.build();
    }

    // Tạo TypedQuery và  thiết lập tham số và phân trang
    private TypedQuery<Product> createQueryWithPagination(ProductParamRequest param, String sql) {
        TypedQuery<Product> query = entityManager.createQuery(sql, getEntityClass());
        setParameters(query, param);
        setPagination(query, param);
        return query;
    }

    // Tạo TypedQuery cho COUNT
    private TypedQuery<Long> createCountQuery(ProductParamRequest param) {
        TypedQuery<Long> query = entityManager.createQuery(buildCountQuery(param), Long.class);
        setParameters(query, param);
        return query;
    }

    private void addConditionsToQueryBuilder(QueryBuilder queryBuilder, ProductParamRequest param) {
        if (StringUtils.hasLength(param.getSearchByName())) {
            queryBuilder.addCondition("entity.name LIKE :filterKeyword");
        }
        if (param.getCategoryId() != null) {
            queryBuilder.addCondition("entity.category.id = :categoryId");
        }
        if (param.getBrandId() != null) {
            queryBuilder.addCondition("entity.brand.id = :brandId");
        }
        if (param.getMaterialId() != null) {
            queryBuilder.addCondition("entity.material.id = :materialId");
        }
        if (param.getSoleId() != null) {
            queryBuilder.addCondition("entity.sole.id = :soleId");
        }
        if (param.getStatus() != null) {
            queryBuilder.addCondition("entity.status = :status");
        }
    }

    private <Q> void setParameters(TypedQuery<Q> query, ProductParamRequest param) {
        if (StringUtils.hasLength(param.getSearchByName())) {
            query.setParameter("filterKeyword", String.format(STARTS_WITH_LIKE_FORMAT, param.getSearchByName()));
            System.out.println(query.getParameter("filterKeyword"));
        }
        if (param.getCategoryId() != null) {
            query.setParameter("categoryId", param.getCategoryId());
        }
        if (param.getBrandId() != null) {
            query.setParameter("brandId", param.getBrandId());
        }
        if (param.getMaterialId() != null) {
            query.setParameter("materialId", param.getMaterialId());
        }
        if (param.getSoleId() != null) {
            query.setParameter("soleId", param.getSoleId());
        }
        if (param.getStatus() != null) {
            query.setParameter("status", param.getStatus().getValue());
        }
    }

    private void setPagination(TypedQuery<Product> query, ProductParamRequest param) {
        query.setFirstResult((param.getPageNo() - 1) * param.getPageSize());
        query.setMaxResults(param.getPageSize());
    }

    public long getTotalElements(ProductParamRequest param) {
        TypedQuery<Long> countQuery = createCountQuery(param);
        return countQuery.getSingleResult();
    }

    // Tạo PageableResponse
    public PageableResponse createPageableResponse(ProductParamRequest param, long totalElements, List<Product> content) {
        return PageableResponse.builder()
                .totalElements(totalElements)
                .pageSize(param.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / param.getPageSize()))
                .pageNo(param.getPageNo())
                .content(content)
                .build();
    }
}
