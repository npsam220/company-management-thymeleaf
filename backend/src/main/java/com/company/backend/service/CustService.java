package com.company.backend.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.backend.dto.CustSearchDto;
import com.company.backend.entity.Bank;
import com.company.backend.entity.Cust;
import com.company.backend.entity.Manager;
import com.company.backend.mapper.BankMapper;
import com.company.backend.mapper.CustMapper;

@Service
public class CustService {
    private final CustMapper custMapper;
    private final BankMapper bankMapper;

    public CustService(CustMapper custMapper, BankMapper bankMapper) {
        this.custMapper = custMapper;
        this.bankMapper = bankMapper;
    }

    public List<Cust> searchCust(CustSearchDto dto) {

        int offset = (dto.getPage() - 1) * dto.getPageSize();

        dto.setOffset(offset);

        return custMapper.searchCust(dto);

    }

    public int countCust(CustSearchDto dto) {
        return custMapper.countCust(dto);
    }

    public Cust getCustDetail(Long custId) {
        Cust cust = custMapper.getCustDetail(custId);
        if (cust != null) {
            cust.setManagers(custMapper.getManagersByCustId(custId));
        }
        return cust;
    }

    @Transactional
    public void update(Cust cust) {
        applyBankMaster(cust);
        // 1. 更新取引先
        // 更新 CUST_TBL 的基本資料。
        int updatedCustCount = custMapper.update(cust);
        if (updatedCustCount != 1) {
            throw new IllegalArgumentException("更新対象の取引先が存在しません。");
        }
        // 2. 取得更新前後の担当者
        // 從資料庫取得目前存在的担当者。
        List<Manager> currentManagers = custMapper.getManagersByCustId(cust.getCustId());
        Set<String> currentKeys = new HashSet<>();
        for (Manager current : currentManagers) {
            currentKeys.add(managerKey(current));
        }
        // 取得畫面送來的担当者。
        // 如果畫面沒有任何担当者，改用空的 List，避免發生 NullPointerException。
        List<Manager> submittedManagers = cust.getManagers() == null
                ? Collections.emptyList()
                : cust.getManagers();
        // 3. 準備記錄保留下來的担当者
        // 這個 Set 用來記錄畫面中仍然存在的舊担当者編號，例如：
        // M0001:01 M0002:01
        Set<String> submittedKeys = new HashSet<>();
        // 4. 判斷新增或更新
        // 逐一處理畫面送來的担当者。
        for (Manager manager : submittedManagers) {
            manager.setManagerCustId(cust.getCustId());
            normalizeManager(manager);
            if (manager.getManagerCd() == null || manager.getManagerCd().isBlank()) {
                manager.setManagerCd("M" + UUID.randomUUID().toString().replace("-", ""));
                manager.setManagerChrcd("01");
                int insertedManagerCount = custMapper.insertManager(manager);
                if (insertedManagerCount != 1) {
                    throw new IllegalArgumentException("担当者を登録できませんでした。");
                }
            } else {
                if (manager.getManagerChrcd() == null || manager.getManagerChrcd().isBlank()) {
                    throw new IllegalArgumentException("担当者コードが不正です。");
                }
                String submittedKey = managerKey(manager);
                if (!currentKeys.contains(submittedKey)) {
                    throw new IllegalArgumentException("担当者情報が最新ではありません。再表示してから更新してください。");
                }
                if (!submittedKeys.add(submittedKey)) {
                    throw new IllegalArgumentException("担当者情報が重複しています。");
                }
                int updatedManagerCount = custMapper.updateManager(manager);
                if (updatedManagerCount != 1) {
                    throw new IllegalArgumentException("担当者を更新できませんでした。");
                }
            }
        }

        for (Manager current : currentManagers) {
            if (!submittedKeys.contains(managerKey(current))) {
                current.setManagerCustId(cust.getCustId());
                int deletedManagerCount = custMapper.deleteManager(current);
                if (deletedManagerCount != 1) {
                    throw new IllegalArgumentException("担当者を削除できませんでした。");
                }
            }
        }
    }

    private void applyBankMaster(Cust cust) {
        if (!"01".equals(cust.getCustType())) {
            return;
        }
        if (cust.getBankCd() == null || cust.getBankCd().isBlank()
                || cust.getBankChrcd() == null || cust.getBankChrcd().isBlank()) {
            throw new IllegalArgumentException("銀行マスタから銀行情報を選択してください。");
        }
        Bank bank = bankMapper.findByCode(cust.getBankCd(), cust.getBankChrcd());
        if (bank == null) {
            throw new IllegalArgumentException("銀行マスタに存在しない銀行情報です。");
        }
        cust.setCustBankNm(bank.getBankNm());
        cust.setCustBranchNm(bank.getBankBranchnm());
        cust.setCustBranchcd(bank.getBankBranchcd());
        cust.setCustAccountno(bank.getBankAccountno());
        cust.setCustNominee(bank.getBankNominee());
        cust.setCustDeposittype(bank.getBankDeposittype());
    }

    private void normalizeManager(Manager manager) {
        manager.setManagerPno(blankToEmpty(manager.getManagerPno()));
        manager.setManagerMail(blankToEmpty(manager.getManagerMail()));
        manager.setManagerJob(blankToEmpty(manager.getManagerJob()));
        manager.setManagerRmk(blankToEmpty(manager.getManagerRmk()));
    }

    private String blankToEmpty(String value) {
        return value == null ? "" : value;
    }

    private String managerKey(Manager manager) {
        return manager.getManagerCd() + ":" + manager.getManagerChrcd();
    }

}
