package com.zyp.mapper;

import com.zyp.pojo.Appointment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    @Insert("insert into appointment (user_id, doctor_id, date, time_slot, status, create_time, update_time) " +
            "values (#{userId},#{doctorId},#{date},#{timeSlot},#{status},#{createTime},#{updateTime})")
    void addAppointment(Appointment appointment);

    /**
     * @ description 找到所有超时订单
     * @param status
     * @param time
     * @ return java.util.List<com.zyp.pojo.Appointment>
     * @ author DELL
     */
    @Select("select *  from appointment where status=#{status} and create_time < #{time}")
    List<Appointment> selectAllTimeOut(int status, LocalDateTime time);

    /**
     * @ description 根据id将状态设置为超时取消
     * @param id
     * @ return void
     * @ author DELL
     */
    @Update("update appointment set status=4,update_time=now() where id=#{id}")
    void updateStatusTimeOut(Long id);
}
