package com.data.loaders.datafetcher.mutation;

import com.data.loaders.model.Organization;
import com.data.loaders.model.OrganizationInput;
import com.data.loaders.repository.OrganizationRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class OrganizationMutation {

    @Autowired
    OrganizationRepository organizationRepository;

    @DgsData(parentType = "MutationResolver",field = "newOrganization")
    public Organization addOrganization(@InputArgument("organization")OrganizationInput organizationInput){
        Organization organization = new Organization();
        organization.setId(organizationInput.getId());
        organization.setName(organizationInput.getName());
        organizationRepository.save(organization);
        return organization;
    }
}
