package com.company.backend.dto;

public class StaffDetailDto {
    private Integer staffId;

    private String staffNo;

    private String staffNm;

    private String staffKana;

    private String email;

    private String phone;

    private String address;

    private String staffClsName;

    private String staffSalesstatusName;

    private String deptNm;

    private String roleNm;

    private String memo;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getStaffNm() {
        return staffNm;
    }

    public void setStaffNm(String staffNm) {
        this.staffNm = staffNm;
    }

    public String getStaffKana() {
        return staffKana;
    }

    public void setStaffKana(String staffKana) {
        this.staffKana = staffKana;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDeptNm() {
        return deptNm;
    }

    public void setDeptNm(String deptNm) {
        this.deptNm = deptNm;
    }

    public String getRoleNm() {
        return roleNm;
    }

    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getStaffClsName() {
        return staffClsName;
    }

    public void setStaffClsName(String staffClsName) {
        this.staffClsName = staffClsName;
    }

    public String getStaffSalesstatusName() {
        return staffSalesstatusName;
    }

    public void setStaffSalesstatusName(String staffSalesstatusName) {
        this.staffSalesstatusName = staffSalesstatusName;
    }

}
