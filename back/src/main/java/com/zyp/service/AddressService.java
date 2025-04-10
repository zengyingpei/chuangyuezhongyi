package com.zyp.service;

import com.zyp.pojo.Address;
import com.zyp.vo.AddressVo;

import java.util.List;

public interface AddressService {
    List<AddressVo> selectAll();

    AddressVo selectById(Long id);

    void update(Address address);

    void add(Address address);

    void delete(Long id);
}
