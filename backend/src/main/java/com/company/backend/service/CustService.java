package com.company.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.backend.dto.CustSearchDto;
import com.company.backend.entity.Cust;
import com.company.backend.mapper.CustMapper;

@Service
public class CustService {
    private final CustMapper custMapper;

    public CustService(CustMapper custMapper) {
        this.custMapper = custMapper;
    }

    public List<Cust> searchCust(CustSearchDto dto) {

        int offset = (dto.getPage() - 1) * dto.getPageSize();

        dto.setOffset(offset);

        return custMapper.searchCust(dto);

    }

    public int countCust(CustSearchDto dto) {
        return custMapper.countCust(dto);
    }

}
