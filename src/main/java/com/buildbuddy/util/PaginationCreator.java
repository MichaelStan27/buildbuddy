package com.buildbuddy.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

@Service
public class PaginationCreator {

    public Sort createSort(String sortDirection, String sortBy){
        return Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
    }

    public Sort createAliasesSort(String sortDirection, String sortBy){
        return JpaSort.unsafe(Sort.Direction.fromString(sortDirection), "(" + sortBy + ")");
    }

    public Pageable createPageable(Boolean isPaginated, Sort sort, Integer pageNo, Integer pageSize){
        return (isPaginated) ? PageRequest.of(pageNo, pageSize, sort) : PageRequest.of(0, Integer.MAX_VALUE, sort);
    }

}
