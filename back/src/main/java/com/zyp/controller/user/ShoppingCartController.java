package com.zyp.controller.user;

import com.zyp.dto.ShoppingCartAddNewDto;
import com.zyp.dto.ShoppingCartAddOneDto;
import com.zyp.pojo.Result;
import com.zyp.service.ShoppingCartService;
import com.zyp.vo.ShoppingCartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/shoppingcart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    /**
     * @ description 根据用户id获取购物车信息
     * @param
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @GetMapping("/list")
    public Result<List<ShoppingCartVo>> selectByUserId(){
        return Result.success(shoppingCartService.selectByUserId());
    }

    /**
     * @ description 新增购物车
     * @param shoppingCartAddNewDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/addNew")
    public Result addNew(@RequestBody ShoppingCartAddNewDto shoppingCartAddNewDto){
        shoppingCartService.addNew(shoppingCartAddNewDto);
        return Result.success();
    }


    /**
     * @ description 增加药物购买数量
     * @param shoppingCartAddOneDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/addOne")
    public Result addOne(@RequestBody ShoppingCartAddOneDto shoppingCartAddOneDto){
        shoppingCartService.addOne(shoppingCartAddOneDto);
        return Result.success();
    }

    /**
     * @ description 删除一条购物车记录
     * @param shoppingCartAddOneDto
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/delete")
    public Result deleteOne(@RequestBody ShoppingCartAddOneDto shoppingCartAddOneDto){
        Long id = shoppingCartAddOneDto.getId();
        shoppingCartService.deleteOne(id);
        return Result.success();
    }
}
