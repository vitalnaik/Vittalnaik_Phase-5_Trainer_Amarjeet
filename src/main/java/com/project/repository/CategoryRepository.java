package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
