/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.core.metadata.datasource.dialect;

import org.apache.shardingsphere.spi.database.UnrecognizedDatabaseURLException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OracleDataSourceMetaDataTest {
    
    @Test
    public void assertGetALLProperties() {
        OracleDataSourceMetaData actual = new OracleDataSourceMetaData("jdbc:oracle:thin:@//127.0.0.1:9999/ds_0");
        assertThat(actual.getHostName(), is("127.0.0.1"));
        assertThat(actual.getPort(), is(9999));
        assertThat(actual.getSchemaName(), is("ds_0"));
    }
    
    @Test
    public void assertGetALLPropertiesWithDefaultPort() {
        OracleDataSourceMetaData actual = new OracleDataSourceMetaData("jdbc:oracle:thin:@//127.0.0.1/ds_0");
        assertThat(actual.getHostName(), is("127.0.0.1"));
        assertThat(actual.getPort(), is(1521));
        assertThat(actual.getSchemaName(), is("ds_0"));
    }
    
    @Test
    public void assertGetPropertiesWithMinus() {
        OracleDataSourceMetaData actual = new OracleDataSourceMetaData("jdbc:oracle:thin:@//host-0/ds-0");
        assertThat(actual.getHostName(), is("host-0"));
        assertThat(actual.getPort(), is(1521));
        assertThat(actual.getSchemaName(), is("ds-0"));
    }
    
    @Test(expected = UnrecognizedDatabaseURLException.class)
    public void assertGetALLPropertiesFailure() {
        new OracleDataSourceMetaData("jdbc:oracle:xxxxxxxx");
    }
    
    @Test
    public void assertIsInSameDatabaseInstance() {
        OracleDataSourceMetaData target = new OracleDataSourceMetaData("jdbc:oracle:thin:@//127.0.0.1/ds_0");
        OracleDataSourceMetaData actual = new OracleDataSourceMetaData("jdbc:oracle:thin:@//127.0.0.1:1521/ds_0");
        assertThat(actual.isInSameDatabaseInstance(target), is(true));
    }
}
