package com.company.backend.dto;

import java.util.List;

import com.company.backend.entity.Staff;

public class StaffSearchResponseDto {
    private List<Staff> list;

    private int totalCount;

    public List<Staff> getList() {
        return list;
    }

    public void setList(List<Staff> list) {
        this.list = list;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
