package com.company.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.backend.dto.CommonDto;
import com.company.backend.dto.StaffSearchDto;
import com.company.backend.entity.Staff;
import com.company.backend.mapper.StaffMapper;

@Service
public class StaffService {

    private final StaffMapper staffMapper;

    public StaffService(StaffMapper staffMapper) {
        this.staffMapper = staffMapper;
    }

    public List<Staff> findAll() {
        return staffMapper.findAll();
    }

    public List<CommonDto> findStaffClsList() {
        return staffMapper.findStaffClsList();
    }

    public List<CommonDto> findSalesStatusList() {
        return staffMapper.findSalesStatusList();
    }

    public List<Staff> searchStaff(StaffSearchDto dto) {

        int offset = (dto.getPage() - 1) * dto.getPageSize();

        dto.setOffset(offset);

        return staffMapper.searchStaff(dto);

    }

    public Staff findById(Long staffId) {
        return staffMapper.findById(staffId);
    }

    public void create(Staff staff) {
        String latestStaffNo = generateStaffNo(staff.getStaffIndt().toString());

        if (!latestStaffNo.equals(staff.getStaffNo())) {
            throw new IllegalArgumentException("社員番号が最新ではありません。契約日を再選択してください。");
        }

        staffMapper.create(staff);
    }

    public void update(Staff staff) {
        staffMapper.update(staff);
    }

    public void delete(Long staffId) {
        staffMapper.delete(staffId);
    }

    public int countStaff(StaffSearchDto dto) {
        return staffMapper.countStaff(dto);
    }

    public Staff getStaffDetail(Long staffId) {
        return staffMapper.getStaffDetail(staffId);
    }

    public String generateStaffNo(String staffIndt) {
        // staffIndt: 2024-03-05
        String datePart = staffIndt.replace("-", ""); // 20240305

        String maxStaffNo = staffMapper.findMaxStaffNoByDate(datePart);

        if (maxStaffNo == null) {
            return datePart + "01";
        }

        String lastTwoDigits = maxStaffNo.substring(8, 10);
        int nextNo = Integer.parseInt(lastTwoDigits) + 1;

        return datePart + String.format("%02d", nextNo);
    }
}
