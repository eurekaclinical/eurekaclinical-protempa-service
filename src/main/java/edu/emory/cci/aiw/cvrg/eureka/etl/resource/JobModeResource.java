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
package edu.emory.cci.aiw.cvrg.eureka.etl.resource;

import com.google.inject.persist.Transactional;
import edu.emory.cci.aiw.cvrg.eureka.etl.dao.JobModeDao;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.JobModeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import org.eurekaclinical.common.resource.AbstractNamedReadOnlyResource;
import org.eurekaclinical.eureka.client.comm.JobMode;

@Transactional
@Path("/protected/jobmodes")
@Produces(MediaType.APPLICATION_JSON)
public class JobModeResource extends AbstractNamedReadOnlyResource<JobModeEntity, JobMode> {

    private final JobModeDao jobModeDao;

    @Inject
    public JobModeResource(JobModeDao inJobModeDao) {
        super(inJobModeDao);
        this.jobModeDao = inJobModeDao;
    }

    @GET
    @Override
    public List<JobMode> getAll(@Context HttpServletRequest req) {
        List<JobMode> result = new ArrayList<>();
        for (JobModeEntity jobModeEntity : this.jobModeDao.getAllAsc()) {
            result.add(toComm(jobModeEntity, req));
        }
        return result;
    }
    
    @Override
    protected JobMode toComm(JobModeEntity entity, HttpServletRequest req) {
        JobMode jobMode = new JobMode();
        jobMode.setId(entity.getId());
        jobMode.setName(entity.getName());
        jobMode.setDescription(entity.getDescription());
        jobMode.setRank(entity.getRank());
        jobMode.setDefault(entity.isDefault());
        return jobMode;
    }

    @Override
    protected boolean isAuthorizedEntity(JobModeEntity entity, HttpServletRequest req) {
        return true;
    }
}
