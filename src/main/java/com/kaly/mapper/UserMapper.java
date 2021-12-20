package com.kaly.mapper;

import com.kaly.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into user (id,account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{id},#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void saveUser(User user);

    @Select("select * from user where token = #{token}")
    User findUserByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") Integer id);

    @Select("select * from user where account_id = #{accountId}")
    User findUserByAccountId(@Param("accountId")String accountId);

    @Update("update user set name=#{name},avatar_url=#{avatarUrl},token=#{token},gmt_modified=#{gmtModified} where id=#{id}")
    void update(User user);
}
