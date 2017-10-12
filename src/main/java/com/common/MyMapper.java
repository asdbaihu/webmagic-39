package com.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by admin on 2017/6/14.
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
