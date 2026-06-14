package com.company.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.backend.config.UploadProperties;
import com.company.backend.dto.StaffSearchDto;
import com.company.backend.entity.Staff;
import com.company.backend.service.StaffService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class StaffViewControllerTests {

    @Mock
    private StaffService staffService;

    @Mock
    private UploadProperties uploadProperties;

    private StaffViewController controller;

    @BeforeEach
    void setUp() {
        controller = new StaffViewController(staffService, uploadProperties);
    }

    @Test
    void listAddsSearchResultsAndPaginationToModel() {
        StaffSearchDto search = new StaffSearchDto();
        Staff staff = new Staff();
        staff.setStaffId(10L);
        staff.setStaffNm("山田太郎");
        when(staffService.countStaff(search)).thenReturn(12);
        when(staffService.searchStaff(search)).thenReturn(List.of(staff));
        when(staffService.findStaffClsList()).thenReturn(List.of());
        when(staffService.findSalesStatusList()).thenReturn(List.of());
        Model model = new ConcurrentModel();

        String view = controller.list(search, 2, 5, model);

        assertThat(view).isEqualTo("staff/list");
        assertThat(model.getAttribute("staffList")).isEqualTo(List.of(staff));
        assertThat(model.getAttribute("currentPage")).isEqualTo(2);
        assertThat(model.getAttribute("totalPages")).isEqualTo(3);
        assertThat(model.getAttribute("totalCount")).isEqualTo(12);
    }

    @Test
    void listNormalizesUnsupportedPageValues() {
        StaffSearchDto search = new StaffSearchDto();
        when(staffService.countStaff(search)).thenReturn(0);
        when(staffService.searchStaff(search)).thenReturn(List.of());
        when(staffService.findStaffClsList()).thenReturn(List.of());
        when(staffService.findSalesStatusList()).thenReturn(List.of());

        controller.list(search, -1, 99, new ConcurrentModel());

        ArgumentCaptor<StaffSearchDto> captor = ArgumentCaptor.forClass(StaffSearchDto.class);
        verify(staffService).searchStaff(captor.capture());
        assertThat(captor.getValue().getPage()).isEqualTo(1);
        assertThat(captor.getValue().getPageSize()).isEqualTo(5);
    }

    @Test
    void createFormUsesEmptyStaffAndMasterData() {
        when(staffService.findStaffClsList()).thenReturn(List.of());
        when(staffService.findSalesStatusList()).thenReturn(List.of());
        Model model = new ConcurrentModel();

        String view = controller.createForm(model);

        assertThat(view).isEqualTo("staff/create");
        assertThat(model.getAttribute("staff")).isInstanceOf(Staff.class);
        assertThat(model.getAttribute("pageTitle")).isEqualTo("スタッフ新規登録");
    }
}
