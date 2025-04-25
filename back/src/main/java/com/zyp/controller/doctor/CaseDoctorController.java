package com.zyp.controller.doctor;

import com.zyp.dto.CreateCaseDTO;
import com.zyp.pojo.Result;
import com.zyp.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor/case")
public class CaseDoctorController {
    @Autowired
    private CaseService caseService;

    @PostMapping("/createcase")
    public Result createCase(@RequestBody CreateCaseDTO caseDTO){
        caseService.createCase(caseDTO);
        return Result.success();
    }
}
