package com.zyp.mapper;

import com.zyp.pojo.Address;
import com.zyp.vo.AddressVo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressMapper {
    /**
     * @ description 查询用户的所有地址
     * @param userId
     * @ return java.util.List<com.zyp.vo.AddressVo>
     * @ author DELL
     */
    @Select("select * from address where user_id = #{userId}")
    List<AddressVo> selectAll(Long userId);

    /**
     * @ description 根据id查询地址信息
     * @param id
     * @ return com.zyp.vo.AddressVo
     * @ author DELL
     */
    @Select("select * from address where id = #{id}")
    Address selectById(Long id);

    /**
     * @ description 更新地址信息
     * @param address
     * @ return void
     * @ author DELL
     */
    void update(Address address);

    /**
     * @ description 新增地址
     * @param address
     * @ return void
     * @ author DELL
     */
    @Insert("insert into address (user_id, receiver_name, receiver_phone, province, city, district, detail,is_default) values " +
            "(#{userId}, #{receiverName},#{receiverPhone},#{province},#{city},#{district},#{detail},#{isDefault})")
    void add(Address address);

    /**
     * @ description 把原本来的默认地址取消
     *
     * @ return void
     * @ author DELL
     */
    @Update("update address set is_default = 0 where is_default = 1")
    void updateOldDefault();

    /**
     * @ description 删除地址
     * @param id
     * @ return void
     * @ author DELL
     */
    @Delete("delete from address where id = #{id}")
    void delete(Long id);
}
