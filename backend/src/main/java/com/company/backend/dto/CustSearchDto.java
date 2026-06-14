package com.company.backend.dto;

public class CustSearchDto {
    private String custNm; // 取引先名
    private String custInforSecurity; // Pマーク/ISMS
    private String custDispatch; // 派遣資格
    private String managerNm; // 他社担当者

    private Integer page;
    private Integer pageSize; // 10、20、50
    private Integer offset;

    public String getCustNm() {
        return custNm;
    }

    public void setCustNm(String custNm) {
        this.custNm = custNm;
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

    public String getManagerNm() {
        return managerNm;
    }

    public void setManagerNm(String managerNm) {
        this.managerNm = managerNm;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
