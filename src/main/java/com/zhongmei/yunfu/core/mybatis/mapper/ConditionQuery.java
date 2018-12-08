package com.zhongmei.yunfu.core.mybatis.mapper;

import com.baomidou.mybatisplus.entity.Column;
import com.baomidou.mybatisplus.entity.Columns;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.Collection;
import java.util.Map;

public class ConditionQuery<T> extends Wrapper<T> implements INotEmpty<T> {

    private Wrapper<T> wrapper;

    public static <T> Wrapper<T> create(Wrapper<T> wrapper) {
        return create(wrapper, false);
    }

    public static <T> Wrapper<T> create(Wrapper<T> wrapper, boolean isWhere) {
        return new ConditionQuery(wrapper, isWhere);
    }

    public ConditionQuery(Wrapper<T> wrapper, boolean isWhere) {
        this.wrapper = wrapper;
        this.isWhere = isWhere;
    }

    @Override
    public T getEntity() {
        return wrapper.getEntity();
    }

    @Override
    public boolean isEmptyOfWhere() {
        return wrapper.isEmptyOfWhere();
    }

    @Override
    public boolean isNotEmptyOfWhere() {
        return wrapper.isNotEmptyOfWhere();
    }

    @Override
    public String getSqlSelect() {
        return wrapper.getSqlSelect();
    }

    @Override
    public Wrapper<T> setSqlSelect(String sqlSelect) {
        return wrapper.setSqlSelect(sqlSelect);
    }

    @Override
    public Wrapper<T> setSqlSelect(String... columns) {
        return wrapper.setSqlSelect(columns);
    }

    @Override
    public Wrapper<T> setSqlSelect(Column... column) {
        return wrapper.setSqlSelect(column);
    }

    @Override
    public Wrapper<T> setSqlSelect(Columns columns) {
        return wrapper.setSqlSelect(columns);
    }

    @Override
    public String getSqlSegment() {
        return wrapper.getSqlSegment();
    }

    @Override
    public String toString() {
        return wrapper.toString();
    }

    @Override
    public String originalSql() {
        return wrapper.originalSql();
    }

    @Override
    public Wrapper<T> where(boolean condition, String sqlWhere, Object... params) {
        return wrapper.where(condition, sqlWhere, params);
    }

    @Override
    public Wrapper<T> where(String sqlWhere, Object... params) {
        return wrapper.where(sqlWhere, params);
    }

    @Override
    public Wrapper<T> eq(boolean condition, String column, Object params) {
        return wrapper.eq(condition, column, params);
    }

    @Override
    public Wrapper<T> eq(String column, Object params) {
        return wrapper.eq(column, params);
    }

    @Override
    public Wrapper<T> ne(boolean condition, String column, Object params) {
        return wrapper.ne(condition, column, params);
    }

    @Override
    public Wrapper<T> ne(String column, Object params) {
        return wrapper.ne(column, params);
    }

    @Override
    public Wrapper<T> allEq(boolean condition, Map<String, Object> params) {
        return wrapper.allEq(condition, params);
    }

    @Override
    public Wrapper<T> allEq(Map<String, Object> params) {
        return wrapper.allEq(params);
    }

    @Override
    public Wrapper<T> gt(boolean condition, String column, Object params) {
        return wrapper.gt(condition, column, params);
    }

    @Override
    public Wrapper<T> gt(String column, Object params) {
        return wrapper.gt(column, params);
    }

    @Override
    public Wrapper<T> ge(boolean condition, String column, Object params) {
        return wrapper.ge(condition, column, params);
    }

    @Override
    public Wrapper<T> ge(String column, Object params) {
        return wrapper.ge(column, params);
    }

    @Override
    public Wrapper<T> lt(boolean condition, String column, Object params) {
        return wrapper.lt(condition, column, params);
    }

    @Override
    public Wrapper<T> lt(String column, Object params) {
        return wrapper.lt(column, params);
    }

    @Override
    public Wrapper<T> le(boolean condition, String column, Object params) {
        return wrapper.le(condition, column, params);
    }

    @Override
    public Wrapper<T> le(String column, Object params) {
        return wrapper.le(column, params);
    }

    @Override
    public Wrapper<T> and(boolean condition, String sqlAnd, Object... params) {
        return wrapper.and(condition, sqlAnd, params);
    }

    @Override
    public Wrapper<T> and(String sqlAnd, Object... params) {
        return wrapper.and(sqlAnd, params);
    }

    @Override
    public Wrapper<T> andNew(boolean condition, String sqlAnd, Object... params) {
        return wrapper.andNew(condition, sqlAnd, params);
    }

    @Override
    public Wrapper<T> andNew() {
        return wrapper.andNew();
    }

    @Override
    public Wrapper<T> andNew(String sqlAnd, Object... params) {
        return wrapper.andNew(sqlAnd, params);
    }

    @Override
    public Wrapper<T> and() {
        return wrapper.and();
    }

    @Override
    public Wrapper<T> or() {
        return wrapper.or();
    }

    @Override
    public Wrapper<T> or(boolean condition, String sqlOr, Object... params) {
        return wrapper.or(condition, sqlOr, params);
    }

    @Override
    public Wrapper<T> or(String sqlOr, Object... params) {
        return wrapper.or(sqlOr, params);
    }

    @Override
    public Wrapper<T> orNew() {
        return wrapper.orNew();
    }

    @Override
    public Wrapper<T> orNew(boolean condition, String sqlOr, Object... params) {
        return wrapper.orNew(condition, sqlOr, params);
    }

    @Override
    public Wrapper<T> orNew(String sqlOr, Object... params) {
        return wrapper.orNew(sqlOr, params);
    }

    @Override
    public Wrapper<T> groupBy(boolean condition, String columns) {
        return wrapper.groupBy(condition, columns);
    }

    @Override
    public Wrapper<T> groupBy(String columns) {
        return wrapper.groupBy(columns);
    }

    @Override
    public Wrapper<T> having(boolean condition, String sqlHaving, Object... params) {
        return wrapper.having(condition, sqlHaving, params);
    }

    @Override
    public Wrapper<T> having(String sqlHaving, Object... params) {
        return wrapper.having(sqlHaving, params);
    }

    @Override
    public Wrapper<T> orderBy(boolean condition, String columns) {
        return wrapper.orderBy(condition, columns);
    }

    @Override
    public Wrapper<T> orderBy(String columns) {
        return wrapper.orderBy(columns);
    }

    @Override
    public Wrapper<T> orderBy(boolean condition, String columns, boolean isAsc) {
        return wrapper.orderBy(condition, columns, isAsc);
    }

    @Override
    public Wrapper<T> orderBy(boolean condition, Collection<String> columns, boolean isAsc) {
        return wrapper.orderBy(condition, columns, isAsc);
    }

    @Override
    public Wrapper<T> orderBy(String columns, boolean isAsc) {
        return wrapper.orderBy(columns, isAsc);
    }

    @Override
    public Wrapper<T> orderAsc(Collection<String> columns) {
        return wrapper.orderAsc(columns);
    }

    @Override
    public Wrapper<T> orderDesc(Collection<String> columns) {
        return wrapper.orderDesc(columns);
    }

    @Override
    public Wrapper<T> like(boolean condition, String column, String value) {
        return wrapper.like(condition, column, value);
    }

    @Override
    public Wrapper<T> like(String column, String value) {
        return wrapper.like(column, value);
    }

    @Override
    public Wrapper<T> notLike(boolean condition, String column, String value) {
        return wrapper.notLike(condition, column, value);
    }

    @Override
    public Wrapper<T> notLike(String column, String value) {
        return wrapper.notLike(column, value);
    }

    @Override
    public Wrapper<T> like(boolean condition, String column, String value, SqlLike type) {
        return wrapper.like(condition, column, value, type);
    }

    @Override
    public Wrapper<T> like(String column, String value, SqlLike type) {
        return wrapper.like(column, value, type);
    }

    @Override
    public Wrapper<T> notLike(boolean condition, String column, String value, SqlLike type) {
        return wrapper.notLike(condition, column, value, type);
    }

    @Override
    public Wrapper<T> notLike(String column, String value, SqlLike type) {
        return wrapper.notLike(column, value, type);
    }

    @Override
    public Wrapper<T> isNotNull(boolean condition, String columns) {
        return wrapper.isNotNull(condition, columns);
    }

    @Override
    public Wrapper<T> isNotNull(String columns) {
        return wrapper.isNotNull(columns);
    }

    @Override
    public Wrapper<T> isNull(boolean condition, String columns) {
        return wrapper.isNull(condition, columns);
    }

    @Override
    public Wrapper<T> isNull(String columns) {
        return wrapper.isNull(columns);
    }

    @Override
    public Wrapper<T> exists(boolean condition, String value) {
        return wrapper.exists(condition, value);
    }

    @Override
    public Wrapper<T> exists(String value) {
        return wrapper.exists(value);
    }

    @Override
    public Wrapper<T> notExists(boolean condition, String value) {
        return wrapper.notExists(condition, value);
    }

    @Override
    public Wrapper<T> notExists(String value) {
        return wrapper.notExists(value);
    }

    @Override
    public Wrapper<T> in(boolean condition, String column, String value) {
        return wrapper.in(condition, column, value);
    }

    @Override
    public Wrapper<T> in(String column, String value) {
        return wrapper.in(column, value);
    }

    @Override
    public Wrapper<T> notIn(boolean condition, String column, String value) {
        return wrapper.notIn(condition, column, value);
    }

    @Override
    public Wrapper<T> notIn(String column, String value) {
        return wrapper.notIn(column, value);
    }

    @Override
    public Wrapper<T> in(boolean condition, String column, Collection<?> value) {
        return wrapper.in(condition, column, value);
    }

    @Override
    public Wrapper<T> in(String column, Collection<?> value) {
        return wrapper.in(column, value);
    }

    @Override
    public Wrapper<T> notIn(boolean condition, String column, Collection<?> value) {
        return wrapper.notIn(condition, column, value);
    }

    @Override
    public Wrapper<T> notIn(String column, Collection<?> value) {
        return wrapper.notIn(column, value);
    }

    @Override
    public Wrapper<T> in(boolean condition, String column, Object[] value) {
        return wrapper.in(condition, column, value);
    }

    @Override
    public Wrapper<T> in(String column, Object[] value) {
        return wrapper.in(column, value);
    }

    @Override
    public Wrapper<T> notIn(boolean condition, String column, Object... value) {
        return wrapper.notIn(condition, column, value);
    }

    @Override
    public Wrapper<T> notIn(String column, Object... value) {
        return wrapper.notIn(column, value);
    }

    @Override
    public Wrapper<T> between(boolean condition, String column, Object val1, Object val2) {
        return wrapper.between(condition, column, val1, val2);
    }

    @Override
    public Wrapper<T> between(String column, Object val1, Object val2) {
        return wrapper.between(column, val1, val2);
    }

    @Override
    public Wrapper<T> notBetween(boolean condition, String column, Object val1, Object val2) {
        return wrapper.notBetween(condition, column, val1, val2);
    }

    @Override
    public Wrapper<T> notBetween(String column, Object val1, Object val2) {
        return wrapper.notBetween(column, val1, val2);
    }

    @Override
    public Wrapper<T> addFilter(String sqlWhere, Object... params) {
        return wrapper.addFilter(sqlWhere, params);
    }

    @Override
    public Wrapper<T> addFilterIfNeed(boolean need, String sqlWhere, Object... params) {
        return wrapper.addFilterIfNeed(need, sqlWhere, params);
    }

    @Override
    public Wrapper<T> isWhere(Boolean bool) {
        return wrapper.isWhere(bool);
    }

    @Override
    public Wrapper<T> last(String limit) {
        return wrapper.last(limit);
    }

    @Override
    public Map<String, Object> getParamNameValuePairs() {
        return wrapper.getParamNameValuePairs();
    }

    @Override
    public String getParamAlias() {
        return wrapper.getParamAlias();
    }

    @Override
    public Wrapper<T> setParamAlias(String paramAlias) {
        return wrapper.setParamAlias(paramAlias);
    }

    @Override
    public Wrapper<T> clone() {
        return wrapper.clone();
    }
}
