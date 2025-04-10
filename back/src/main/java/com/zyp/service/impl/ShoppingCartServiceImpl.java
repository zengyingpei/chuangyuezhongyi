package com.zyp.service.impl;

import com.zyp.dto.ShoppingCartAddNewDto;
import com.zyp.dto.ShoppingCartAddOneDto;
import com.zyp.exception.NoStorageException;
import com.zyp.mapper.MedicineMapper;
import com.zyp.mapper.ShoppingCartMapper;
import com.zyp.pojo.Medicine;
import com.zyp.pojo.Result;
import com.zyp.pojo.ShoppingCart;
import com.zyp.service.ShoppingCartService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.ShoppingCartVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private MedicineMapper medicineMapper;
    /**
     * @ description 根据用户的id查询购物车信息
     * @param
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.pojo.ShoppingCart>>
     * @ author DELL
     */
    public List<ShoppingCartVo> selectByUserId(){
        Long userId = ThreadLocalUtil.get();
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.selectByUserId(userId);
        List<ShoppingCartVo> ans =new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCarts) {
            ShoppingCartVo shoppingCartVo =new ShoppingCartVo();
            BeanUtils.copyProperties(shoppingCart, shoppingCartVo);
            ans.add(shoppingCartVo);
        }
        return ans;
    }

    /**
     * @ description 药物购买数量增加/减少
     * @param shoppingCartAddOneDto
     * @ return void
     * @ author DELL
     */
    public void addOne(ShoppingCartAddOneDto shoppingCartAddOneDto) {
        Long shoppingcartId = shoppingCartAddOneDto.getId();
        Integer num = shoppingCartAddOneDto.getNum();

        // 1、先查询药品信息表，看库存情况
        ShoppingCart shoppingCart = shoppingCartMapper.selectById(shoppingcartId);
        Long medicineId = shoppingCart.getMedicineId();

        Medicine medicine = medicineMapper.selectById(medicineId);
        Integer storage = medicine.getNumber();

        // 判断是要增加还是减少
        if(num>0){  //增加
            // 2、判断库存是够足够，足够才可以增加购买数量
            if(shoppingCart.getNumber()+1<=storage){
                shoppingCartMapper.addOne(shoppingcartId,medicine.getPrice(),1);
            }else{  //否则，增加失败
                throw new NoStorageException("库存不足,无法添加购买数量");
            }
//            if(storage>=1){
//                shoppingCartMapper.addOne(shoppingcartId,medicine.getPrice(),1);
//            }else{  //否则，增加失败
//                throw new NoStorageException("库存不足,无法添加购买数量");
//            }
        }else {      //减少
            //如果，要购买的数量不为0，此时可以继续减少
            if (shoppingCart.getNumber() > 1) {
                shoppingCartMapper.addOne(shoppingcartId,medicine.getPrice(), -1);
            } else {      //此时，购买数量减为零，直接将其从购物车表中删除
                shoppingCartMapper.deleteById(shoppingcartId);
            }
        }
    }

    /**
     * @ description 添加购物车
     * @param shoppingCartAddNewDto
     * @ return void
     * @ author DELL
     */
    public void addNew(ShoppingCartAddNewDto shoppingCartAddNewDto) {
        Long medicineId = shoppingCartAddNewDto.getMedicineId();
        Medicine medicine = medicineMapper.selectById(medicineId);
        // 查询购物车中是否已经有这个药品
        ShoppingCart shoppingCart = shoppingCartMapper.selectByMedicineId(medicineId);

        //购物车还没有
        if(shoppingCart==null){

            Integer storage = medicine.getNumber();
            if(storage >= 1){
                Long userId = ThreadLocalUtil.get();

                shoppingCart = ShoppingCart.builder()
                        .userId(userId)
                        .medicineId(medicineId)
                        .number(1)
                        .name(medicine.getName())
                        .image(medicine.getImage())
                        .price(medicine.getPrice())
//                        .createTime(LocalDateTime.now())
//                        .updateTime(LocalDateTime.now())
                        .build();

                shoppingCartMapper.addNew(shoppingCart);
            }else{
                throw new NoStorageException("库存不足,无法添加购买数量");
            }
        }else{  //购物车已经有了
            //判断库存是否足够
            Integer storage = medicine.getNumber();
            if(shoppingCart.getNumber() + 1 <= storage){
                shoppingCartMapper.addOne(shoppingCart.getId(), medicine.getPrice(),1);
            }else{
                throw new NoStorageException("库存不足,无法添加购买数量");
            }
        }
    }

    /**
     * @ description 删除一条购物车记录
     * @param id
     * @ return void
     * @ author DELL
     */
    public void deleteOne(Long id) {
        shoppingCartMapper.deleteOne(id);
    }
}
