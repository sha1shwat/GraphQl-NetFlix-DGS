package com.data.loaders.datafetcher;

import com.data.loaders.model.Department;
import com.data.loaders.model.Organization;
import com.data.loaders.repository.DepartmentRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CompletableFuture;

@DgsComponent
@Slf4j
public class DepartmentFetcher {

    @Autowired
    private DepartmentRepository departmentRepository;

    @DgsData(parentType = "Organization",field = "department")
    public CompletableFuture<Department> findDepartment(DgsDataFetchingEnvironment dfe){

        DataLoader<String, Department> departmentDataLoader = dfe.getDataLoader("department");


       Organization organization = dfe.getSource();

//        log.info("Department fetch for id {}",employee.getId());
       // return departmentRepository.findByOrganizationId(organization.getId()).get();
       return departmentDataLoader.load(organization.getId());

    }
}
