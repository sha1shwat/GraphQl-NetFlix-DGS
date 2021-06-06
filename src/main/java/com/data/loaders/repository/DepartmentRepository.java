package com.data.loaders.repository;

import com.data.loaders.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department,Integer> {


    Optional<Department> findByOrganizationId(String id);
    Iterable<Department> findByOrganizationIdIn( Iterable<String> ids);
}
