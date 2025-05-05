package com.zyp.mapper;

import com.zyp.pojo.Schedule;
import com.zyp.service.ScheduleService;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface ScheduleMapper {

    @Select("select * from schedule where doctor_id = #{docId} and date >= #{now} order by date, AP")
    List<Schedule> selectByDocId(Long docId, LocalDate now);

    @Select("select * from schedule where doctor_id = #{doctorId} and date=#{date} and AP = #{AP}")
    Schedule selectByDateAndDocIdAndAP(Schedule schedule);


    @Insert("insert into schedule(doctor_id, date, AP, surplus, create_time) value (#{doctorId}, #{date}, #{AP}, #{surplus}, #{createTime})")
    void insert(Schedule schedule);

    @Update("update schedule set surplus =#{surplus} where doctor_id=#{doctorId} and date=#{date} and AP = #{AP}")
    void update(Schedule schedule);

    @Delete("delete from schedule where doctor_id = #{doctorId} and date=#{date} and AP = #{AP} ")
    void remove(Schedule schedule);

    @Select("select * from schedule where id = #{scheduleId}")
    Schedule selectById(long scheduleId);

    @Update("update schedule set surplus = #{surplus} where id = #{id}")
    void reduceRurplus(int surplus, long id);
}
