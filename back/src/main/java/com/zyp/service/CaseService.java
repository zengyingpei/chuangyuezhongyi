package com.zyp.service;

import com.zyp.dto.CreateCaseDTO;
import com.zyp.dto.IdDTO;
import com.zyp.pojo.Case;
import com.zyp.vo.CaseVO;

import java.util.List;

public interface CaseService {
    List<CaseVO> selectCaseById();

    void createCase(CreateCaseDTO caseDTO);

}
