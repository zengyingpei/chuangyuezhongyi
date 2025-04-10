package com.zyp.service.impl;

import com.zyp.mapper.CategoryMapper;
import com.zyp.mapper.MedicineMapper;
import com.zyp.pojo.Category;
import com.zyp.pojo.Medicine;
import com.zyp.service.MedicineService;
import com.zyp.vo.KeepHeathyAndCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Autowired
    private MedicineMapper medicineMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * @ description 获取所有治疗药物(可按名称)
     *
     * @ return java.util.List<com.zyp.pojo.Medicine>
     * @ author DELL
     */
    public List<Medicine> selectAll1(String name) {
        return medicineMapper.selectAll1(name);
    }

    /**
     * @ description 获取养生药物(可按分类和名称)
     *
     * @ return com.zyp.vo.KeepHeathyAndCategoryVo
     * @ author DELL
     */
    public KeepHeathyAndCategoryVo selectAll2(Long categoryId,String name) {
        // 获取所有的养生品
        List<Medicine> medicines = medicineMapper.selectAll2(categoryId,name);
        // 获取所有的养生分类
        List<Category> categories = categoryMapper.selectAll();
        return KeepHeathyAndCategoryVo.builder()
                .categories(categories)
                .medicines(medicines)
                .build();
    }


}
