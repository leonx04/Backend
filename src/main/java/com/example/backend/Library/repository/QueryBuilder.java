package com.example.backend.Library.repository;

public class QueryBuilder {
    private final StringBuilder query;
    private boolean hasCondition = false;

    public QueryBuilder(String baseQuery) {
        this.query = new StringBuilder(baseQuery);
    }

    // Thêm điều kiện WHERE
    public QueryBuilder addCondition(String condition) {
        if (!hasCondition) {
            query.append(" WHERE ").append(condition);
            hasCondition = true;
        } else {
            query.append(" AND ").append(condition);
        }
        return this;
    }

    // Thêm sắp xếp ORDER BY
    public QueryBuilder addOrder(String sortBy, String sortDir) {
        query.append(" ORDER BY ").append(sortBy).append(" ").append(sortDir);
        return this;
    }

    // Xây dựng câu truy vấn cuối cùng
    public String build() {
        return query.toString();
    }
}
