package com.zsj.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by zsj on 2017/4/10.
 */
@Mapper
public interface UserDao {

    @Select("select * from user where username = #{username}")
    JSONObject findUserByName(@Param("username") String username);


}
