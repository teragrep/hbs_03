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

import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableExistsException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.BufferedMutator;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptor;
import org.apache.hadoop.hbase.client.ColumnFamilyDescriptorBuilder;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.TableDescriptor;
import org.apache.hadoop.hbase.client.TableDescriptorBuilder;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class LogfileTable {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogfileTable.class);
    private final Connection connection;
    private final TableName tableName;

    public LogfileTable(final Connection connection) {
        this(connection, TableName.valueOf("logfile"));
    }

    public LogfileTable(final Connection connection, TableName tableName) {
        this.connection = connection;
        this.tableName = tableName;
    }

    public void create() {
        try (final Admin admin = connection.getAdmin()) {

            final TableDescriptor descriptor = TableDescriptorBuilder
                    .newBuilder(tableName)
                    .setColumnFamilies(columnFamilyDescriptors())
                    .build();
            admin.createTable(descriptor);

            LOGGER.info("Created Logfile table to HBase");

        }
        catch (final MasterNotRunningException e) {
            throw new RuntimeException("Master was not running: " + e.getMessage());
        }
        catch (final TableExistsException e) {
            throw new RuntimeException("Logfile table already exists: " + e.getMessage());
        }
        catch (final IllegalArgumentException e) {
            throw new RuntimeException(tableName + " restricted: " + e.getMessage());
        }
        catch (final IOException e) {
            throw new RuntimeException("Error creating logfile table: " + e.getMessage());
        }
    }

    public ResultScanner scan(final Scan scan) {
        try (final Table table = connection.getTable(tableName)) {
            final ResultScanner result = table.getScanner(scan);
            return result;
        }
        catch (IOException e) {
            throw new RuntimeException("Error writing files to table: " + e.getMessage());
        }
    }

    public void put(final Put put) {
        try (final Table table = connection.getTable(tableName)) {
            table.put(put);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Put <{}> into database", put);
            }
        }
        catch (final IOException e) {
            throw new RuntimeException("Error writing files to table: " + e.getMessage());
        }
    }

    public void putAll(final List<Put> puts) {
        try (final BufferedMutator mutator = connection.getBufferedMutator(tableName)) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Putting <{}> objects", puts.size());
            }
            mutator.mutate(puts);
            mutator.flush();
        }
        catch (final IOException e) {
            throw new RuntimeException("Error writing files to table: " + e.getMessage());
        }
    }

    private List<ColumnFamilyDescriptor> columnFamilyDescriptors() {
        return Collections
                .unmodifiableList(Arrays.asList(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("meta")).build(), ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("bloom")).build()));
    }
}
