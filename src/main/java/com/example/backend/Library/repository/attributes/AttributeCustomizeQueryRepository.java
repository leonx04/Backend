package com.example.backend.Library.repository.attributes;

import com.example.backend.Library.model.dto.reponse.PageableResponse;
import com.example.backend.Library.model.dto.request.attributes.AttributeParamRequest;
import com.example.backend.Library.repository.QueryBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.util.StringUtils;

import java.util.List;
public abstract class AttributeCustomizeQueryRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    private static final String WILDCARD_LIKE_FORMAT = "%%%s%%"; // Cho tìm kiếm có ký tự đại diện ở cả đầu và cuối
    private static final String STARTS_WITH_LIKE_FORMAT = "%s%%"; // Cho tìm kiếm bắt đầu bằng chuỗi
    private static final String ENDS_WITH_LIKE_FORMAT = "%%%s"; // Cho tìm kiếm kết thúc bằng chuỗi
    protected abstract String getEntityName();

    protected abstract Class<T> getEntityClass();

    public List<T> getWithPagination(AttributeParamRequest param) {
        String sql = buildSelectQuery("SELECT entity FROM " + getEntityName() + " entity",param);
        TypedQuery<T> query = createQueryWithPagination(param, sql);
        return query.getResultList();
    }

    public PageableResponse getPageData(AttributeParamRequest param) {
        long totalElements = getTotalElements(param);
        List<T> content = getWithPagination(param);
        return createPageableResponse(param, totalElements, content);
    }

    private String buildSelectQuery(String sqlQuery ,AttributeParamRequest param) {
        //Dựa vào Param builder ra Query.
        QueryBuilder queryBuilder = new QueryBuilder(sqlQuery);

        if (StringUtils.hasLength(param.getSearchByName())) {//!=null
            queryBuilder.addCondition("entity.name LIKE :filterKeyword");
        }
        if(StringUtils.hasLength(param.getSortBy())){//!=null
            queryBuilder.addOrder(param.getSortBy(), param.getSortDir());
        }
        return queryBuilder.build();
    }

    // Tạo câu truy vấn COUNT
    private String buildCountQuery(AttributeParamRequest param) {
        QueryBuilder queryBuilder = new QueryBuilder("SELECT COUNT(entity) FROM " + getEntityName() + " entity");

        if (StringUtils.hasLength(param.getSearchByName())) {
            System.out.println(param.getSearchByName());
            queryBuilder.addCondition("entity.name LIKE :filterKeyword");
        }
        return queryBuilder.build();
    }

    // Tạo TypedQuery và  thiết lập tham số và phân trang
    private TypedQuery<T> createQueryWithPagination(AttributeParamRequest param, String sql) {
        TypedQuery<T> query = entityManager.createQuery(sql, getEntityClass());
        setParameters(query, param);
        setPagination(query, param);
        return query;
    }

    // Tạo TypedQuery cho COUNT
    private TypedQuery<Long> createCountQuery(AttributeParamRequest param) {
        TypedQuery<Long> query = entityManager.createQuery(buildCountQuery(param), Long.class);
        setParameters(query, param);
        return query;
    }


    private <Q> void setParameters(TypedQuery<Q> query, AttributeParamRequest param) {
        if (StringUtils.hasLength(param.getSearchByName())) {
            query.setParameter("filterKeyword", String.format(STARTS_WITH_LIKE_FORMAT, param.getSearchByName()));
            System.out.println(query.getParameter("filterKeyword"));
        }
    }

    private void setPagination(TypedQuery<T> query, AttributeParamRequest param) {
        query.setFirstResult((param.getPageNo() - 1) * param.getPageSize());
        query.setMaxResults(param.getPageSize());
    }

    public long getTotalElements(AttributeParamRequest param) {
        TypedQuery<Long> countQuery = createCountQuery(param);
        return countQuery.getSingleResult();
    }

    // Tạo PageableResponse
    public PageableResponse createPageableResponse(AttributeParamRequest param, long totalElements, List<T> content) {
        return PageableResponse.builder()
                .totalElements(totalElements)
                .pageSize(param.getPageSize())
                .totalPages((int) Math.ceil((double) totalElements / param.getPageSize()))
                .pageNo(param.getPageNo())
                .content(content)
                .build();
    }
}
