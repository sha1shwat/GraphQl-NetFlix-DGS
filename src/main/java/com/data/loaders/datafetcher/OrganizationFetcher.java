package com.data.loaders.datafetcher;


import com.data.loaders.context.OrganizationContextBuilder;
import com.data.loaders.model.Organization;
import com.data.loaders.repository.OrganizationRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DgsComponent
public class OrganizationFetcher {

    @Autowired
    private OrganizationRepository repository;

    @Autowired
    private OrganizationContextBuilder contextBuilder;


    @DgsData(parentType = "QueryResolver",field = "allOrganization")
    public List<Organization>  findAll(DgsDataFetchingEnvironment dfe){
        List<Organization> allOrganization = (List<Organization>) repository.findAll();
        contextBuilder.withOrganization(allOrganization).build();
        return allOrganization;
    }

    @DgsData(parentType = "QueryResolver",field = "organization")
    public Organization findById(@InputArgument("id") String id){
        return repository.findById(id).get();
    }


}
