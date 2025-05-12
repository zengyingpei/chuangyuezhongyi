package com.zyp.mapper;

import com.zyp.pojo.Appointment;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    @Insert("insert into appointment (user_id, doctor_id, date, time_slot, status, create_time, update_time) " +
            "values (#{userId},#{doctorId},#{date},#{timeSlot},#{status},#{createTime},#{updateTime})")
    void addAppointment(Appointment appointment);

    /**
     * @param status
     * @param time
     * @ description 找到所有超时订单
     * @ return java.util.List<com.zyp.pojo.Appointment>
     * @ author DELL
     */
    @Select("select *  from appointment where status=#{status} and create_time < #{time}")
    List<Appointment> selectAllTimeOut(int status, LocalDateTime time);

    /**
     * @param id
     * @ description 根据id将状态设置为超时取消
     * @ return void
     * @ author DELL
     */
    @Update("update appointment set status=3,update_time=now() where id=#{id}")
    void updateStatusTimeOut(Long id);

    @Select("select * from appointment where user_id = #{userId} and status = 1 order by date , time_slot ")
    List<Appointment> selectAllNoFinishedByUserId(Long userId);

    @Select("select * from appointment where user_id = #{userId} and status = 2 order by date , time_slot")
    List<Appointment> selectAllFinishedByUserId(Long userId);

    @Select("select * from appointment where doctor_id = #{doctorId} and status = 1 and date >=now() order by date ")
    List<Appointment> selectAllByDoctorId(Long doctorId);

    @Select("select * from appointment where id = #{id}")
    Appointment selectById(long id);

    @Delete("delete from appointment where id = #{id}")
    void deleteOne(long id);

    @Update("update appointment set status = 2 , update_time = now() where id = #{id}")
    void finish(long id);

    @Update("update appointment set status = 4, reason = #{reason} where id = #{id}")
    void refuse(long id, String reason);
}
