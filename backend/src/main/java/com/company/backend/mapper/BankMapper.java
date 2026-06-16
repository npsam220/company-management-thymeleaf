package com.company.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.company.backend.entity.Bank;

@Mapper
public interface BankMapper {

    List<Bank> findAll();

    Bank findByCode(@Param("bankCd") String bankCd, @Param("bankChrcd") String bankChrcd);
}
