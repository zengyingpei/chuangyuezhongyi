package com.zyp.service;

import com.zyp.dto.ShoppingCartAddNewDto;
import com.zyp.dto.ShoppingCartAddOneDto;
import com.zyp.pojo.Result;
import com.zyp.pojo.ShoppingCart;
import com.zyp.vo.ShoppingCartVo;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartVo> selectByUserId();

    void addOne(ShoppingCartAddOneDto shoppingCartAddOneDto);

    void addNew(ShoppingCartAddNewDto shoppingCartAddNewDto);

    void deleteOne(Long id);
}
