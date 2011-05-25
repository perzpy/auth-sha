package com.create.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  
@Table(name="TEST_EXAMPLE") 
public class ExampleTable {
	
	
	private String id;
	private String name;
	private int value;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public String toString(){
		return "[id=" + id + 
				",name=" + name + 
				",value=" + value + "]";
	}

}
