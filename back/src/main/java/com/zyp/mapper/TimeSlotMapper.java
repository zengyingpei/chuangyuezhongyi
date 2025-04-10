package com.zyp.mapper;

import com.zyp.pojo.TimeSlot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TimeSlotMapper {
    @Select("select * from time_slot where doctor_id = #{docId} and date >= #{time}")
    List<TimeSlot> selectByDocId(Long docId, LocalDate time);

    @Select("select * from time_slot where id = #{slotId}")
    TimeSlot selectById(Long slotId);

    @Update("update time_slot set available = available -1 where id =#{slotId} and available >=1")
    void reduceAvailable(Long slotId);

    /**
     * @ description 将超时取消的预约的时间段的可用人数加1
     * @param doctorId
     * @param date
     * @param timeSlot
     * @ return void
     * @ author DELL
     */
    @Update("update time_slot set available=available+1 where doctor_id=#{doctorId} and date=#{date} and slot=#{timeSlot}")
    void addAvailable(Long doctorId, LocalDate date, String timeSlot);
}
