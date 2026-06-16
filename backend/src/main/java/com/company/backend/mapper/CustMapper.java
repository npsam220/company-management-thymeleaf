package com.company.backend.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.company.backend.dto.CustSearchDto;
import com.company.backend.entity.Cust;
import com.company.backend.entity.Manager;

@Mapper
public interface CustMapper {
    List<Cust> searchCust(CustSearchDto dto);

    int countCust(CustSearchDto dto);

    void create();

    int update(Cust cust);

    int updateManager(Manager manager);

    int insertManager(Manager manager);

    int deleteManager(Manager manager);

    void delete();

    Cust getCustDetail(Long custId);

    List<Manager> getManagersByCustId(Long custId);

}
