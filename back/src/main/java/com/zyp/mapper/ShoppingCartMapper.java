package com.zyp.mapper;

import com.zyp.anno.AutoFill;
import com.zyp.enumeration.OperationType;
import com.zyp.pojo.Result;
import com.zyp.pojo.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    /**
     * @ description 根据用户的id查询购物车信息
     * @param userId
     * @ return java.util.List<com.zyp.pojo.ShoppingCart>
     * @ author DELL
     */
    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> selectByUserId(Long userId);

    /**
     * @ description 药物购买数量增加/减少
     * @param shoppingcartId 
     * @ return void
     * @ author DELL
     */
    @Update("update shopping_cart set number = number + #{num},price =#{num} * #{unit} + price where id = #{shoppingcartId}")
    void addOne(Long shoppingcartId, BigDecimal unit, Integer num);

    /**
     * @ description 根据id查询某条购物车记录信息
     * @param shoppingcartId
     * @ return com.zyp.pojo.ShoppingCart
     * @ author DELL
     */
    @Select("select * from shopping_cart where id = #{shoppingcartId}")
    ShoppingCart selectById(Long shoppingcartId);

    /**
     * @ description 根据id删除记录
     * @param shoppingcartId
     * @ return void
     * @ author DELL
     */
    @Delete("delete from shopping_cart where id =#{shoppingcartId}")
    void deleteById(Long shoppingcartId);

    /**
     * @ description 根据购买的药品信息查询购物车信息
     * @param medicineId
     * @ return com.zyp.pojo.ShoppingCart
     * @ author DELL
     */
    @Select("select * from shopping_cart where medicine_id = #{medicineId}")
    ShoppingCart selectByMedicineId(Long medicineId);

    /**
     * @ description 新增购物车
     * @param shoppingCart
     * @ return void
     * @ author DELL
     */
    @AutoFill(OperationType.INSERT)
    void addNew(ShoppingCart shoppingCart);

    /**
     * @ description 删除一条购物车记录
     * @param id
     * @ return void
     * @ author DELL
     */
    @Delete("delete from shopping_cart where id =#{id}")
    void deleteOne(Long id);
}
