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

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.testing.TestingHBaseCluster;
import org.apache.hadoop.hbase.testing.TestingHBaseClusterOption;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.stream.Collectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogfileTableTest {

    private final String username = "streamdb";
    private final String password = "streamdb_pass";
    private final String url = "jdbc:mariadb://192.168.49.2:31420/archiver_journal_tyrael";

    private TestingHBaseCluster hbase;
    private Admin admin;
    private org.apache.hadoop.hbase.client.Connection hbaseConn;

    @BeforeAll
    public void setup() {
        Assertions.assertDoesNotThrow(() -> {
            hbase = TestingHBaseCluster.create(TestingHBaseClusterOption.builder().build());
            hbase.start();
            hbaseConn = ConnectionFactory.createConnection(hbase.getConf());
            admin = hbaseConn.getAdmin();
        });
    }

    @AfterAll
    public void tearDown() {
        Assertions.assertDoesNotThrow(() -> {
            admin.close();
            hbaseConn.close();
            hbase.stop();
        });
    }

    @Test
    @Disabled("Cluster setup not working")
    public void testCreate() {
        Assertions.assertDoesNotThrow(() -> new LogfileTable(hbaseConn).create());
        Assertions.assertDoesNotThrow(() -> {
            final boolean exists = admin.tableExists(TableName.valueOf("logfile"));
            Assertions.assertTrue(exists);
        });
    }

    @Test
    @Disabled("Cluster setup not working")
    @EnabledIfSystemProperty(
            named = "database.tests.enabled",
            matches = "true"
    )
    public void testPut() {
        final LogfileTable table = Assertions.assertDoesNotThrow(() -> new LogfileTable(hbaseConn));
        Assertions.assertDoesNotThrow(table::create);

        final List<Put> putList = Assertions.assertDoesNotThrow(() -> {
            final Connection conn = DriverManager.getConnection(url, username, password);
            final SQLQuery query = new SQLQuery(conn, "archiver_streamdb_tyrael", 10);
            return query.rows().stream().map(Row::put).collect(Collectors.toList());
        });

        Assertions.assertEquals(10, putList.size());
        table.putAll(putList);
        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes("meta"));
        ResultScanner scanner = table.scan(scan);
        int loops = 0;
        for (Result result : scanner) {
            System.out.println("HBase row result: " + result);
            loops++;
        }
        scanner.close();
        Assertions.assertEquals(10, loops);
    }
}
