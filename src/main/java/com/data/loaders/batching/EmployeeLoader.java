package com.data.loaders.batching;

import com.data.loaders.federation.GraphQLFederation;
import com.data.loaders.model.Employee;
import com.data.loaders.model.Organization;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
@DgsDataLoader(name = "organization")
public class EmployeeLoader implements MappedBatchLoader<String, List<Employee>> {

    @Autowired
    GraphQLFederation graphQLFederation;

    @Override
    public CompletionStage<Map<String, List<Employee>>> load(Set<String> set) {



            log.info("department fetcher is called for {}", set.toString());
            return CompletableFuture.supplyAsync(() -> {
                try {
                    return get(new ArrayList<>(set));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            });

    }

    private Map<String, List<Employee>> get(List<String> ids) throws JsonProcessingException {

        List<Employee> employeeList = graphQLFederation.getData(ids);
        Map<String, List<Employee>> map = new LinkedHashMap<>();
        for (int i=0;i<employeeList.size();i++){
            if (!map.containsKey(employeeList.get(i).getOrganizationId())){
                List<Employee> employees = new ArrayList<>();
                employees.add(employeeList.get(i));
                map.put(employeeList.get(i).getOrganizationId(),employees);
            } else {
                map.get(employeeList.get(i).getOrganizationId()).add(employeeList.get(i));
            }

        }
        return map;
    }

//    public Map<Employee, Organization> get(List<Employee> set) throws JsonProcessingException {
//        List<Integer> ids = new ArrayList<>();
//        List<String> names = new ArrayList<>();
//        names.add("Technology Development");
//        names.add("Technology Development");
//        names.add("Site Operations");
//
//        for(Employee e : set){
//            System.out.println((Integer) e.getId());
//        }
//
//
//        List<Organization> departmentList = graphQLFederation.getData(ids,names);
//        Map<Employee,Organization> map = new HashMap<>();
//        int i=0;
//        for(Employee e : set){
//            map.put(e,departmentList.get(i));
//            i++;
//        }
//        return map;
//
//    }
}
