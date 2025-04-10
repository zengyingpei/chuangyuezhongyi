package com.zyp.controller.user;

import com.zyp.pojo.Address;
import com.zyp.pojo.Result;
import com.zyp.service.AddressService;
import com.zyp.vo.AddressVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/user/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * @ description 查询用户的所有地址
     * @param
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.vo.AddressVo>>
     * @ author DELL
     */
    @GetMapping("/list")
    public Result<List<AddressVo>> selectAll(){
        List<AddressVo> res = addressService.selectAll();
        return Result.success(res);
    }

    /**
     * @ description 根据id查询地址信息
     * @param id
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @GetMapping("/one")
    @Cacheable(cacheNames = "useraddress", key = "#id")
    public Result<AddressVo> selectById(Long id){
        AddressVo res = addressService.selectById(id);
        return Result.success(res);
    }

    /**
     * @ description 更新地址信息
     * @param address
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("update")
    @CacheEvict(cacheNames = "useraddress", allEntries = true)
    public Result update(@RequestBody Address address){
        log.info("{}",address);
        addressService.update(address);
        return Result.success();
    }

    /**
     * @ description 新增地址
     * @param address
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/add")
    @CacheEvict(cacheNames = "useraddress", allEntries = true)
    public Result add(@RequestBody Address address){
        addressService.add(address);
        return Result.success();
    }

    /**
     * @ description 删除地址
     * @param address
     * @ return com.zyp.pojo.Result
     * @ author DELL
     */
    @PostMapping("/del")
    @CacheEvict(cacheNames = "useraddress", allEntries = true)
    public Result delete(@RequestBody Address address){
        Long id = address.getId();
        addressService.delete(id);
        return Result.success();
    }
}
