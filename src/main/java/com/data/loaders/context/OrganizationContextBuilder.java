package com.data.loaders.context;

import com.data.loaders.model.Organization;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrganizationContextBuilder implements DgsCustomContextBuilder<OrganizationContext> {

    private List<Organization> organizations;

    public OrganizationContextBuilder withOrganization(List<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    @Override
    public OrganizationContext build() {
        return new OrganizationContext(organizations);
    }
}
