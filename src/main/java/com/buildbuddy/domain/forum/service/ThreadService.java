package com.buildbuddy.domain.forum.service;

import com.buildbuddy.audit.AuditorAwareImpl;
import com.buildbuddy.domain.forum.dto.param.ThreadRequestParam;
import com.buildbuddy.domain.forum.dto.request.ThreadRequestDto;
import com.buildbuddy.domain.forum.dto.response.ThreadResponseDto;
import com.buildbuddy.domain.forum.dto.response.ThreadResponseSchema;
import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.forum.repository.ThreadRepository;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import com.buildbuddy.jsonresponse.DataResponse;
import com.buildbuddy.util.spesification.ParamFilter;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ThreadService {

    @Autowired
    private AuditorAwareImpl audit;

    @Autowired
    private ThreadRepository threadRepository;

    public DataResponse<ThreadResponseSchema> get(ThreadRequestParam requestParam){

        boolean isPaginated = requestParam.isPagination();
        Integer pageNo = requestParam.getPageNo();
        Integer pageSize = requestParam.getPageSize();
        String sortBy = requestParam.getSortBy();
        String sortDirection = requestParam.getSortDirection();

        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        Pageable pageable = (isPaginated) ? PageRequest.of(pageNo, pageSize, sort) : PageRequest.of(0, Integer.MAX_VALUE, sort);

        Page<ThreadEntity> dataPage = getThreadFromDB(requestParam, pageable);
        List<ThreadEntity> threadList = dataPage.getContent();

        List<ThreadResponseDto> threadResponseDtos = threadList.stream()
                .map(ThreadResponseDto::convertToDto)
                .toList();

        ThreadResponseSchema data = ThreadResponseSchema.builder()
                .threadList(threadResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(dataPage.getTotalPages())
                .totalData(dataPage.getTotalElements())
                .build();

        return DataResponse.<ThreadResponseSchema>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success getting thread")
                .data(data)
                .build();
    }

    private Page<ThreadEntity> getThreadFromDB(ThreadRequestParam param, Pageable pageable){
        log.info("Getting thread from DB...");

        List<ParamFilter> paramFilters = param.getFilters();
        Page<ThreadEntity> data = null;

        if(paramFilters.isEmpty())
            data = threadRepository.findAll(pageable);
        else
            data = threadRepository.findAll(getSpecification(paramFilters), pageable);

        return data;
    }

    private Specification<ThreadEntity> getSpecification(List<ParamFilter> filters){
        log.info("creating query by: {}", filters);

        Specification<ThreadEntity> specification = Specification.where(createSpecification(filters.remove(0)));

        for (ParamFilter filter: filters){
            specification = specification.and(createSpecification(filter));
        }

        return specification;
    }

    private Specification<ThreadEntity> createSpecification(ParamFilter filter){
        return switch (filter.getOperator()) {
            case IN -> (root, query, cb) -> cb.in(root.get(filter.getField())).value(filter.getValues());
            case EQUAL -> (root, query, cb) -> cb.equal(root.get(filter.getField()), filter.getValue());
            case LIKE -> (root, query, cb) -> cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%");
            case THREAD -> (root, query, cb) -> {
                Join<ThreadEntity, UserEntity> join = root.join("user", JoinType.INNER);
                return cb.in(join.get(filter.getField())).value(filter.getValues());
            };
            default -> throw new BadRequestException("Operation for: " + filter.getOperator() + " is not supported");
        };
    }

    @Transactional
    public DataResponse<ThreadResponseDto> save(ThreadRequestDto threadDto){
        log.info("thread: {}", threadDto);

        Integer id = threadDto.getId();
        ThreadEntity thread = null;

        if(id != null){
            UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
            log.info("current authenticated user: {}", currentUser.getUsername());

            thread = threadRepository.findByIdAndUserId(id, currentUser.getId()).orElseThrow(() -> new BadRequestException("Post Not Found"));
            thread.setPost(threadDto.getPost());
        }
        else{
            thread = ThreadRequestDto.convertToEntity(threadDto);
        }

        log.info("saving...");
        threadRepository.saveAndFlush(thread);
        log.info("saved...");

        ThreadResponseDto data = ThreadResponseDto.builder()
                .post(thread.getPost())
                .username(thread.getUser().getUsername())
                .createdTime(thread.getCreatedTime())
                .lastUpdateTime(thread.getLastUpdateTime())
                .build();

        return DataResponse.<ThreadResponseDto>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success saving thread")
                .data(data)
                .build();
    }

    @Transactional
    public DataResponse<String> delete(Integer threadId){
        log.info("deleting thread with id: {}", threadId);
        UserEntity currentUser = audit.getCurrentAuditor().orElseThrow(() ->  new BadRequestException("Request not authenticated"));
        log.info("current authenticated user: {}", currentUser.getUsername());

        ThreadEntity thread = threadRepository.findByIdAndUserId(threadId, currentUser.getId()).orElseThrow(() -> new BadRequestException("Post Not Found"));

        log.info("deleting...");
        threadRepository.delete(thread);
        log.info("deleted.");

        return DataResponse.<String>builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .message("Success deleting thread")
                .data("Thread with id: " + threadId + " deleted.")
                .build();
    }

}
