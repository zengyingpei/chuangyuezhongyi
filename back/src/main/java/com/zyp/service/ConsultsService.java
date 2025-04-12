package com.zyp.service;

import com.zyp.vo.ConsultsListVO;
import com.zyp.vo.ConsultsVO;

import java.util.List;

public interface ConsultsService {

    List<ConsultsListVO> selectAll();

    ConsultsVO selectById(Long id);
}
