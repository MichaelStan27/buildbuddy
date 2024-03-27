package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.RoomMaster;
import com.buildbuddy.domain.consult.entity.RoomMasterModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoomMasterRepository extends JpaRepository<RoomMaster, String>, JpaSpecificationExecutor<RoomMaster> {

    @Query(nativeQuery = true, value = "select rm.room_id as roomId, u1.username as username, u1.profile_picture as userProfile, " +
            "u2.username as consultantName, u2.profile_picture as consultantProfile, rm.created_time as createdTime " +
            "from room_master rm join user u1 on rm.user_id = u1.user_id " +
            "join user u2 on rm.consultant_id = u2.user_id " +
            "join consult_transaction ct on ct.room_id = rm.room_id " +
            "where rm.user_id = (case when :userId is null then rm.user_id else :userId end) " +
            "and rm.consultant_id = (case when :consultantId is null then rm.consultant_id else :consultantId end) " +
            "and ct.status = (case when :isActive is true then 'ON_PROGRESS' else 'COMPLETED' end) " +
            "and (u1.username like (case when :search is null then u1.username else :search end) " +
            "or u2.username like (case when :search is null then u2.username else :search end))")
    Page<RoomMasterModel> getByCustomParam(@Param("userId") Integer userId,
                                           @Param("consultantId") Integer consultantId,
                                           @Param("isActive") Boolean isActive,
                                           @Param("search") String search,
                                           Pageable pageable);

}
