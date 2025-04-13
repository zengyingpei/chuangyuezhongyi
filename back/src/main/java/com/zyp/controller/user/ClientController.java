package com.zyp.controller.user;

import com.zyp.dto.ClientDTO;
import com.zyp.pojo.Result;
import com.zyp.service.ClientService;
import com.zyp.vo.ClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/all")
    public Result<List<ClientVO>> selectAll(){
        List<ClientVO> clientVOS = clientService.selectAll();
        return Result.success(clientVOS);
    }

    @PostMapping("/add")
    public Result insert(@RequestBody ClientDTO clientDTO){
        clientService.insert(clientDTO);
        return Result.success();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Long id){
        clientService.delete(id);
        return Result.success();
    }
}
