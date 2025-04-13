package com.zyp.service;

import com.zyp.dto.ClientDTO;
import com.zyp.vo.ClientVO;

import java.util.List;

public interface ClientService {

    List<ClientVO> selectAll();

    void insert(ClientDTO clientDTO);

    void delete(Long id);
}
