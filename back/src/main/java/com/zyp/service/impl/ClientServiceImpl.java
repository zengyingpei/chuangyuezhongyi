package com.zyp.service.impl;

import com.zyp.dto.ClientDTO;
import com.zyp.mapper.ClientMapper;
import com.zyp.pojo.Client;
import com.zyp.service.ClientService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.ClientVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<ClientVO> selectAll() {
        List<Client> clients = clientMapper.selectAll(ThreadLocalUtil.get());
        List<ClientVO> ans = new ArrayList<>();
        for (Client client : clients) {
            ClientVO clientVO = new ClientVO();
            BeanUtils.copyProperties(client, clientVO);
            if (client.getGender() == 0) {
                clientVO.setGender("男");
            } else {
                clientVO.setGender("女");
            }
            ans.add(clientVO);
        }
        return ans;
    }

    @Override
    public void insert(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        client.setUserId(ThreadLocalUtil.get());
        clientMapper.insert(client);
    }

    @Override
    public void delete(Long id) {
        Long userId = ThreadLocalUtil.get();
        clientMapper.delete(id, userId);
    }
}
