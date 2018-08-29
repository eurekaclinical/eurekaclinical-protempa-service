package edu.emory.cci.aiw.cvrg.eureka.etl.entity;

/*-
 * #%L
 * Eureka! Clinical Protempa Service
 * %%
 * Copyright (C) 2012 - 2018 Emory University
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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.eurekaclinical.protempa.client.comm.IdPoolId;

/**
 *
 * @author Andrew Post
 */
@Entity
@Table(name="id_pool_ids")
public class IdPoolIdEntity implements org.eurekaclinical.standardapis.entity.Entity<Long> {
    @Id
    @SequenceGenerator(name = "ID_POOL_ID_SEQ_GENERATOR",
            sequenceName = "ID_POOL_ID_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "ID_POOL_ID_SEQ_GENERATOR")
    private Long id;
    
    private String fromId;
    
    private String description;
    
    @ManyToOne
    @JoinColumn(nullable=false)
    private IdPoolEntity idPool;
    
    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IdPoolEntity getIdPool() {
        return this.idPool;
    }

    public void setIdPool(IdPoolEntity idPool) {
        this.idPool = idPool;
    }
    
    public IdPoolId toIdPoolId() {
        IdPoolId idPoolId = new IdPoolId();
        idPoolId.setId(this.id);
        idPoolId.setFromId(this.fromId);
        idPoolId.setDescription(this.description);
        return idPoolId;
    }
    
}
