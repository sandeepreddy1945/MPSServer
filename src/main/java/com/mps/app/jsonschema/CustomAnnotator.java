/**
 * 
 */
package com.mps.app.jsonschema;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.codemodel.JDefinedClass;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep
 *
 */
public class CustomAnnotator extends Jackson2Annotator {

	/**
	 * @param generationConfig
	 */
	public CustomAnnotator(GenerationConfig generationConfig) {
		super(generationConfig);
	}

	@Override
	public void propertyInclusion(JDefinedClass clazz, JsonNode schema) {
		super.propertyInclusion(clazz, schema);
		clazz.annotate(Getter.class);
		clazz.annotate(Setter.class);
		clazz.annotate(Entity.class);
		clazz.annotate(Table.class);
	}
}
