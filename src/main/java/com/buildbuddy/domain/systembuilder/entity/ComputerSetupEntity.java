package com.buildbuddy.domain.systembuilder.entity;

import com.buildbuddy.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "computer_setup")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ComputerSetupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "computer_setup_id")
    private Integer id;

    @Column(name = "computer_case_id")
    private Integer caseId;

    @Column(name = "cooler_id")
    private Integer coolerId;

    @Column(name = "graphics_card_id")
    private Integer graphicsCardId;

    @Column(name = "monitor_id")
    private Integer monitorId;

    @Column(name = "motherboard_id")
    private Integer motherboardId;

    @Column(name = "power_supply_id")
    private Integer powersupplyId;

    @Column(name = "processor_id")
    private Integer processorId;

    @Column(name = "ram_id")
    private Integer ramId;

    @Column(name = "storage_id")
    private Integer storageId;

    @CreatedBy
    @OneToOne
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "computer_case_id", updatable = false, insertable = false)
    private CaseEntity computerCase;

    @OneToOne
    @JoinColumn(name = "cooler_id", updatable = false,insertable = false)
    private CoolerEntity cooler;

    @OneToOne
    @JoinColumn(name = "graphics_card_id", updatable = false,insertable = false)
    private GraphicsCardEntity graphicsCard;

    @OneToOne
    @JoinColumn(name = "monitor_id", updatable = false,insertable = false)
    private MonitorEntity monitor;

    @OneToOne
    @JoinColumn(name = "motherboard_id", updatable = false,insertable = false)
    private MotherboardEntity motherboard;

    @OneToOne
    @JoinColumn(name = "power_supply_id", updatable = false,insertable = false)
    private PowerSupplyEntity powerSupply;

    @OneToOne
    @JoinColumn(name = "processor_id", updatable = false,insertable = false)
    private ProcessorEntity processor;

    @OneToOne
    @JoinColumn(name = "ram_id", updatable = false,insertable = false)
    private RamEntity ram;

    @OneToOne
    @JoinColumn(name = "storage_id", updatable = false,insertable = false)
    private StorageEntity storage;

    @CreatedDate
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @LastModifiedDate
    @Column(name = "last_update_time")
    private LocalDateTime lastUpdateTime;

}
