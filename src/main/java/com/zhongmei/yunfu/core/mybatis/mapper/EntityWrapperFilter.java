package com.zhongmei.yunfu.core.mybatis.mapper;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

public class EntityWrapperFilter<T> extends EntityWrapper<T> implements INotEmpty {

    public EntityWrapperFilter() {
        this(false);
    }

    public EntityWrapperFilter(Boolean isWhere) {
        this.isWhere = isWhere;
    }

    public EntityWrapperFilter(T entity) {
        this(entity, false);
    }

    public EntityWrapperFilter(T entity, Boolean isWhere) {
        super(entity);
        this.isWhere = isWhere;
    }

    public EntityWrapperFilter(T entity, String sqlSelect) {
        super(entity, sqlSelect);
    }

    private boolean isNotEmpty(Object params) {
        return !ObjectUtils.isEmpty(params);
    }

    @Override
    public Wrapper eq(String column, Object params) {
        return eq(isNotEmpty(params), column, params);
    }

    @Override
    public Wrapper ne(String column, Object params) {
        return ne(isNotEmpty(params), column, params);
    }

    @Override
    public Wrapper gt(String column, Object params) {
        return gt(isNotEmpty(params), column, params);
    }

    @Override
    public Wrapper ge(String column, Object params) {
        return ge(isNotEmpty(params), column, params);
    }

    @Override
    public Wrapper lt(String column, Object params) {
        return lt(isNotEmpty(params), column, params);
    }

    @Override
    public Wrapper le(String column, Object params) {
        return le(isNotEmpty(params), column, params);
    }

    @Override
    public Wrapper and(String sqlAnd, Object... params) {
        return and(isNotEmpty(params), sqlAnd, params);
    }

    @Override
    public Wrapper andNew(String sqlAnd, Object... params) {
        return andNew(isNotEmpty(params), sqlAnd, params);
    }

    @Override
    public Wrapper or(String sqlOr, Object... params) {
        return or(isNotEmpty(params), sqlOr, params);
    }

    @Override
    public Wrapper orNew(String sqlOr, Object... params) {
        return orNew(isNotEmpty(params), sqlOr, params);
    }

    @Override
    public Wrapper having(String sqlHaving, Object... params) {
        return having(isNotEmpty(params), sqlHaving, params);
    }

    @Override
    public Wrapper like(String column, String value) {
        return like(isNotEmpty(value), column, value);
    }

    @Override
    public Wrapper notLike(String column, String value) {
        return notLike(isNotEmpty(value), column, value);
    }

    @Override
    public Wrapper like(String column, String value, SqlLike type) {
        return like(isNotEmpty(value), column, value, type);
    }

    @Override
    public Wrapper notLike(String column, String value, SqlLike type) {
        return notLike(isNotEmpty(value), column, value, type);
    }

    @Override
    public Wrapper exists(String value) {
        return exists(isNotEmpty(value), value);
    }

    @Override
    public Wrapper notExists(String value) {
        return notExists(isNotEmpty(value), value);
    }

    @Override
    public Wrapper in(String column, String value) {
        return in(isNotEmpty(value), column, value);
    }

    @Override
    public Wrapper notIn(String column, String value) {
        return notIn(isNotEmpty(value), column, value);
    }

    @Override
    public Wrapper in(String column, Collection value) {
        return in(isNotEmpty(value), column, value);
    }

    @Override
    public Wrapper notIn(String column, Collection value) {
        return notIn(isNotEmpty(value), column, value);
    }

    @Override
    public Wrapper in(String column, Object[] value) {
        return in(isNotEmpty(value), column, value);
    }

    @Override
    public Wrapper notIn(String column, Object... value) {
        return notIn(isNotEmpty(value), column, value);
    }

}
