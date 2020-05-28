package com.ssafy.model.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Embeddable
@Value
public class Additional implements Serializable {
	String key;
	String value;
	
	@JsonCreator
    public Additional(@JsonProperty("key") String key, @JsonProperty("value") String value) {
        this.key = key;
        this.value = value;
    }
}
