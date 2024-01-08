package com.buildbuddy.util.spesification;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.buildbuddy.domain.user.entity.UserEntity;
import com.buildbuddy.exception.BadRequestException;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class SpecificationCreator<T> {

    public Specification<T> getSpecification(List<ParamFilter> filters){
        log.info("creating query by: {}", filters);

        Specification<T> specification = Specification.where(createSpecification(filters.remove(0)));

        for (ParamFilter filter: filters){
            specification = specification.and(createSpecification(filter));
        }

        return specification;
    }

    public Specification<T> createSpecification(ParamFilter filter){
        return switch (filter.getOperator()) {
            case IN -> (root, query, cb) -> cb.in(root.get(filter.getField())).value(filter.getValues());
            case EQUAL -> (root, query, cb) -> cb.equal(root.get(filter.getField()), filter.getValue());
            case LIKE -> (root, query, cb) -> cb.like(root.get(filter.getField()), "%" + filter.getValue() + "%");
            case USERNAME -> (root, query, cb) -> {
                Join<T, UserEntity> join = root.join("user", JoinType.INNER);
                return cb.in(join.get(filter.getField())).value(filter.getValues());
            };
            case THREAD_ID -> (root, query, cb) -> {
                Join<T, ThreadEntity> join = root.join("thread", JoinType.INNER);
                return cb.in(join.get(filter.getField())).value(filter.getValues());
            };
            default -> throw new BadRequestException("Operation for: " + filter.getOperator() + " is not supported");
        };
    }

}
