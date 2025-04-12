package com.zyp.service.impl;

import com.zyp.mapper.ConsultsMapper;
import com.zyp.pojo.Consults;
import com.zyp.service.ConsultsService;
import com.zyp.vo.ConsultsListVO;
import com.zyp.vo.ConsultsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultsServiceImpl implements ConsultsService {
    @Autowired
    private ConsultsMapper consultsMapper;

    @Override
    public List<ConsultsListVO> selectAll() {
        List<Consults> list = consultsMapper.selectAll();
        List<ConsultsListVO> ans = new ArrayList<>();
        for (Consults consults : list) {
            ConsultsListVO vo = ConsultsListVO.builder()
                    .id(consults.getId())
                    .title(consults.getTitle())
                    .image(consults.getImage())
                    .from(consults.getFrom())
                    .build();
            ans.add(vo);
        }
        return ans;
    }

    @Override
    public ConsultsVO selectById(Long id) {
        Consults consults=consultsMapper.selectById(id);
        ConsultsVO vo = ConsultsVO.builder()
                .id(consults.getId())
                .title(consults.getTitle())
                .image(consults.getImage())
                .from(consults.getFrom())
                .content(consults.getContent())
                .build();

        return vo;
    }
}
