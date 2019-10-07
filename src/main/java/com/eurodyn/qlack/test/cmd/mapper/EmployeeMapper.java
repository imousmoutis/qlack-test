package com.eurodyn.qlack.test.cmd.mapper;

import com.eurodyn.qlack.test.cmd.dto.EmployeeDTO;
import com.eurodyn.qlack.test.cmd.model.Employee;
import org.mapstruct.Mapper;

/**
 * @author European Dynamics
 */

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

  EmployeeDTO employeeToEmployeeDTO(Employee employee);
}
