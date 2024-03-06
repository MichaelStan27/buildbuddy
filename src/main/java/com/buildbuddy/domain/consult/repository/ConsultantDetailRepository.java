package com.buildbuddy.domain.consult.repository;

import com.buildbuddy.domain.consult.entity.ConsultantDetail;
import com.buildbuddy.domain.consult.entity.ConsultantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConsultantDetailRepository extends JpaRepository<ConsultantDetail, Integer> {

    @Query(nativeQuery = true, value = "SELECT u.user_id as consultantId, u.username as username, u.email as email, u.age as age, u.gender as gender, " +
            "detail.description as description, detail.fee as fee, detail.available as available " +
            "FROM consultant_detail detail JOIN user u ON u.user_id = detail.user_id " +
            "WHERE UPPER(u.username) LIKE (case when :username is null then u.username else :username end) " +
            "AND UPPER(u.gender) = (case when :gender is null then u.gender else :gender end) " +
            "AND UPPER(detail.description) LIKE (case when :description is null then detail.description else :description end) " +
            "AND detail.available = (case when :available is null then available else :available end)")
    Page<ConsultantModel> getConsultantByCustomParam(@Param("username") String username,
                                                     @Param("gender") String gender,
                                                     @Param("description") String description,
                                                     @Param("available") Integer available,
                                                     Pageable pageable);

}
