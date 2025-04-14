package com.zyp.mapper;

import com.zyp.pojo.Client;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClientMapper {
    @Select("select * from client where user_id = #{userId}")
    List<Client> selectAll(Long userId);

    @Insert("insert into client(user_id, name, phone, age, gender, id_number) value (#{userId},#{name},#{phone},#{age},#{gender},#{idNumber})")
    void insert(Client client);

    @Delete("delete from client where id = #{id} and user_id = #{userId}")
    void delete(Long id, Long userId);

    @Select("select * from client where id = #{id}")
    Client selectById(Long id);

    /**
     * @ description 根据用户id查询对应的clientid
     * @param userId
     * @ return java.util.List<java.lang.Long>
     * @ author DELL
     */
    @Select("select id from client where user_id = #{userId}")
    List<Long> selectClientIdByUserId(Long userId);
}
