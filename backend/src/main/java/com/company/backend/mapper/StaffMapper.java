package com.company.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.backend.dto.CommonDto;
import com.company.backend.dto.StaffSearchDto;
import com.company.backend.entity.Staff;

@Mapper
public interface StaffMapper {

    List<Staff> findAll();

    List<CommonDto> findStaffClsList();

    List<CommonDto> findSalesStatusList();

    Staff findById(Long staffId);

    void create(Staff staff);

    void update(Staff staff);

    void delete(Long staffId);

    List<Staff> searchStaff(StaffSearchDto dto);

    // List<Staff> searchStaff(StaffSearchDto dto, int offset, Integer pageSize);
    int countStaff(StaffSearchDto dto);

    Staff getStaffDetail(Long staffId);

    String findMaxStaffNoByDate(String datePart);
}
