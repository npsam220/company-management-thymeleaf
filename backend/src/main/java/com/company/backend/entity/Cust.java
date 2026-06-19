package com.company.backend.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class Cust {

    private Long custId;
    @NotBlank(message = "取引先名は必須です。")
    @Size(max = 128, message = "取引先名は128文字以内で入力してください。")
    private String custNm;
    @Size(max = 128, message = "カナは128文字以内で入力してください。")
    private String custKn;
    @Size(max = 15, message = "電話番号は15文字以内で入力してください。")
    private String custPhone;
    private String custDelflg;
    @Size(max = 128, message = "FAXは128文字以内で入力してください。")
    private String custFax;
    @Size(max = 128, message = "Webサイトは128文字以内で入力してください。")
    private String custWeb;
    @Size(max = 10, message = "郵便番号は10文字以内で入力してください。")
    private String custPostno;
    @Size(max = 128, message = "住所は128文字以内で入力してください。")
    private String custAddress;
    @Pattern(regexp = "|01|02|03", message = "取引先の種別を正しく選択してください。")
    private String custType;
    @PositiveOrZero(message = "資本金は0以上で入力してください。")
    private Long custMoney;
    @Size(max = 25, message = "登録番号は25文字以内で入力してください。")
    private String custInvoiceNo;
    @Pattern(regexp = "|01|02", message = "Pマークを正しく選択してください。")
    private String custInforSecurity;
    @Pattern(regexp = "|01|02", message = "派遣資格を正しく選択してください。")
    private String custDispatch;
    private Integer custEmpnum;
    private LocalDate custStartdt;
    private Integer custAnnualsale;
    @Size(max = 128, message = "自社担当者は128文字以内で入力してください。")
    private String custCompanyrep;
    @Size(max = 256, message = "備考は256文字以内で入力してください。")
    private String custRmk1;
    @Size(max = 256, message = "備考は256文字以内で入力してください。")
    private String custRmk2;
    @Pattern(regexp = "|01|02|03|05", message = "支払締日を正しく選択してください。")
    private String custClosedt;
    @Pattern(regexp = "|01|02|03", message = "支払月を正しく選択してください。")
    private String custPaydt;
    @Min(value = 1, message = "支払指定日は1から31で入力してください。")
    @Max(value = 31, message = "支払指定日は1から31で入力してください。")
    private Integer custOrderdt;
    @Pattern(regexp = "|01|02|03", message = "入金月を正しく選択してください。")
    private String custGetdt;
    @Pattern(regexp = "|01|02|03|05", message = "入金締日を正しく選択してください。")
    private String custGetcdt;
    @Min(value = 1, message = "入金指定日は1から31で入力してください。")
    @Max(value = 31, message = "入金指定日は1から31で入力してください。")
    private Integer custOrdergdt;
    private String bankCd;
    private String bankChrcd;
    @Size(max = 20, message = "銀行名は20文字以内で入力してください。")
    private String custBankNm;
    @Size(max = 20, message = "支店名は20文字以内で入力してください。")
    private String custBranchNm;
    @Pattern(regexp = "|01|02|03|04", message = "預金種別を正しく選択してください。")
    private String custDeposittype;
    @Size(max = 20, message = "支店番号は20文字以内で入力してください。")
    private String custBranchCd;
    @Size(max = 20, message = "口座番号は20文字以内で入力してください。")
    private String custAccountno;
    @Size(max = 128, message = "名義人は128文字以内で入力してください。")
    private String custNominee;
    private String custCrdUsr;
    private LocalDateTime custCrdDt;
    private String custUpdUsr;
    private LocalDateTime custUpdDt;
    private String managerNm;
    private String managerPno;
    private String managerMail;
    private String managerJob;
    private String managerRmk;
    @Valid
    private List<Manager> managers;

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
    }

    public String getCustKn() {
        return custKn;
    }

    public void setCustKn(String custKn) {
        this.custKn = custKn;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public String getCustDelflg() {
        return custDelflg;
    }

    public void setCustDelflg(String custDelflg) {
        this.custDelflg = custDelflg;
    }

    public String getCustFax() {
        return custFax;
    }

    public void setCustFax(String custFax) {
        this.custFax = custFax;
    }

    public String getCustWeb() {
        return custWeb;
    }

    public void setCustWeb(String custWeb) {
        this.custWeb = custWeb;
    }

    public String getCustPostno() {
        return custPostno;
    }

    public void setCustPostno(String custPostno) {
        this.custPostno = custPostno;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public Long getCustMoney() {
        return custMoney;
    }

    public void setCustMoney(Long custMoney) {
        this.custMoney = custMoney;
    }

    public String getCustInvoiceNo() {
        return custInvoiceNo;
    }

    public void setCustInvoiceNo(String custInvoiceNo) {
        this.custInvoiceNo = custInvoiceNo;
    }

    public String getCustInforSecurity() {
        return custInforSecurity;
    }

    public void setCustInforSecurity(String custInforSecurity) {
        this.custInforSecurity = custInforSecurity;
    }

    public String getCustDispatch() {
        return custDispatch;
    }

    public void setCustDispatch(String custDispatch) {
        this.custDispatch = custDispatch;
    }

    public Integer getCustEmpnum() {
        return custEmpnum;
    }

    public void setCustEmpnum(Integer custEmpnum) {
        this.custEmpnum = custEmpnum;
    }

    public LocalDate getCustStartdt() {
        return custStartdt;
    }

    public void setCustStartdt(LocalDate custStartdt) {
        this.custStartdt = custStartdt;
    }

    public Integer getCustAnnualsale() {
        return custAnnualsale;
    }

    public void setCustAnnualsale(Integer custAnnualsale) {
        this.custAnnualsale = custAnnualsale;
    }

    public String getCustCompanyrep() {
        return custCompanyrep;
    }

    public void setCustCompanyrep(String custCompanyrep) {
        this.custCompanyrep = custCompanyrep;
    }

    public String getCustRmk1() {
        return custRmk1;
    }

    public void setCustRmk1(String custRmk1) {
        this.custRmk1 = custRmk1;
    }

    public String getCustRmk2() {
        return custRmk2;
    }

    public void setCustRmk2(String custRmk2) {
        this.custRmk2 = custRmk2;
    }

    public String getCustClosedt() {
        return custClosedt;
    }

    public void setCustClosedt(String custClosedt) {
        this.custClosedt = custClosedt;
    }

    public String getCustPaydt() {
        return custPaydt;
    }

    public void setCustPaydt(String custPaydt) {
        this.custPaydt = custPaydt;
    }

    public Integer getCustOrderdt() {
        return custOrderdt;
    }

    public void setCustOrderdt(Integer custOrderdt) {
        this.custOrderdt = custOrderdt;
    }

    public String getCustGetdt() {
        return custGetdt;
    }

    public void setCustGetdt(String custGetdt) {
        this.custGetdt = custGetdt;
    }

    public String getCustGetcdt() {
        return custGetcdt;
    }

    public void setCustGetcdt(String custGetcdt) {
        this.custGetcdt = custGetcdt;
    }

    public Integer getCustOrdergdt() {
        return custOrdergdt;
    }

    public void setCustOrdergdt(Integer custOrdergdt) {
        this.custOrdergdt = custOrdergdt;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getBankChrcd() {
        return bankChrcd;
    }

    public void setBankChrcd(String bankChrcd) {
        this.bankChrcd = bankChrcd;
    }

    public String getCustBankNm() {
        return custBankNm;
    }

    public void setCustBankNm(String custBankNm) {
        this.custBankNm = custBankNm;
    }

    public String getCustBranchNm() {
        return custBranchNm;
    }

    public void setCustBranchNm(String custBranchNm) {
        this.custBranchNm = custBranchNm;
    }

    public String getCustDeposittype() {
        return custDeposittype;
    }

    public void setCustDeposittype(String custDeposittype) {
        this.custDeposittype = custDeposittype;
    }

    public String getCustBranchCd() {
        return custBranchCd;
    }

    public void setCustBranchCd(String custBranchCd) {
        this.custBranchCd = custBranchCd;
    }

    public String getCustAccountno() {
        return custAccountno;
    }

    public void setCustAccountno(String custAccountno) {
        this.custAccountno = custAccountno;
    }

    public String getCustNominee() {
        return custNominee;
    }

    public void setCustNominee(String custNominee) {
        this.custNominee = custNominee;
    }

    public String getCustCrdUsr() {
        return custCrdUsr;
    }

    public void setCustCrdUsr(String custCrdUsr) {
        this.custCrdUsr = custCrdUsr;
    }

    public LocalDateTime getCustCrdDt() {
        return custCrdDt;
    }

    public void setCustCrdDt(LocalDateTime custCrdDt) {
        this.custCrdDt = custCrdDt;
    }

    public String getCustUpdUsr() {
        return custUpdUsr;
    }

    public void setCustUpdUsr(String custUpdUsr) {
        this.custUpdUsr = custUpdUsr;
    }

    public LocalDateTime getCustUpdDt() {
        return custUpdDt;
    }

    public void setCustUpdDt(LocalDateTime custUpdDt) {
        this.custUpdDt = custUpdDt;
    }

    public String getManagerNm() {
        return managerNm;
    }

    public void setManagerNm(String managerNm) {
        this.managerNm = managerNm;
    }

    public String getManagerPno() {
        return managerPno;
    }

    public void setManagerPno(String managerPno) {
        this.managerPno = managerPno;
    }

    public String getManagerMail() {
        return managerMail;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }

    public String getManagerJob() {
        return managerJob;
    }

    public void setManagerJob(String managerJob) {
        this.managerJob = managerJob;
    }

    public String getManagerRmk() {
        return managerRmk;
    }

    public void setManagerRmk(String managerRmk) {
        this.managerRmk = managerRmk;
    }

    public List<Manager> getManagers() {
        return managers;
    }

    public void setManagers(List<Manager> managers) {
        this.managers = managers;
    }
}
