package com.data.loaders.batching;

import com.data.loaders.model.Department;
import com.data.loaders.model.Employee;
import com.data.loaders.repository.DepartmentRepository;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@DgsDataLoader(name = "department")
@Slf4j
public class DepartmentLoader implements MappedBatchLoader<String, List<Department>> {

    @Autowired
    DepartmentRepository departmentRepository;
    @Override
    public CompletionStage<Map<String, List<Department>>> load(Set<String> set) {
        log.info("department fetcher is called for {}",set.toString());
        return CompletableFuture.supplyAsync(()-> get(new ArrayList<>(set)));
    }


    private Map<String,List<Department>> get(List<String> ids){

        List<Department> departmentList = (List<Department>) departmentRepository.findByOrganizationIdIn(ids);
        Map<String,List<Department>> map = new LinkedHashMap<>();
        for (int i=0;i<departmentList.size();i++){
            if (!map.containsKey(departmentList.get(i).getOrganizationId())){
                List<Department> departments = new ArrayList<>();
                departments.add(departmentList.get(i));
                map.put(departmentList.get(i).getOrganizationId(),departments);
            } else {
                map.get(departmentList.get(i).getOrganizationId()).add(departmentList.get(i));
            }

        }
        return map;
    }
}
