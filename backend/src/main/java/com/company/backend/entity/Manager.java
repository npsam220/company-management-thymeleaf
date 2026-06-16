package com.company.backend.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Manager {

    private String managerCd;
    private String managerChrcd;
    @NotBlank(message = "担当者名は必須です。")
    @Size(max = 128, message = "担当者名は128文字以内で入力してください。")
    private String managerNm;
    private Long managerCustId;
    @Size(max = 128, message = "電話番号は128文字以内で入力してください。")
    private String managerPno;
    @Size(max = 128, message = "役職は128文字以内で入力してください。")
    private String managerJob;
    @Size(max = 256, message = "備考は256文字以内で入力してください。")
    private String managerRmk;
    @Email(message = "メールの形式で入力してください。")
    @Size(max = 128, message = "メールは128文字以内で入力してください。")
    private String managerMail;

    public String getManagerCd() {
        return managerCd;
    }

    public void setManagerCd(String managerCd) {
        this.managerCd = managerCd;
    }

    public String getManagerChrcd() {
        return managerChrcd;
    }

    public void setManagerChrcd(String managerChrcd) {
        this.managerChrcd = managerChrcd;
    }

    public String getManagerNm() {
        return managerNm;
    }

    public void setManagerNm(String managerNm) {
        this.managerNm = managerNm;
    }

    public Long getManagerCustId() {
        return managerCustId;
    }

    public void setManagerCustId(Long managerCustId) {
        this.managerCustId = managerCustId;
    }

    public String getManagerPno() {
        return managerPno;
    }

    public void setManagerPno(String managerPno) {
        this.managerPno = managerPno;
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

    public String getManagerMail() {
        return managerMail;
    }

    public void setManagerMail(String managerMail) {
        this.managerMail = managerMail;
    }
}
