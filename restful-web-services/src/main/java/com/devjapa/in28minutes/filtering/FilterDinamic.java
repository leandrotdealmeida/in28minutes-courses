package com.devjapa.in28minutes.filtering;

import org.springframework.http.converter.json.MappingJacksonValue;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class FilterDinamic {

	
	protected MappingJacksonValue dinamicFilter(Object someBean, String field1, String field2) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(field1, field2);
	
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBean", filter);
	
		MappingJacksonValue mapping = new MappingJacksonValue(someBean);
	
		mapping.setFilters(filters);
		return mapping;
	}

}