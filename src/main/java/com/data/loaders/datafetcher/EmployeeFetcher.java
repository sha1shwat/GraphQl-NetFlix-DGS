package com.data.loaders.datafetcher;

import com.data.loaders.batching.EmployeeLoader;
import com.data.loaders.model.Organization;
import com.data.loaders.model.Employee;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;

@DgsComponent
public class EmployeeFetcher {

    @DgsData(parentType = "Organization",field = "employee")
    public CompletableFuture<Employee> findDepartment(DgsDataFetchingEnvironment dfe){

        DataLoader<String, Employee> organizationDataLoader = dfe.getDataLoader(EmployeeLoader.class);


        Organization organization = dfe.getSource();


        //   return departmentRepository.findByEmployeeId(employee.getId()).get();
        return organizationDataLoader.load(organization.getId());

    }
}
