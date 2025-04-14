package com.zyp.service;

import com.zyp.pojo.Case;
import com.zyp.vo.CaseVO;

import java.util.List;

public interface CaseService {
    List<CaseVO> selectCaseById();

}
