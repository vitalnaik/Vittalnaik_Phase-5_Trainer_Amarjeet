package com.project.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.project.model.Category;

import lombok.Data;

@Data
public class ProductDTO {
	private long id;
	private String name;
	
	private int categoryId;
	
	private double price;
	private String description;
	private String imageName;


}
