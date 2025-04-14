package com.zyp.service.impl;

import com.zyp.mapper.CaseMapper;
import com.zyp.mapper.ClientMapper;
import com.zyp.mapper.DoctorMapper;
import com.zyp.pojo.Case;
import com.zyp.pojo.Client;
import com.zyp.pojo.Doctor;
import com.zyp.service.CaseService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.CaseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {
    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public List<CaseVO> selectCaseById() {
        Long userId = ThreadLocalUtil.get();
        List<Long> clientIds = clientMapper.selectClientIdByUserId(userId);
        List<CaseVO> ans = new ArrayList<>();
        for (Long clientId : clientIds) {
            List<Case> cases = caseMapper.selectByClientId(clientId);
            for (Case c : cases) {
                CaseVO caseVO = new CaseVO();
                BeanUtils.copyProperties(c, caseVO);

                Doctor doctor = doctorMapper.selectById(c.getDoctorId());
                doctor.setPassword(null);
                doctor.setCard(null);
                caseVO.setDoctor(doctor);
                Client client = clientMapper.selectById(c.getClientId());
                caseVO.setClient(client);

                ans.add(caseVO);
            }
        }
        return ans;
    }
}
