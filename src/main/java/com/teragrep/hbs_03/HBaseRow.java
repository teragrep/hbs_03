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

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public final class HBaseRow implements Row {

    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseRow.class);

    private final List<Object> values;
    private final List<String> columns;
    private final RowKey rowKey;

    public HBaseRow(List<Object> values) {
        this(
                values,
                Arrays
                        .asList(
                                "epoch", "exp", "pth", "orig_nm", "archived", "checksum", "etag", "logtag",
                                "uncmp_size", "meta", "src_nm", "ctg_nm", "bckt_nm", "host_nm", "strm_dir", "strm_tag",
                                "log_grp_nm"
                        ),
                new RowKey(values.get(14).toString(), values.get(0).toString())
        );
    }

    private HBaseRow(List<Object> values, List<String> columns, RowKey rowKey) {
        this.values = values;
        this.columns = columns;
        this.rowKey = rowKey;
    }

    public Put put() {
        if (values.size() != columns.size()) {
            throw new IllegalStateException("Number of values does not match the number of columns");
        }
        // create put with a row key
        final Put put = new Put(rowKey.bytes());
        int i = 0;
        final byte[] metaCFBytes = Bytes.toBytes("meta");
        // iterate values and put to corresponding columns
        for (final Object value : values) {
            final String column = columns.get(i);
            put.addColumn(metaCFBytes, Bytes.toBytes(column), Bytes.toBytes((value.toString())));
            LOGGER.trace("Col: <{}>=<{}> ", column, value);
            i++;
        }
        return put;
    }

    public String id() {
        return rowKey.value();
    }
}
