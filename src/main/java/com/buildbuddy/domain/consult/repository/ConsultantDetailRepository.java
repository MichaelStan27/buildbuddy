package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.ConsultantDetail;
import com.buildbuddy.domain.consult.entity.ConsultantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsultantDetailRepository extends JpaRepository<ConsultantDetail, Integer> {

    @Query(nativeQuery = true, value = "SELECT u.user_id as consultantId, u.username as username, u.profile_picture as profile, u.email as email, u.age as age, u.gender as gender, " +
            "detail.description as description, detail.fee as fee, detail.available as available " +
            "FROM consultant_detail detail JOIN user u ON u.user_id = detail.user_id " +
            "where detail.fee like (case when :search is null then detail.fee else :search end) " +
            "or detail.description like (case when :search is null then detail.description else :search end) " +
            "or u.username like (case when :search is null then u.username else :search end)")
    Page<ConsultantModel> getConsultantByCustomParam(@Param("search") String username,
                                                     Pageable pageable);

}
