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
package com.teragrep.hbs_03.hbase;

import com.teragrep.hbs_03.hbase.binary.Binary;
import com.teragrep.hbs_03.sql.MockS3MetaData;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class MetaRowTest {

    // SQL Mock
    final MockS3MetaData provider = new MockS3MetaData();
    final MockConnection connection = new MockConnection(provider);
    final DSLContext ctx = DSL.using(connection, SQLDialect.MYSQL);

    @Test
    public void testRowKey() {
        final Row row = new Row.FakeRow();
        final String expected = "RowKey(streamId=<123>, logtime=1285880400000, logfileId=123456789)\n"
                + " bytes=<[00 00 00 00 00 00 00 7b 23 7f ff fe d4 9b 8e 37 7f 23 00 00 00 00 07 5b cd 15]>";
        final Binary rowKey = row.rowKey();
        Assertions.assertEquals(expected, rowKey.toString());
    }

    @Test
    public void testPutQualifiers() {

        final Row row = new Row.FakeRow();
        final Put put = row.put();
        final byte[] columnFamily = Bytes.toBytes("meta");
        // test qualifiers
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("a")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("b")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("c")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("chk")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("d")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("e")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("et")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("fs")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("h")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("i")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("ld")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("lt")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("m")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("of")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("okh")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("p")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("s")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("sid")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("src")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("t")));
        Assertions.assertTrue(put.has(columnFamily, Bytes.toBytes("ufs")));
    }

    @Test
    public void testCorrectSize() {
        final Row row = new Row.FakeRow();
        final Put put = row.put();
        Assertions.assertTrue(put.getFamilyCellMap().containsKey(Bytes.toBytes("meta")));
        Assertions.assertEquals(21, put.getFamilyCellMap().get(Bytes.toBytes("meta")).size());
    }

    @Test
    public void testNullValue() {
        final Row row = new Row.FakeRow(null);
        final Put put = row.put();
        Assertions.assertTrue(put.getFamilyCellMap().containsKey(Bytes.toBytes("meta")));
        Assertions.assertEquals(21, put.getFamilyCellMap().get(Bytes.toBytes("meta")).size());
    }
}
