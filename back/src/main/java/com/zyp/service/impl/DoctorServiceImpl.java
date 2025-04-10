package com.zyp.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zyp.dto.AdminDoctorPageQueryDto;
import com.zyp.dto.DoctorDto;
import com.zyp.dto.DoctorPageQueryDto;
import com.zyp.mapper.DocSickMapper;
import com.zyp.mapper.DoctorDetailMapper;
import com.zyp.mapper.DoctorMapper;
import com.zyp.mapper.SelectDoctorMapper;
import com.zyp.pojo.DocSick;
import com.zyp.pojo.Doctor;
import com.zyp.pojo.DoctorDetail;
import com.zyp.pojo.PageBean;
import com.zyp.service.DoctorService;
import com.zyp.utils.Md5Util;
import com.zyp.vo.DoctorDeatilVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private DocSickMapper docSickMapper;
    @Autowired
    private DoctorDetailMapper doctorDetailMapper;
    @Autowired
    private SelectDoctorMapper selectDoctorMapper;
    @Override
    public PageBean<Doctor> selectBySicknessId(DoctorPageQueryDto doctorPageQueryDto) {
        PageHelper.startPage(doctorPageQueryDto.getPage(), doctorPageQueryDto.getPageSize());   //开启分页查询
        Page<Doctor> page=doctorMapper.selectBySicknessId(doctorPageQueryDto);
        return new PageBean<Doctor>(page.getTotal(),page.getResult());
    }

    @Override
    public List<Doctor> selectSelectedDoctor() {
        List<Doctor> list=doctorMapper.selectSelectedDoctor();
        return list;
    }

    @Override
    @Transactional
    public DoctorDeatilVo selectDoctorDeatil(Long id) {
        Doctor doctor=doctorMapper.selectById(id);
        DoctorDetail doctorDetail=doctorMapper.selectDetailById(id);
        BigDecimal score=doctorMapper.countScore(id);
        List<String> department=doctorMapper.selectDeptOfDoctor(id);
        return DoctorDeatilVo.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .age(doctor.getAge())
                .phone(doctor.getPhone())
                .avatar(doctor.getAvatar())
                .workplace(doctor.getWorkplace())
                .level(doctor.getLevel())
                .desc(doctor.getDesc())
                .card(doctor.getCard())
                .specialization(doctorDetail!=null ? doctorDetail.getSpecialization():null)
                .score(score)
                .department(department)
                .consultCost(doctorDetail!=null?doctorDetail.getConsultCost():null)
                .bookingCost(doctorDetail!=null?doctorDetail.getBookingCost():null)
                .build();
    }

    /**
     * @ description 医生信息分页查询
     * @param adminDoctorPageQueryDto
     * @ return com.zyp.pojo.PageBean<com.zyp.pojo.Doctor>
     * @ author DELL
     */
    @Override
    public PageBean<Doctor> pageQuery(AdminDoctorPageQueryDto adminDoctorPageQueryDto) {
        PageHelper.startPage(adminDoctorPageQueryDto.getPage(), adminDoctorPageQueryDto.getPageSize());
        Page<Doctor> page=doctorMapper.pageQuery(adminDoctorPageQueryDto);
        return new PageBean<>(page.getTotal(), page.getResult());
    }

    /**
     * @ description 新增医生
     * @param doctorDto
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void insert(DoctorDto doctorDto) {
        // 1、doctor表中插入数据，主键要回传
        Doctor doctor= new Doctor();
        BeanUtils.copyProperties(doctorDto, doctor);
        // 获取手机号码
        String phone = doctor.getPhone();
        // 将新增的医生的登陆密码设置成默认是 手机号
        String password = Md5Util.getMD5String(phone);
        doctor.setPassword(password);

        log.info("doctor={}",doctor);
        doctorMapper.insert(doctor);

        // 2、doc-sick表中也要插入数据
        List<Long> sicknessList = doctorDto.getSicknessId();
        for (Long sicknessId : sicknessList) {
            DocSick docSick=DocSick.builder()
                    .docId(doctor.getId())
                    .sicknessId(sicknessId)
                    .build();
            docSickMapper.insert(docSick);
        }
    }

    /**
     * @ description 根据id删除
     * @param id
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void deleteById(Long id) {
        // 删除医生时，要把所有与这个医生相关的信息，删掉
        doctorMapper.deleteById(id);
        //删除医生详细信息表
        doctorDetailMapper.deleteByDocId(id);
        //精选医生删除
        selectDoctorMapper.deleteByDocId(id);
        //医生-疾病中间表也要删除
        docSickMapper.deleteByDocId(id);
    }

    /**
     * @ description 修改医生的基本信息
     * @param doctorDto
     * @ return void
     * @ author DELL
     */
    @Transactional
    public void update(DoctorDto doctorDto) {
        // 1、先修改基本信息
        Doctor doctor=new Doctor();
        BeanUtils.copyProperties(doctorDto,doctor);
        doctorMapper.update(doctor);

        // 2、再看该医生所选疾病是否修改 (先把该医生所选疾病全部删除，再重新添加)
        docSickMapper.deleteByDocId(doctor.getId());
        List<Long> sicknessIdList = doctorDto.getSicknessId();
        for (Long sicknessId : sicknessIdList) {
            DocSick docSick = DocSick.builder()
                    .docId(doctorDto.getId())
                    .sicknessId(sicknessId)
                    .build();
            docSickMapper.insert(docSick);
        }
    }
}
