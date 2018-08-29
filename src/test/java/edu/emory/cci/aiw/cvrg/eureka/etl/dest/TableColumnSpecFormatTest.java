package edu.emory.cci.aiw.cvrg.eureka.etl.dest;

/*-
 * #%L
 * Eureka Protempa ETL
 * %%
 * Copyright (C) 2012 - 2016 Emory University
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
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.text.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.protempa.dest.table.ConstantColumnSpec;
import org.protempa.dest.table.FileTabularWriter;
import org.protempa.dest.table.TabularWriterException;

/**
 *
 * @author Andrew Post
 */
public class TableColumnSpecFormatTest {

    private TableColumnSpecFormat format;

    @Before
    public void setUp() {
        this.format = new TableColumnSpecFormat("FOO");
    }

    @Test
    public void testParseConstantNotNull() throws ParseException {
        Object parseObject = this.format.parseObject("bar");
        Assert.assertNotNull(parseObject);
    }

    @Test
    public void testParseConstantType() throws ParseException {
        Object parseObject = this.format.parseObject("bar");
        if (parseObject instanceof FileTableColumnSpecWrapper) {
            Assert.assertEquals(
                    ConstantColumnSpec.class.getName(),
                    ((FileTableColumnSpecWrapper) parseObject).getTableColumnSpec().getClass().getName());
        }
    }

    @Test
    public void testParseConstantBar() throws ParseException, TabularWriterException {
        Object parseObject = this.format.parseObject("bar");
        if (parseObject instanceof FileTableColumnSpecWrapper) {
            StringWriter sw = new StringWriter();
            try (FileTabularWriter ftw = new FileTabularWriter(new BufferedWriter(sw), '\t')) {
                ((FileTableColumnSpecWrapper) parseObject)
                        .getTableColumnSpec()
                        .columnValues("00001", null, null, null, null, null, ftw);
            }
            Assert.assertEquals("bar", sw.toString());
        }
    }
    
    @Test
    public void testParseObjectNotNull() throws ParseException {
        Object parseObject = this.format.parseObject("[PatientDetails Constant 0].patientId$NOMINALVALUE");
        Assert.assertNotNull(parseObject);
    }

    @Test
    public void testParseTableColumnSpecWrapper() throws ParseException {
        Object parseObject = this.format.parseObject("[PatientDetails Constant 0].patientId$NOMINALVALUE");
        if (parseObject != null) {
            Assert.assertEquals(FileTableColumnSpecWrapper.class.getName(), parseObject.getClass().getName());
        }
    }

    @Test
    public void testParseObjectPropId() throws ParseException {
        Object parseObject = this.format.parseObject("[PatientDetails Constant 0].patientId$NOMINALVALUE");
        if (parseObject != null && parseObject instanceof FileTableColumnSpecWrapper) {
            Assert.assertEquals("PatientDetails", ((FileTableColumnSpecWrapper) parseObject).getPropId());
        }
    }

    @Test
    public void testParseObjectPropIdWithColon() throws ParseException {
        Object parseObject = this.format.parseObject("[ICD9:Diagnoses Event 0].code$NOMINALVALUE");
        if (parseObject != null && parseObject instanceof FileTableColumnSpecWrapper) {
            Assert.assertEquals("ICD9:Diagnoses", ((FileTableColumnSpecWrapper) parseObject).getPropId());
        }
    }

}
