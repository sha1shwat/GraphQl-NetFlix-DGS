package com.data.loaders.context;

import com.data.loaders.model.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OrganizationContext {
    private List<Organization> organizations = new ArrayList<>();
}