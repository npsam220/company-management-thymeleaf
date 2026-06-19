package com.company.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.company.backend.entity.Bank;
import com.company.backend.entity.Cust;
import com.company.backend.entity.Manager;
import com.company.backend.mapper.BankMapper;
import com.company.backend.mapper.CustMapper;

@ExtendWith(MockitoExtension.class)
class CustServiceTests {

    @Mock
    private CustMapper custMapper;

    @Mock
    private BankMapper bankMapper;

    private CustService custService;

    @BeforeEach
    void setUp() {
        custService = new CustService(custMapper, bankMapper);
    }

    @Test
    void updateThrowsWhenCustWasNotUpdated() {
        Cust cust = new Cust();
        cust.setCustId(1L);
        when(custMapper.update(cust)).thenReturn(0);

        assertThatThrownBy(() -> custService.update(cust))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("更新対象の取引先");
    }

    @Test
    void updateAppliesBankMasterForCustomerType() {
        Cust cust = new Cust();
        cust.setCustId(1L);
        cust.setCustType("01");
        cust.setBankCd("0005");
        cust.setBankChrcd("002");
        cust.setCustBankNm("不正銀行");
        cust.setManagers(Collections.emptyList());

        Bank bank = new Bank();
        bank.setBankNm("三菱UFJ銀行");
        bank.setBankBranchnm("新宿支店");
        bank.setBankBranchcd("341");
        bank.setBankAccountno("3456789");
        bank.setBankNominee("カブシキガイシャサンプル");
        bank.setBankDeposittype("01");

        when(bankMapper.findByCode("0005", "002")).thenReturn(bank);
        when(custMapper.update(any(Cust.class))).thenReturn(1);
        when(custMapper.getManagersByCustId(1L)).thenReturn(Collections.emptyList());

        custService.update(cust);

        ArgumentCaptor<Cust> captor = ArgumentCaptor.forClass(Cust.class);
        verify(custMapper).update(captor.capture());
        Cust updatedCust = captor.getValue();
        assertThat(updatedCust.getCustBankNm()).isEqualTo("三菱UFJ銀行");
        assertThat(updatedCust.getCustBranchNm()).isEqualTo("新宿支店");
        assertThat(updatedCust.getCustBranchCd()).isEqualTo("341");
        assertThat(updatedCust.getCustAccountno()).isEqualTo("3456789");
        assertThat(updatedCust.getCustNominee()).isEqualTo("カブシキガイシャサンプル");
        assertThat(updatedCust.getCustDeposittype()).isEqualTo("01");
    }

    @Test
    void updateThrowsWhenSubmittedManagerKeyIsNotCurrent() {
        Cust cust = new Cust();
        cust.setCustId(1L);

        Manager currentManager = new Manager();
        currentManager.setManagerCd("M0001");
        currentManager.setManagerChrcd("01");

        Manager submittedManager = new Manager();
        submittedManager.setManagerCd("M9999");
        submittedManager.setManagerChrcd("01");
        submittedManager.setManagerNm("佐藤");
        cust.setManagers(List.of(submittedManager));

        when(custMapper.update(cust)).thenReturn(1);
        when(custMapper.getManagersByCustId(1L)).thenReturn(List.of(currentManager));

        assertThatThrownBy(() -> custService.update(cust))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("担当者情報が最新ではありません");
    }

    @Test
    void createUsesGeneratedCustIdForManagers() {
        Cust cust = new Cust();
        Manager manager = new Manager();
        manager.setManagerNm("佐藤");
        cust.setManagers(List.of(manager));

        doAnswer(invocation -> {
            Cust insertedCust = invocation.getArgument(0);
            insertedCust.setCustId(42L);
            return 1;
        }).when(custMapper).create(cust);
        when(custMapper.insertManager(any(Manager.class))).thenReturn(1);

        custService.create(cust);

        ArgumentCaptor<Manager> captor = ArgumentCaptor.forClass(Manager.class);
        verify(custMapper).insertManager(captor.capture());
        assertThat(captor.getValue().getManagerCustId()).isEqualTo(42L);
    }

    @Test
    void deleteThrowsWhenCustWasNotDeleted() {
        when(custMapper.delete(1L)).thenReturn(0);

        assertThatThrownBy(() -> custService.deleteCust(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("削除対象の取引先");
    }
}
