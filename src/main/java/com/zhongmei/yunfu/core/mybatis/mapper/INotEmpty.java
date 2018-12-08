package com.zhongmei.yunfu.core.mybatis.mapper;

import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.util.Collection;

public interface INotEmpty<T> {

    Wrapper<T> eq(String column, Object params);

    Wrapper<T> ne(String column, Object params);

    Wrapper<T> gt(String column, Object params);

    Wrapper<T> ge(String column, Object params);

    Wrapper<T> lt(String column, Object params);

    Wrapper<T> le(String column, Object params);

    Wrapper<T> and(String sqlAnd, Object... params);

    Wrapper<T> andNew(String sqlAnd, Object... params);

    Wrapper<T> or(String sqlOr, Object... params);

    Wrapper<T> orNew(String sqlOr, Object... params);

    Wrapper<T> having(String sqlHaving, Object... params);

    Wrapper<T> like(String column, String value);

    Wrapper<T> notLike(String column, String value);

    Wrapper<T> like(String column, String value, SqlLike type);

    Wrapper<T> notLike(String column, String value, SqlLike type);

    Wrapper<T> exists(String value);

    Wrapper<T> notExists(String value);

    Wrapper<T> in(String column, String value);

    Wrapper<T> notIn(String column, String value);

    Wrapper<T> in(String column, Collection<?> value);

    Wrapper<T> notIn(String column, Collection<?> value);

    Wrapper<T> in(String column, Object[] value);

    Wrapper<T> notIn(String column, Object... value);
}
