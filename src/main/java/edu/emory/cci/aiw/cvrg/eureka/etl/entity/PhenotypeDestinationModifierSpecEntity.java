package edu.emory.cci.aiw.cvrg.eureka.etl.entity;

/*
 * #%L
 * Eureka Common
 * %%
 * Copyright (C) 2012 - 2015 Emory University
 * %%
 * This program is dual licensed under the Apache 2 and GPLv3 licenses.
 * 
 * Apache License, Version 2.0:
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * GNU General Public License version 3:
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author Nita Deshpande
 */
@Entity
@Table(name = "phen_dest_modifierspecs", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "phendestconceptspecs_id"})})
public class PhenotypeDestinationModifierSpecEntity {
	@Id
	@SequenceGenerator(name = "PHEN_MS_SEQ_GENERATOR",
		sequenceName = "PHEN_MS_SEQ", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
		generator = "PHEN_MS_SEQ_GENERATOR")
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	private String codePrefix;
	
	@Column(nullable = false)
	private String property;
	
	private String val;
	
	@ManyToOne
	@JoinColumn(name = "phendestconceptspecs_id")
	private PhenotypeDestinationConceptSpecEntity conceptSpec;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodePrefix() {
		return codePrefix;
	}

	public void setCodePrefix(String codePrefix) {
		this.codePrefix = codePrefix;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public PhenotypeDestinationConceptSpecEntity getConceptSpec() {
		return conceptSpec;
	}

	public void setConceptSpec(PhenotypeDestinationConceptSpecEntity conceptSpec) {
		this.conceptSpec = conceptSpec;
	}
	
}
