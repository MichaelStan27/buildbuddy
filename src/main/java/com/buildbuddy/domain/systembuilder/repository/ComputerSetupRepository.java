package com.buildbuddy.domain.systembuilder.repository;

import com.buildbuddy.domain.systembuilder.entity.ComputerSetupEntity;
import com.buildbuddy.domain.systembuilder.entity.ComputerSetupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ComputerSetupRepository extends JpaRepository<ComputerSetupEntity, Integer>, JpaSpecificationExecutor<ComputerSetupEntity> {
    Optional<ComputerSetupEntity> findById(Integer integer);


    @Query(nativeQuery = true, value = "select cs.computer_setup_id as computerSetupId, " +
            "cc.computer_case_id as caseId, cc.name as caseName, cc.image as image, " +
            "c.cooler_id as coolerId, c.name as coolerName, c.image as image, " +
            "gc.graphics_card_id as graphicsCardId, gc.name as graphicsCardName, gc.image as graphicsCardImage, " +
            "m.monitor_id as monitorId, m.name as monitorName, m.image as monitorImage, " +
            "mo.motherboard_id as motherboardId, mo.name as motherboardName, mo.image as motherboardImage, " +
            "ps.power_supply_id as powerSupplyId, ps.name as powersupplyName, ps.image as powersupplyImage, " +
            "p.processor_id as processorId, p.name as processorName, p.image as processorImage, " +
            "r.ram_id as ramId, r.name as ramName, r.image as ramImage, " +
            "s.storage_id as storageId, s.name as storageName, s.image as storageImage," +
            "u.username as username, cs.created_time as createdTime, cs.last_update_time as lastUpdateTime " +
            "from computer_setup cs " +
            "join user u on u.user_id = cs.user_id " +
            "join computer_case cc on cc.computer_case_id = cs.computer_case_id " +
            "join cooler c on c.cooler_id = cs.cooler_id " +
            "join graphics_card gc on gc.graphics_card_id = cs.graphics_card_id " +
            "join monitor m on m.monitor_id = cs.monitor_id " +
            "join motherboard mo on mo.motherboard_id = cs.motherboard_id " +
            "join power_supply ps on ps.power_supply_id = cs.power_supply_id " +
            "join processor p on p.processor_id = cs.processor_id " +
            "join ram r on r.ram_id = cs.ram_id " +
            "join storage s on s.storage_id = cs.storage_id " +
            "where cs.computer_setup_id = (case when :computerSetupId is null then cs.computer_setup_id else :computerSetupId end) " +
            "and u.user_id = (case when :userId is null then u.user_id else :userId end)")
    List<ComputerSetupModel> getByCustomParam(@Param("computerSetupId") Integer computerSetupId, @Param("userId") Integer userId);

}
