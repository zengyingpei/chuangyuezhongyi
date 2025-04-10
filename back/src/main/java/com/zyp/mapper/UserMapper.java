package com.zyp.mapper;

import com.zyp.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("select * from user where openid = #{openid}")
    User selectByOpenid(String openid);

    void insert(User user);

    @Update("update user set name=#{name}, phone=#{phone}, sex=#{sex}, id_number=#{idNumber} where id=#{id}")
    void update(User user);

    @Select("select name from user where id = #{id}")
    String selectNameById(Long id);

}
