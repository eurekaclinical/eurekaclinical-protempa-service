package edu.emory.cci.aiw.cvrg.eureka.etl.dest;

/*
 * #%L
 * Eureka Protempa ETL
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
import com.google.inject.persist.Transactional;
import com.google.inject.persist.UnitOfWork;
import org.protempa.dest.key.KeyLoaderDestination;

import org.eurekaclinical.eureka.client.comm.Cohort;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.CohortDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.CohortEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.CovidOmopDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.DestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.I2B2DestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.Neo4jDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.OmopDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.PatientSetExtractorDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.PatientSetSenderDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.PhenotypeSearchDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.TabularFileDestinationEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.config.EtlProperties;
import edu.emory.cci.aiw.cvrg.eureka.etl.dao.DestinationDao;
import edu.emory.cci.aiw.i2b2etl.dest.I2b2Destination;
import edu.emory.cci.aiw.i2b2etl.dest.config.ConfigurationInitException;
import edu.emory.cci.aiw.neo4jetl.Neo4jDestination;

import org.protempa.dest.DestinationInitException;
import org.protempa.dest.deid.DeidentifiedDestination;
import edu.emory.cci.aiw.cvrg.eureka.etl.dao.DeidPerPatientParamsDao;
import edu.emory.cci.aiw.cvrg.eureka.etl.dao.ETLIdPoolDao;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.protempa.query.QueryMode;

/**
 *
 * @author Andrew Post
 */
@Singleton
public class ProtempaDestinationFactory {

    private final EtlProperties etlProperties;
    private final DestinationDao destinationDao;
    private final DeidPerPatientParamsDao deidPerPatientParamsDao;
    private final EurekaDeidConfigFactory eurekaDeidConfigFactory;
    private final Provider<ETLIdPoolDao> idPoolDaoProvider;
    private final UnitOfWork unitOfWork;

    @Inject
    public ProtempaDestinationFactory(DestinationDao inDestinationDao, 
            DeidPerPatientParamsDao inDeidPerPatientParamsDao, 
            Provider<ETLIdPoolDao> inIdPoolDaoProvider,
            EtlProperties etlProperties, 
            EurekaDeidConfigFactory inEurekaDeidConfigFactory,
            UnitOfWork inUnitOfWork) {
        this.destinationDao = inDestinationDao;
        this.deidPerPatientParamsDao = inDeidPerPatientParamsDao;
        this.etlProperties = etlProperties;
        this.eurekaDeidConfigFactory = inEurekaDeidConfigFactory;
        this.idPoolDaoProvider = inIdPoolDaoProvider;
        this.unitOfWork = inUnitOfWork;
    }

    @Transactional
    public org.protempa.dest.Destination getInstance(Long destId, QueryMode queryMode) throws DestinationInitException {
        DestinationEntity dest = this.destinationDao.retrieve(destId);
        return getInstance(dest, queryMode);
    }

    @Transactional
    public org.protempa.dest.Destination getInstance(DestinationEntity dest, QueryMode queryMode) throws DestinationInitException {
        org.protempa.dest.Destination actualDest;
        try {
            if (dest instanceof I2B2DestinationEntity) {
                actualDest = new I2b2Destination(new EurekaI2b2Configuration((I2B2DestinationEntity) dest, this.etlProperties));
            } else if (dest instanceof CohortDestinationEntity) {
                CohortEntity cohortEntity = ((CohortDestinationEntity) dest).getCohort();
                Cohort cohort = cohortEntity.toCohort();
                actualDest = new KeyLoaderDestination(dest.getName(), new CohortCriteria(cohort));
            } else if (dest instanceof Neo4jDestinationEntity) {
                actualDest = new Neo4jDestination(new EurekaNeo4jConfiguration((Neo4jDestinationEntity) dest));
            } else if (dest instanceof PatientSetExtractorDestinationEntity) {
                actualDest = new PatientSetExtractorDestination(this.etlProperties, (PatientSetExtractorDestinationEntity) dest);
            } else if (dest instanceof PatientSetSenderDestinationEntity) {
                actualDest = new PatientSetSenderDestination(this.etlProperties, (PatientSetSenderDestinationEntity) dest);
            } else if (dest instanceof TabularFileDestinationEntity) {
                actualDest = new TabularFileDestination(this.etlProperties, (TabularFileDestinationEntity) dest, this.idPoolDaoProvider);
            } else if (dest instanceof OmopDestinationEntity) {
                actualDest = new OmopDestination(new EurekaOmopConfiguration((OmopDestinationEntity) dest, this.etlProperties));
            } else if (dest instanceof PhenotypeSearchDestinationEntity) {
                actualDest = new PhenotypeSearchDestination(new EurekaPhenotypeSearchConfiguration((PhenotypeSearchDestinationEntity)dest, this.etlProperties));
            } else if (dest instanceof CovidOmopDestinationEntity) {
                actualDest = new CovidOmopDestination(new CovidOmopConfiguration((CovidOmopDestinationEntity)dest, this.etlProperties));
            } else {
                throw new AssertionError("Invalid destination entity type " + dest.getClass());
            }

            if (dest.isDeidentificationEnabled()) {
                if (queryMode == QueryMode.REPLACE) {
                        this.deidPerPatientParamsDao.deleteAll(dest);
                }
                EurekaDeidConfig deidConfig = this.eurekaDeidConfigFactory.getInstance(dest);
                return new DestinationWithUnitOfWork(new DeidentifiedDestination(actualDest, deidConfig), this.unitOfWork);
            } else {
                return new DestinationWithUnitOfWork(actualDest, this.unitOfWork);
            }
        } catch (ConfigurationInitException ex) {
            throw new DestinationInitException(ex);
        }
    }

}
