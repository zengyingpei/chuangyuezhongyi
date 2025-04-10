package com.zyp.service.impl;

import com.zyp.mapper.AddressMapper;
import com.zyp.pojo.Address;
import com.zyp.service.AddressService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.AddressVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    /**
     * @ description 查询用户的所有地址
     * @param
     * @ return java.util.List<com.zyp.vo.AddressVo>
     * @ author DELL
     */
    public List<AddressVo> selectAll() {
        Long userId = ThreadLocalUtil.get();
        return addressMapper.selectAll(userId);
    }

    /**
     * @ description 根据id查询地址信息
     * @param id
     * @ return com.zyp.vo.AddressVo
     * @ author DELL
     */
    public AddressVo selectById(Long id) {
        Address address = addressMapper.selectById(id);
        AddressVo addressVo =new AddressVo();
        BeanUtils.copyProperties(address, addressVo);
        return addressVo;
    }

    /**
     * @ description 更新地址信息
     * @param address
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void update(Address address) {
        Long userId = ThreadLocalUtil.get();
        address.setUserId(userId);
        // 1、在修改时，要判断当前这个记录是不是要设置成默认地址
        Integer isDefault = address.getIsDefault();
        // 2、要设置成默认地址，先把之前的默认地址删掉，在把当前设置成默认地址
        // 2.1、删掉之前的默认地址
        if(isDefault == 1){
            addressMapper.updateOldDefault();
        }
        // 2.2、最后更新当前地址信息
        addressMapper.update(address);
    }

    /**
     * @ description 新增地址
     * @param address
     * @ return void
     * @ author DELL
     */
    public void add(Address address) {
        address.setUserId(ThreadLocalUtil.get());
        Integer isDefault = address.getIsDefault();
        if(isDefault==1){
            addressMapper.updateOldDefault();
        }
        addressMapper.add(address);
    }

    /**
     * @ description 删除地址
     * @param id
     * @ return void
     * @ author DELL
     */
    public void delete(Long id) {
        addressMapper.delete(id);
    }
}
