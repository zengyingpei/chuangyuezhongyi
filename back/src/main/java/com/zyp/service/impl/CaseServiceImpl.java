package com.zyp.service.impl;

import com.zyp.dto.CreateCaseDTO;
import com.zyp.dto.IdDTO;
import com.zyp.mapper.*;
import com.zyp.pojo.*;
import com.zyp.service.CaseService;
import com.zyp.utils.ThreadLocalUtil;
import com.zyp.vo.CaseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private  ChatMapper chatMapper;
    @Autowired
    private UserMapper userMapper;

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

//    public List<CaseVO> selectCaseById(){
//        Long userId = ThreadLocalUtil.get();
//        List<Case> cases = caseMapper.selectByClientId(userId);
//        List<CaseVO> ans = new ArrayList<>();
//
//        for (Case c : cases) {
//            CaseVO caseVO = new CaseVO();
//            BeanUtils.copyProperties(c , caseVO);
//            Doctor doctor = doctorMapper.selectById(c.getDoctorId());
//            doctor.setPassword(null);
//            doctor.setCard(null);
//            caseVO.setDoctor(doctor);
//        }
//
//    }

    /**
     * @ description 给问诊用户生成电子病历
     * @param caseDTO
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void createCase(CreateCaseDTO caseDTO){
        Chatlink chatlink = chatMapper.selectByLinkId(caseDTO.getLinkId());
        Case c = Case.builder()
                .clientId(chatlink.getClientId())
                .doctorId(ThreadLocalUtil.get())
                .chatLinkId(chatlink.getId())
                .visitDate(LocalDate.now())
                .symptoms(caseDTO.getSymptoms())
                .diagnosis(caseDTO.getDiagnosis())
                .treatment(caseDTO.getTreatment())
                .build();

        caseMapper.createCase(c);
        chatMapper.updateState(chatlink.getId());
    }

    @Override
    public CaseVO selectCaseByChatLinkId(Long chatLinkId) {
        Case c = caseMapper.selectCaseByChatLinkId(chatLinkId);
        CaseVO caseVO = new CaseVO();
        BeanUtils.copyProperties(c , caseVO);
        Doctor doctor = doctorMapper.selectById(c.getDoctorId());
        caseVO.setDoctor(doctor);
        Client client = clientMapper.selectById(c.getClientId());
        caseVO.setClient(client);
        return caseVO;
    }
}
