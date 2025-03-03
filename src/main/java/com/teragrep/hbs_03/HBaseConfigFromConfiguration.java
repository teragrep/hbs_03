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
import com.teragrep.cnf_01.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class HBaseConfigFromConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseConfigFromConfiguration.class);

    private final Configuration config;
    private final String prefix;

    public HBaseConfigFromConfiguration(final Configuration config) {
        this(config, "hbs_03.hadoop.");
    }

    public HBaseConfigFromConfiguration(final Configuration config, final String prefix) {
        this.config = config;
        this.prefix = prefix;
    }

    public org.apache.hadoop.conf.Configuration config() {
        final Map<String, String> map;
        try {
            map = config.asMap();
        }
        catch (final ConfigurationException e) {
            throw new HbsRuntimeException("Error getting configuration", e);
        }
        return hbaseConfigFromMap(map);
    }

    private org.apache.hadoop.conf.Configuration hbaseConfigFromMap(final Map<String, String> map) {

        final org.apache.hadoop.conf.Configuration hbaseConfig = new org.apache.hadoop.conf.Configuration();
        final String filePrefix = prefix + "config.path";

        for (final Map.Entry<String, String> entry : map.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            LOGGER.debug("key <{}>, value <{}>", key, value);

            if (key.matches(filePrefix)) { // from file
                final Path path = Paths.get(value);
                if (!Files.exists(path)) {
                    LOGGER.warn("Found no file in given path <{}>, no options were set", value);
                    throw new HbsRuntimeException(
                            "Could not find a file in given file path",
                            new MalformedURLException("No file in path")
                    );
                }
                else {
                    LOGGER.info("Loading options from file in path=<{}>", value);
                    try {
                        // checks the file system, not the class path
                        hbaseConfig.addResource(path.toAbsolutePath().toUri().toURL());
                    }
                    catch (final MalformedURLException e) {
                        throw new HbsRuntimeException("Error getting options file", e);
                    }
                }
            }
            else if (key.startsWith(prefix)) {
                final String hbaseOption = key.substring(prefix.length());
                LOGGER.info("Set HBase configuration option: <{}>=<{}>", hbaseOption, value);
                hbaseConfig.set(hbaseOption, value);
            }
        }

        // add default values if not set
        hbaseConfig.setIfUnset("hbase.zookeeper.quorum", "localhost"); // required for connection
        hbaseConfig.setIfUnset("hbase.zookeeper.property.clientProt", "2181"); // default zookeeper port

        hbaseConfig.setIfUnset("hbase.client.retries.number", "10"); // retries for failed request
        hbaseConfig.setIfUnset("hbase.client.pause", "150"); // pause between retries ms

        hbaseConfig.setIfUnset("hbase.client.scanner.timeout.period", "60000"); // scanner timeout ms
        hbaseConfig.setIfUnset("hbase.rpc.timeout", "60000"); // rpc timeout ms
        hbaseConfig.setIfUnset("hbase.client.operation.timeout", "60000"); // operation timeout ms
        hbaseConfig.setIfUnset("hbase.client.write.buffer", "2097152"); // write buffer size bytes

        hbaseConfig.setIfUnset("hbase.regionserver.durability", "SYNC_WAL"); // default safest data durability
        hbaseConfig.setIfUnset("hbase.rootdir", "file:///tmp/hbase"); // default to use local filesystem

        return hbaseConfig;
    }
}
