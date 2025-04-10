package com.zyp.vo;

import com.zyp.pojo.Category;
import com.zyp.pojo.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeepHeathyAndCategoryVo implements Serializable {
    // 所有的分类信息
    private List<Category> categories;
    // 所有养生药品
    private List<Medicine> medicines;
}
