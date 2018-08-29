package edu.emory.cci.aiw.cvrg.eureka.etl.dest;

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

import edu.emory.cci.aiw.cvrg.eureka.etl.pool.Pool;
import edu.emory.cci.aiw.cvrg.eureka.etl.pool.PoolException;
import java.text.Format;
import org.protempa.dest.table.TabularWriter;
import org.protempa.dest.table.TabularWriterException;
import org.protempa.proposition.Parameter;
import org.protempa.proposition.Proposition;
import org.protempa.proposition.TemporalProposition;
import org.protempa.proposition.value.BooleanValue;
import org.protempa.proposition.value.DateValue;
import org.protempa.proposition.value.InequalityNumberValue;
import org.protempa.proposition.value.NominalValue;
import org.protempa.proposition.value.NumberValue;
import org.protempa.proposition.value.Value;

/**
 *
 * @author Andrew Post
 */
public class TabularWriterWithPool implements TabularWriter {

    private TabularWriter tabularWriter;
    private final Pool pool;

    public TabularWriterWithPool(Pool inPool) {
        this.pool = inPool;
    }

    public TabularWriter getTabularWriter() {
        return tabularWriter;
    }

    public void setTabularWriter(TabularWriter tabularWriter) {
        this.tabularWriter = tabularWriter;
    }

    @Override
    public void writeNominal(NominalValue inValue) throws TabularWriterException {
        this.tabularWriter.writeNominal(inValue);
    }

    @Override
    public void writeNominal(NominalValue inValue, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeNominal(inValue, inFormat);
    }

    @Override
    public void writeNumber(NumberValue inValue) throws TabularWriterException {
        this.tabularWriter.writeNumber(inValue);
    }

    @Override
    public void writeNumber(NumberValue inValue, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeNumber(inValue, inFormat);
    }

    @Override
    public void writeInequality(InequalityNumberValue inValue) throws TabularWriterException {
        this.tabularWriter.writeInequality(inValue);
    }

    @Override
    public void writeInequality(InequalityNumberValue inValue, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeInequality(inValue, inFormat);
    }

    @Override
    public void writeNumber(InequalityNumberValue inValue) throws TabularWriterException {
        this.tabularWriter.writeNumber(inValue);
    }

    @Override
    public void writeNumber(InequalityNumberValue inValue, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeNumber(inValue, inFormat);
    }

    @Override
    public void writeInequalityNumber(InequalityNumberValue inValue) throws TabularWriterException {
        this.tabularWriter.writeInequalityNumber(inValue);
    }

    @Override
    public void writeInequalityNumber(InequalityNumberValue inValue, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeInequalityNumber(inValue, inFormat);
    }

    @Override
    public void writeDate(DateValue inValue) throws TabularWriterException {
        this.tabularWriter.writeDate(inValue);
    }

    @Override
    public void writeDate(DateValue inValue, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeDate(inValue, inFormat);
    }

    @Override
    public void writeBoolean(BooleanValue inValue) throws TabularWriterException {
        this.tabularWriter.writeBoolean(inValue);
    }

    @Override
    public void writeBoolean(BooleanValue inValue, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeBoolean(inValue, inFormat);
    }

    @Override
    public void writeId(Proposition inProposition) throws TabularWriterException {
        this.tabularWriter.writeId(inProposition);
    }

    @Override
    public void writeUniqueId(Proposition inProposition) throws TabularWriterException {
        this.tabularWriter.writeUniqueId(inProposition);
    }

    @Override
    public void writeLocalUniqueId(Proposition inProposition) throws TabularWriterException {
        this.tabularWriter.writeLocalUniqueId(inProposition);
    }

    @Override
    public void writeNumericalId(Proposition inProposition) throws TabularWriterException {
        this.tabularWriter.writeNumericalId(inProposition);
    }

    @Override
    public void writeStart(TemporalProposition inProposition) throws TabularWriterException {
        this.tabularWriter.writeStart(inProposition);
    }

    @Override
    public void writeStart(TemporalProposition inProposition, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeStart(inProposition, inFormat);
    }

    @Override
    public void writeFinish(TemporalProposition inProposition) throws TabularWriterException {
        this.tabularWriter.writeFinish(inProposition);
    }

    @Override
    public void writeFinish(TemporalProposition inProposition, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeFinish(inProposition, inFormat);
    }

    @Override
    public void writeLength(TemporalProposition inProposition) throws TabularWriterException {
        this.tabularWriter.writeLength(inProposition);
    }

    @Override
    public void writeLength(TemporalProposition inProposition, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeLength(inProposition, inFormat);
    }

    @Override
    public void writeParameterValue(Parameter inProposition) throws TabularWriterException {
        this.tabularWriter.writeParameterValue(inProposition);
    }

    @Override
    public void writeParameterValue(Parameter inProposition, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writeParameterValue(inProposition, inFormat);
    }

    @Override
    public void writePropertyValue(Proposition inProposition, String inPropertyName) throws TabularWriterException {
        this.tabularWriter.writePropertyValue(inProposition, inPropertyName);
    }

    @Override
    public void writePropertyValue(Proposition inProposition, String inPropertyName, Format inFormat) throws TabularWriterException {
        this.tabularWriter.writePropertyValue(inProposition, inPropertyName, inFormat);
    }

    @Override
    public void writeNull() throws TabularWriterException {
        this.tabularWriter.writeNull();
    }

    @Override
    public void newRow() throws TabularWriterException {
        this.tabularWriter.newRow();
    }

    @Override
    public void close() throws TabularWriterException {
        this.tabularWriter.close();
    }

    @Override
    public void writeValue(Value inValue) throws TabularWriterException {
        try {
            this.tabularWriter.writeValue(this.pool != null ? this.pool.valueFor(inValue) : inValue);
        } catch (PoolException ex) {
            throw new TabularWriterException(ex);
        }
    }

    @Override
    public void writeValue(Value inValue, Format inFormat) throws TabularWriterException {
        try {
            this.tabularWriter.writeValue(this.pool != null ? this.pool.valueFor(inValue) : inValue, inFormat);
        } catch (PoolException ex) {
            throw new TabularWriterException(ex);
        }
    }

}
