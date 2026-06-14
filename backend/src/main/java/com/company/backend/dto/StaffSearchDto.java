package com.company.backend.dto;

public class StaffSearchDto {

    private String staffNm;
    private String staffCls;
    private String staffSalesstatus;
    private String comCd;
    private String comContent1;
    private Integer page;
    private Integer pageSize;
    private Integer offset;

    public String getComCd() {
        return comCd;
    }

    public void setComCd(String comCd) {
        this.comCd = comCd;
    }

    public String getComContent1() {
        return comContent1;
    }

    public void setComContent1(String comContent1) {
        this.comContent1 = comContent1;
    }

    public String getStaffNm() {
        return staffNm;
    }

    public void setStaffNm(String staffNm) {
        this.staffNm = staffNm;
    }

    public String getStaffCls() {
        return staffCls;
    }

    public void setStaffCls(String staffCls) {
        this.staffCls = staffCls;
    }

    public String getStaffSalesstatus() {
        return staffSalesstatus;
    }

    public void setStaffSalesstatus(String staffSalesstatus) {
        this.staffSalesstatus = staffSalesstatus;
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