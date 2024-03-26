package com.buildbuddy.domain.user.entity;

import com.buildbuddy.domain.consult.entity.ConsultTransaction;
import com.buildbuddy.domain.consult.entity.ConsultantDetail;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.user.dto.request.UserRequestDto;
import com.buildbuddy.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<ThreadEntity> threads;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private ConsultantDetail consultantDetail;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<BalanceTransaction> balanceTransactionList;

    public BalanceTransaction getBalanceByConsultTransactionId(ConsultTransaction consultTransaction){
        return this.balanceTransactionList.stream()
                .filter(b -> {
                    if(b.getConsultTransaction() != null)
                        return b.getConsultTransaction().getTransactionId().equals(consultTransaction.getTransactionId());
                    return false;
                })
                .findAny()
                .orElseThrow(() -> new RuntimeException("Cant find balance transaction by consult transaction "));
    }

    public void updateDetail(UserRequestDto dto){
        String email = dto.getEmail();
        Integer age = dto.getAge();
        String gender = dto.getGender();

        this.setEmail(email != null ? email : this.getEmail());
        this.setAge(age != null ? age : this.getAge());
        this.setGender(gender != null ? gender : this.getGender());
        if(dto.getRole().equals(UserRole.CONSULTANT.getValue())){
            ConsultantDetail detailConsultant = this.getConsultantDetail();
            if(detailConsultant == null) throw new RuntimeException("Consultant Detail Not Found");
            String description = dto.getDescription();
            BigDecimal fee = dto.getFee();
            Integer available = dto.getAvailable();
            detailConsultant.setDescription(description != null ? description : detailConsultant.getDescription());
            detailConsultant.setFee(fee != null ? fee : detailConsultant.getFee());
            detailConsultant.setAvailable(available != null ? available : detailConsultant.getAvailable());
        }
    }
}
