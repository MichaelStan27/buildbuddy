package com.buildbuddy.domain.consult.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.consult.dto.param.ConsultTransactionReqParam;
import com.buildbuddy.domain.consult.dto.param.ConsultantReqParam;
import com.buildbuddy.domain.consult.dto.param.RoomChatReqParam;
import com.buildbuddy.domain.consult.dto.request.ConsultantReqDto;
import com.buildbuddy.domain.consult.dto.request.TransactionReqDto;
import com.buildbuddy.domain.consult.dto.response.*;
import com.buildbuddy.domain.consult.entity.*;
import com.buildbuddy.domain.consult.repository.ConsultTransactionRepository;
import com.buildbuddy.domain.consult.repository.ConsultantDetailRepository;
import com.buildbuddy.domain.consult.repository.RoomMasterRepository;
import com.buildbuddy.domain.user.entity.BalanceTransaction;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.domain.user.repository.BalanceTransactionRepository;
import com.buildbuddy.domain.user.repository.UserRepository;
import com.buildbuddy.enums.balance.BalanceTransactionStatus;
import com.buildbuddy.enums.balance.BalanceTransactionType;
import com.buildbuddy.enums.consult.ConsultTransactionStatus;
import com.buildbuddy.enums.consult.ConsultTransactionWorkflow;
import com.buildbuddy.enums.UserRole;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.PaginationCreator;
import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.SpecificationCreator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ConsultService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConsultantDetailRepository consultantDetailRepository;

    @Autowired
    private ConsultTransactionRepository consultTransactionRepository;

    @Autowired
    private RoomMasterRepository roomMasterRepository;

    @Autowired
    private BalanceTransactionRepository balanceTransactionRepository;

    @Autowired
    private PaginationCreator paginationCreator;

    @Autowired
    private SpecificationCreator<RoomMaster> roomMasterSpecCreator;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyHHmmss");

    @Transactional
    public DataResponse<ConsultantDetailDto> save(ConsultantReqDto consultantReqDto){

        BigDecimal fee = consultantReqDto.getFee();
        String desc = consultantReqDto.getDescription();

        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        if(!currentUser.getRole().equals(UserRole.CONSULTANT.getValue()))
            throw new RuntimeException("authenticated user is not a consultant");

        ConsultantDetail userDetail = currentUser.getConsultantDetail();

        if(userDetail == null){
            ConsultantDetail detail = ConsultantDetail.builder()
                    .user(currentUser)
                    .fee(fee)
                    .description(desc)
                    .available(0)
                    .build();

            currentUser.setConsultantDetail(detail);
        }
        else{
            userDetail.setFee(fee);
            userDetail.setDescription(desc);
        }

        currentUser = userRepository.saveAndFlush(currentUser);

        ConsultantDetail savedDetail = currentUser.getConsultantDetail();

        ConsultantDetailDto response = ConsultantDetailDto.builder()
                .username(currentUser.getUsername())
                .email(currentUser.getEmail())
                .age(currentUser.getAge())
                .gender(currentUser.getGender())
                .description(savedDetail.getDescription())
                .fee(savedDetail.getFee())
                .isAvailable(savedDetail.getAvailable() != 0)
                .build();

        return DataResponse.<ConsultantDetailDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving consultant detail")
                .data(response)
                .build();
    }

    public DataResponse<ConsultantSchema> get(ConsultantReqParam param){
        log.info("get consultant by param: {}", param);

        boolean isPaginated = param.isPagination();
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        String sortBy = param.getSortBy();
        String sortDirection = param.getSortDirection();

        Sort sort = paginationCreator.createAliasesSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        String username = createLikeKeyword(param.getUsername());
        String gender = param.getGender() != null ? param.getGender().toUpperCase() : null;
        String desc = createLikeKeyword(param.getDescription());
        Integer avail = param.getAvailable();

        Page<ConsultantModel> consultantModelPage = consultantDetailRepository.getConsultantByCustomParam(username, gender, desc, avail, pageable);

        List<ConsultantDetailDto> dtoList = consultantModelPage.getContent().stream()
                .map(ConsultantDetailDto::convertToDto)
                .toList();

        ConsultantSchema data = ConsultantSchema.builder()
                .consultantList(dtoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(consultantModelPage.getTotalPages())
                .totalData(consultantModelPage.getTotalElements())
                .build();

        return DataResponse.<ConsultantSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting consultant list")
                .data(data)
                .build();
    }

    public DataResponse<Object> getTransaction(ConsultTransactionReqParam param){

        log.info("Getting transaction by: {}", param);

        boolean isPaginated = param.isPagination();
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        String sortBy = param.getSortBy();
        String sortDirection = param.getSortDirection();

        Sort sort = paginationCreator.createAliasesSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<ConsultTransactionModel> consultTransactionModels = consultTransactionRepository.getByCustomParam(param.getUserId(), pageable);

        List<ConsultTransactionDto> consultTransactionDtos = consultTransactionModels.getContent().stream()
                .map(ConsultTransactionDto::convertToDto)
                .toList();

        ConsultTransactionSchema data = ConsultTransactionSchema.builder()
                .consultTransactionList(consultTransactionDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalData(consultTransactionModels.getTotalElements())
                .totalPages(consultTransactionModels.getTotalPages())
                .build();

        return DataResponse.builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("Success Gettomg Consult Transaction")
                .data(data)
                .build();
    }

    private String createLikeKeyword(String word){
        return word != null ? "%" + word.toUpperCase() + "%" : null;
    }

    public DataResponse<Object> getRoomChat(RoomChatReqParam param){
        log.info("Getting room chat list by: {}", param);

        boolean isPaginated = param.isPagination();
        Integer pageNo = param.getPageNo();
        Integer pageSize = param.getPageSize();
        String sortBy = param.getSortBy();
        String sortDirection = param.getSortDirection();

        Sort sort = paginationCreator.createSort(sortDirection, sortBy);

        Pageable pageable = paginationCreator.createPageable(isPaginated, sort, pageNo, pageSize);

        Page<RoomMaster> roomMasterPage = getRoomMasterFromDB(param, pageable);

        List<RoomMasterDto> dtoList = roomMasterPage.getContent().stream()
                .map(RoomMasterDto::convertToDto)
                .toList();

        RoomMasterSchema data = RoomMasterSchema.builder()
                .roomChatList(dtoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(roomMasterPage.getTotalPages())
                .totalData(roomMasterPage.getTotalElements())
                .build();

        return DataResponse.builder()
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message("success getting room chat list")
                .data(data)
                .build();
    }

    private Page<RoomMaster> getRoomMasterFromDB(RoomChatReqParam param, Pageable pageable){
        log.info("Getting room master from DB...");

        List<ParamFilter> paramFilters = param.getFilter();
        Page<RoomMaster> data = null;

        if(paramFilters.isEmpty())
            data = roomMasterRepository.findAll(pageable);
        else
            data = roomMasterRepository.findAll(roomMasterSpecCreator.getSpecification(paramFilters), pageable);

        return data;
    }

    @Transactional
    public DataResponse<Object> transaction(TransactionReqDto dto){
        log.info("Start saving transaction detail");

        ConsultTransaction consultTransaction = null;
        ConsultTransactionStatus status = null;
        UserEntity user = null;
        UserEntity consultant = null;

        if(dto.getTransactionId() == null){
            user = userRepository.findUserById(dto.getUserId()).orElseThrow(() -> new RuntimeException("user not found"));

            consultant = userRepository.findByConsultantId(dto.getConsultantId()).orElseThrow(() -> new RuntimeException("consultant not found"));

            status = ConsultTransactionStatus.PENDING;

            consultTransaction = ConsultTransaction.builder()
                    .user(user)
                    .consultant(consultant)
                    .status(ConsultTransactionStatus.PENDING.getValue())
                    .build();
        }
        else{
            status = ConsultTransactionStatus.stream()
                    .filter(s -> s.getValue().equals(dto.getStatus()))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Status not found"));

            consultTransaction = consultTransactionRepository.findByTransactionIdAndUserIdAndConsultantId(dto.getTransactionId(), dto.getUserId(), dto.getConsultantId())
                    .orElseThrow(() -> new RuntimeException("consult transaction not found"));

            ConsultTransactionStatus oldStatus = ConsultTransactionStatus.getByValue(consultTransaction.getStatus());
            ConsultTransactionWorkflow wf = ConsultTransactionWorkflow.getByTransactionStatus(oldStatus);
            List<ConsultTransactionStatus> allowedNextStatus = wf.getNextStatus();

            if(!allowedNextStatus.contains(status))
                throw new RuntimeException("Cant change transaction status from " + oldStatus + " to " + status);

            consultTransaction.setStatus(status.getValue());

            user = consultTransaction.getUser();
            consultant = consultTransaction.getConsultant();
        }

        String roomId = null;
        List<BalanceTransaction> balanceTransactionList = new ArrayList<>();

        switch (status){
            case PENDING -> {
                BigDecimal userBalance = user.getBalance();
                BigDecimal consultantFee = consultant.getConsultantDetail().getFee();
                if(userBalance.compareTo(consultantFee) < 0) throw new RuntimeException("Insufficient user balance");
                user.setBalance(userBalance.subtract(consultantFee));
                log.info("User Balance Deducted by: {}", consultantFee);

                BalanceTransaction userTrans = createBalanceTrans(user, consultTransaction, consultantFee, BalanceTransactionStatus.ON_HOLD, BalanceTransactionType.CONSULT);
                balanceTransactionList.add(userTrans);
            }
            case ON_PROGRESS -> {
                String time = LocalDateTime.now().format(formatter);
                roomId = user.getId() + consultant.getId() + time;

                RoomMaster roomMaster = RoomMaster.builder()
                        .roomId(roomId)
                        .user(user)
                        .consultant(consultant)
                        .build();

                roomMasterRepository.saveAndFlush(roomMaster);
                consultTransaction.setRoomMaster(roomMaster);
            }
            case REJECTED -> {
                BigDecimal userBalance = user.getBalance();
                BigDecimal consultantFee = consultant.getConsultantDetail().getFee();
                user.setBalance(userBalance.add(consultantFee));
                log.info("User Balance added by: {}", consultantFee);

                BalanceTransaction userTrans = user.getBalanceByConsultTransactionId(consultTransaction);
                userTrans.setStatus(BalanceTransactionStatus.RETURNED.getValue());
                balanceTransactionList.add(userTrans);
            }
            case COMPLETED -> {
                BigDecimal consultantBalance = consultant.getBalance();
                BigDecimal consultantFee = consultant.getConsultantDetail().getFee();
                consultant.setBalance(consultantBalance.add(consultantFee));
                log.info("Consultant Balance added by: {}", consultantFee);

                BalanceTransaction userTrans = user.getBalanceByConsultTransactionId(consultTransaction);
                userTrans.setStatus(BalanceTransactionStatus.DEDUCTED.getValue());
                BalanceTransaction consultTrans = createBalanceTrans(consultant, consultTransaction, consultantFee, BalanceTransactionStatus.ADDED, BalanceTransactionType.CONSULT);
                balanceTransactionList.addAll(List.of(userTrans, consultTrans));
            }
        }

        consultTransaction = consultTransactionRepository.saveAndFlush(consultTransaction);
        userRepository.saveAllAndFlush(List.of(user, consultant));
        balanceTransactionRepository.saveAllAndFlush(balanceTransactionList);

        TransactionDto data = TransactionDto.builder()
                .transactionId(consultTransaction.getTransactionId())
                .status(consultTransaction.getStatus())
                .roomId(roomId)
                .build();

        return DataResponse.<Object>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving transaction detail")
                .data(data)
                .build();
    }

    private BalanceTransaction createBalanceTrans(UserEntity user, ConsultTransaction consultTransaction, BigDecimal nominal, BalanceTransactionStatus status, BalanceTransactionType type){
        return BalanceTransaction.builder()
                .user(user)
                .consultTransaction(consultTransaction)
                .nominal(nominal)
                .status(status.getValue())
                .transactionType(type.getValue())
                .build();
    }
}
