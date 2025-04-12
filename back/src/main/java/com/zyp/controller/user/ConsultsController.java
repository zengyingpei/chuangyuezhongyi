package com.zyp.controller.user;

import com.zyp.pojo.Result;
import com.zyp.service.ConsultsService;
import com.zyp.vo.ConsultsListVO;
import com.zyp.vo.ConsultsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user/consults")
public class ConsultsController {
    @Autowired
    private ConsultsService consultsService;

    @GetMapping("/list")
    public Result<List<ConsultsListVO>> getConsultsList(){
        List<ConsultsListVO> list = consultsService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/detail")
    public Result<ConsultsVO> getConsultsDetail(@RequestParam Long id){
        ConsultsVO consultsVO = consultsService.selectById(id);
        return Result.success(consultsVO);
    }
}
