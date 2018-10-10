/*
 * #%L
 * Eureka Services
 * %%
 * Copyright (C) 2012 - 2013 Emory University
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
package edu.emory.cci.aiw.cvrg.eureka.etl.conversion;

import org.protempa.PropositionDefinition;

import java.util.List;
import org.eurekaclinical.eureka.client.comm.Phenotype;
import org.eurekaclinical.eureka.client.comm.exception.PhenotypeHandlingException;

/**
 * Converts Eureka! database entities into equivalent Protempa proposition
 * definitions.
 *
 * @param <E> The type of {@link PhenotypeEntity} to convert.
 */
interface PropositionDefinitionConverter<E extends Phenotype,
		P extends PropositionDefinition> {

	/**
	 * Converts a Eureka database entity into an equivalent list of Protempa
	 * proposition definitions.
	 *
	 * @param entity The database entity to convert
	 * @return a list of {@link PropositionDefinition}s
	 */
	List<PropositionDefinition> convert(E entity) throws PhenotypeHandlingException;

	/**
	 * Retrieves the primary proposition created by a call to
	 * {@link #convert(org.eurekaclinical.phenotype.service.entity.PhenotypeEntity)}.
	 * This is mostly for converters invoking each other and needing only
	 * the final highest level (ie, primary) proposition that was created.
	 *
	 * @return a {@link PropositionDefinition}.
	 */
	P getPrimaryPropositionDefinition();
	
	String getPrimaryPropositionId();
}