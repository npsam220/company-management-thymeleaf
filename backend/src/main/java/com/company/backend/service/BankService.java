package com.company.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.backend.entity.Bank;
import com.company.backend.mapper.BankMapper;

@Service
public class BankService {

    private final BankMapper bankMapper;

    public BankService(BankMapper bankMapper) {
        this.bankMapper = bankMapper;
    }

    public List<Bank> findAll() {
        return bankMapper.findAll();
    }

    public Bank findByCode(String bankCd, String bankChrcd) {
        return bankMapper.findByCode(bankCd, bankChrcd);
    }
}
