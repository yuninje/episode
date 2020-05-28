package com.ssafy.model.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class CharacterAdditionalConverter implements AttributeConverter<Set<Additional>, String> {
	private ObjectMapper om = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(Set<Additional> attribute) {
		 try {
			return om.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("error log...");
		}
	}

	@Override
	public Set<Additional> convertToEntityAttribute(String dbData) {
		try {
			return om.readValue(dbData, new TypeReference<Set<Additional>>() { });
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("error log...");
		}
	}
}
