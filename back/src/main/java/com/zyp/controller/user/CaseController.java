package com.zyp.controller.user;

import com.zyp.pojo.Case;
import com.zyp.pojo.Result;
import com.zyp.service.CaseService;
import com.zyp.vo.CaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.List;

@RestController
@RequestMapping("/api/user/case")
public class CaseController implements Serializable {
    @Autowired
    private CaseService caseService;

    /**
     * @ description 查询当前用户关联的所有的就诊人的病例
     *
     * @ return com.zyp.pojo.Result<java.util.List<com.zyp.pojo.Case>>
     * @ author DELL
     */
    @GetMapping("/all")
    public Result<List<CaseVO>> selectCaseById(){
        List<CaseVO> cases = caseService.selectCaseById();
        return Result.success(cases);
    }
}
