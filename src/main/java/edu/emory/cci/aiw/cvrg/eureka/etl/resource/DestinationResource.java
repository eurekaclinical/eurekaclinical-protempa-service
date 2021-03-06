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

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import org.eurekaclinical.eureka.client.comm.DestinationType;
import org.eurekaclinical.protempa.client.comm.EtlDestination;
import edu.emory.cci.aiw.cvrg.eureka.etl.entity.AuthorizedUserEntity;
import edu.emory.cci.aiw.cvrg.eureka.etl.config.EtlProperties;
import edu.emory.cci.aiw.cvrg.eureka.etl.conversion.ConversionSupport;
import edu.emory.cci.aiw.cvrg.eureka.etl.conversion.DestinationToEtlDestinationVisitor;
import edu.emory.cci.aiw.cvrg.eureka.etl.conversion.EtlDestinationToDestinationVisitor;
import edu.emory.cci.aiw.cvrg.eureka.etl.dao.DestinationDao;
import edu.emory.cci.aiw.cvrg.eureka.etl.dao.EtlGroupDao;
import edu.emory.cci.aiw.cvrg.eureka.etl.dao.AuthorizedUserDao;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eurekaclinical.common.auth.AuthorizedUserSupport;
import org.eurekaclinical.eureka.client.comm.Destination;
import org.eurekaclinical.standardapis.exception.HttpStatusException;

@Transactional
@Path("/protected/destinations")
@RolesAllowed({"researcher"})
@Consumes(MediaType.APPLICATION_JSON)
public class DestinationResource {

    private final EtlProperties etlProperties;
    private final AuthorizedUserDao userDao;
    private final DestinationDao destinationDao;
    private final AuthorizedUserSupport<AuthorizedUserEntity, AuthorizedUserDao, ?> authenticationSupport;
    private final EtlGroupDao groupDao;
    private final EtlDestinationToDestinationEntityVisitor destToDestEntityVisitor;
    private final ConversionSupport conversionSupport;
    private static final Logger LOGGER = Logger.getLogger(DestinationResource.class.getName());

    @Inject
    public DestinationResource(EtlProperties inEtlProperties, AuthorizedUserDao inEtlUserDao, DestinationDao inDestinationDao, EtlGroupDao inGroupDao, EtlDestinationToDestinationEntityVisitor inDestToDestEntityVisitor, ConversionSupport inConversionSupport) {
        this.etlProperties = inEtlProperties;
        this.userDao = inEtlUserDao;
        this.destinationDao = inDestinationDao;
        this.authenticationSupport = new AuthorizedUserSupport<>(this.userDao);
        this.groupDao = inGroupDao;
        this.destToDestEntityVisitor = inDestToDestEntityVisitor;
        this.conversionSupport = inConversionSupport;
    }

    @POST
    public Response create(@Context HttpServletRequest request, Destination inDestination) {
        AuthorizedUserEntity user = this.authenticationSupport.getUser(request);
        Destinations destinations = new Destinations(this.etlProperties, user, this.destinationDao, this.groupDao, this.destToDestEntityVisitor);
        DestinationToEtlDestinationVisitor v
                        = new DestinationToEtlDestinationVisitor(this.conversionSupport);
        inDestination.accept(v);
        EtlDestination etlDestination = v.getEtlDestination();
        Long destId = destinations.create(etlDestination);
        return Response.created(URI.create("/" + destId)).build();
    }

    @PUT
    public void update(@Context HttpServletRequest request, Destination inDestination) {
        AuthorizedUserEntity user = this.authenticationSupport.getUser(request);
        DestinationToEtlDestinationVisitor v
				= new DestinationToEtlDestinationVisitor(this.conversionSupport);
        inDestination.accept(v);
        EtlDestination etlDestination = v.getEtlDestination();
        new Destinations(this.etlProperties, user, this.destinationDao, this.groupDao, this.destToDestEntityVisitor).update(etlDestination);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Destination> getAll(
            @Context HttpServletRequest request,
            @QueryParam("type") DestinationType type) {
        AuthorizedUserEntity user = this.authenticationSupport.getUser(request);
        LOGGER.log(Level.FINEST, "Inside DestinationResource, user: {0}", user.getUsername());
        Destinations destinations = new Destinations(this.etlProperties, user, this.destinationDao, this.groupDao, this.destToDestEntityVisitor);
        List<? extends EtlDestination> etlDestinations;
        if (type == null) {
            etlDestinations= destinations.getAll();
        }
        else {
            switch (type) {
                case I2B2:
                    etlDestinations = new ArrayList<>(destinations.getAllI2B2s());
                    break;
                case COHORT:
                    etlDestinations = new ArrayList<>(destinations.getAllCohorts());
                    break;
                case PATIENT_SET_EXTRACTOR:
                    etlDestinations = new ArrayList<>(destinations.getAllPatientSetExtractors());
                    break;
                case PATIENT_SET_SENDER:
                    etlDestinations = new ArrayList<>(destinations.getAllPatientSetSenders());
                    break;
                case TABULAR_FILE:
                    etlDestinations = new ArrayList<>(destinations.getAllTabularFiles());
                    break;
                case AOU_PARTICIPANT:
                    etlDestinations = new ArrayList<>(destinations.getAllOmopDestinations());
                    break;
                case PHENOTYPE_SEARCH:
                    etlDestinations = new ArrayList<>(destinations.getAllPhenotypeSearchDestinations());
                    break;
                default:
                    throw new AssertionError("Unexpected destination type " + type);
            }
        }
        
        List<Destination> result;
        result = new ArrayList<>(etlDestinations.size());
        LOGGER.log(Level.FINEST, "Inside DestinationResource, got ETL destinations: {0}", etlDestinations == null ? 0: etlDestinations.size());
        EtlDestinationToDestinationVisitor v
                        = new EtlDestinationToDestinationVisitor(this.conversionSupport);
        for (EtlDestination etlDest : etlDestinations) {
                etlDest.accept(v);
                result.add(v.getDestination());
        }
        return result;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{destId}")
    public Destination getDestination(
            @Context HttpServletRequest request,
            @PathParam("destId") String destId) {
        AuthorizedUserEntity user = this.authenticationSupport.getUser(request);
        EtlDestination result
                = new Destinations(this.etlProperties, user, this.destinationDao, this.groupDao, this.destToDestEntityVisitor).getOne(destId);
        EtlDestinationToDestinationVisitor v
				= new EtlDestinationToDestinationVisitor(this.conversionSupport);
        if (result != null) {
            result.accept(v);
            return v.getDestination();
        } else {
            throw new HttpStatusException(Status.NOT_FOUND);
        }
    }    

    @DELETE
    @Path("/{destId}")
    public void delete(@Context HttpServletRequest request,
            @PathParam("destId") String destId) {
        AuthorizedUserEntity user = this.authenticationSupport.getUser(request);
        new Destinations(this.etlProperties, user, this.destinationDao, this.groupDao, this.destToDestEntityVisitor).delete(destId);
    }

}
