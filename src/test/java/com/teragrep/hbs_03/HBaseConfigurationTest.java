/*
 * Teragrep Metadata Using HBase (hbs_03)
 * Copyright (C) 2024 Suomen Kanuuna Oy
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 * Additional permission under GNU Affero General Public License version 3
 * section 7
 *
 * If you modify this Program, or any covered work, by linking or combining it
 * with other code, such other code is not for that reason alone subject to any
 * of the requirements of the GNU Affero GPL version 3 as long as this Program
 * is the same Program as licensed from Suomen Kanuuna Oy without any additional
 * modifications.
 *
 * Supplemented terms under GNU Affero General Public License version 3
 * section 7
 *
 * Origin of the software must be attributed to Suomen Kanuuna Oy. Any modified
 * versions must be marked as "Modified version of" The Program.
 *
 * Names of the licensors and authors may not be used for publicity purposes.
 *
 * No rights are granted for use of trade names, trademarks, or service marks
 * which are in The Program if any.
 *
 * Licensee must indemnify licensors and authors for any liability that these
 * contractual assumptions impose on licensors and authors.
 *
 * To the extent this program is licensed as part of the Commercial versions of
 * Teragrep, the applicable Commercial License may apply to this file if you as
 * a licensee so wish it.
 */
package com.teragrep.hbs_03;

import com.teragrep.cnf_01.Configuration;
import com.teragrep.cnf_01.PropertiesConfiguration;
import com.teragrep.hbs_03.hbase.HBaseConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public final class HBaseConfigurationTest {

    @Test
    public void testDefaultConfig() {
        final Properties props = new Properties();
        final Configuration configuration = new PropertiesConfiguration(props);
        final org.apache.hadoop.conf.Configuration hbaseConfig = new HBaseConfiguration(configuration, "prefix.")
                .config();
        Assertions.assertEquals("host_from_file", hbaseConfig.get("hbase.zookeeper.quorum"));
    }

    @Test
    public void testConfig() {
        final Properties props = new Properties();
        props.put("prefix.zookeeper.quorum", "testhost");
        final Configuration configuration = new PropertiesConfiguration(props);
        final org.apache.hadoop.conf.Configuration hbaseConfig = new HBaseConfiguration(configuration, "prefix.")
                .config();
        Assertions.assertEquals("testhost", hbaseConfig.get("zookeeper.quorum"));
    }

    @Test
    public void testConfigFromFile() {
        final Properties props = new Properties();
        props.put("prefix.config.path", "src/test/resources/hbase-site.xml");
        final Configuration configuration = new PropertiesConfiguration(props);
        final org.apache.hadoop.conf.Configuration hbaseConfig = new HBaseConfiguration(configuration, "prefix.")
                .config();
        Assertions.assertEquals("host_from_file", hbaseConfig.get("hbase.zookeeper.quorum"));
    }

    @Test
    public void testInvalidFilePath() {
        final Properties props = new Properties();
        props.put("prefix.config.path", "broken/path/");
        final Configuration configuration = new PropertiesConfiguration(props);
        final HbsRuntimeException ex = Assertions
                .assertThrows(HbsRuntimeException.class, () -> new HBaseConfiguration(configuration, "prefix.").config());
        final String expectedMessage = "Could not find a file in given file path (caused by: MalformedURLException: No file in path)";
        Assertions.assertEquals(expectedMessage, ex.getMessage());
    }
}
