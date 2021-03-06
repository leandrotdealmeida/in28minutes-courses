package com.devjapa.in28minutes.filtering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilterController {

	@GetMapping("/filtering")
	public SomeBean retriveSomeBean() {
		return new SomeBean("value1", "value2", "value3");
	}

	@GetMapping("/filtering-list")
	public List<SomeBean> retriveListSomeBean() {
		return Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("value1", "value2", "value3"));
	}

	@GetMapping("/filtering-dinamic")
	public MappingJacksonValue retriveDinamicSomeBean() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");

		MappingJacksonValue mapping = dinamicFilter(someBean, "field1", "field2");

		return mapping;
	}


	@GetMapping("/filtering-list-dinamic")
	public MappingJacksonValue retriveDinamicListSomeBean() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"),	new SomeBean("value1", "value2", "value3"));

		MappingJacksonValue mapping = dinamicFilter(list, "field2", "field3");

		return mapping;
	}
	
	private MappingJacksonValue dinamicFilter(Object someBean, String field1, String field2) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(field1, field2);

		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBean", filter);

		MappingJacksonValue mapping = new MappingJacksonValue(someBean);

		mapping.setFilters(filters);
		return mapping;
	}

}
