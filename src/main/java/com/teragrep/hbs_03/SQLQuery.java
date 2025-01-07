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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLQuery {

    private final ValidName tableName;
    private final long limit;
    private final Connection connection;

    public SQLQuery(Connection connection) {
        this(connection, new ValidName("streamdb"), 0);
    }

    public SQLQuery(Connection connection, long limit) {
        this(connection, new ValidName("streamdb"), limit);
    }

    public SQLQuery(Connection connection, String tableName) {
        this(connection, new ValidName(tableName), 0);
    }

    public SQLQuery(Connection connection, String tableName, long limit) {
        this(connection, new ValidName(tableName), limit);
    }

    public SQLQuery(Connection connection, ValidName tableName, long limit) {
        this.tableName = tableName;
        this.limit = limit;
        this.connection = connection;
    }

    public List<Row> rows() {
        final List<Row> rows = new ArrayList<>();
        try (ResultSet resultSet = result()) {
            while (resultSet.next()) {
                final String id = resultSet.getString(1);
                final String epoch = resultSet.getString(2);
                final String exp = resultSet.getString(3);
                final String pth = resultSet.getString(4);
                final String orig_nn = resultSet.getString(5);
                final String archived = resultSet.getString(6);
                final String checksum = resultSet.getString(7);
                final String etag = resultSet.getString(8);
                final String logtag = resultSet.getString(9);
                final String uncmp_size = resultSet.getString(10);
                final String meta = resultSet.getString(11);
                final String src_nm = resultSet.getString(12);
                final String ctg_nm = resultSet.getString(13);
                final String bckt_nm = resultSet.getString(14);
                final String host_nm = resultSet.getString(15);
                final String strm_dir = resultSet.getString(16);
                final String strm_tag = resultSet.getString(17);
                final String log_grp_nm = resultSet.getString(18);
                final List<Object> values = Arrays
                        .asList(
                                id, epoch, exp, pth, orig_nn, archived, checksum, etag, logtag, uncmp_size, meta,
                                src_nm, ctg_nm, bckt_nm, host_nm, strm_dir, strm_tag, log_grp_nm
                        );
                final Row row = new SQLRow(values);
                rows.add(row);
            }
        }
        catch (final SQLException e) {
            throw new RuntimeException("Error getting rows :" + e.getMessage());
        }
        return rows;
    }

    private ResultSet result() {
        try (final PreparedStatement stmt = connection.prepareStatement(sql())) {
            return stmt.executeQuery();
        }
        catch (final SQLException e) {
            throw new RuntimeException("Error executing query: " + e.getMessage());
        }
    }

    private String sql() {
        final String sql;
        if (limit > 0) {
            sql = MessageFormat
                    .format(
                            "SELECT " + "`logfile`.`id` AS `id`, UNIX_TIMESTAMP(`logfile`.`logdate`) AS `epoch`,"
                                    + "    `logfile`.`expiration` AS `exp`," + "    `logfile`.`path` AS `pth`,"
                                    + "    `logfile`.`original_filename` AS `orig_nm`,"
                                    + "    UNIX_TIMESTAMP(`logfile`.`archived`) AS `archived`,"
                                    + "    `logfile`.`sha256_checksum` AS `checksum`,"
                                    + "    `logfile`.`archive_etag` AS `etag`," + "    `logfile`.`logtag` AS `logtag`,"
                                    + "    `logfile`.`uncompressed_file_size` AS `uncomp_sz`,"
                                    + "    JSON_OBJECTAGG(`metadata_value`.`value_key`, `metadata_value`.`value`) AS `meta`,"
                                    + "    `source_system`.`name` AS `source_nm`,"
                                    + "    `category`.`name` AS `ctg_nm`," + "    `bucket`.`name` AS `bckt_nm`,"
                                    + "    `host`.`name` AS `host_nm`,"
                                    + "    `{0}`.`stream`.`directory` AS `strm_dir`,"
                                    + "    `{0}`.`stream`.`tag` AS `strm_tag`,"
                                    + "    `{0}`.`log_group`.`name` AS `log_grp_nm`" + "FROM " + "    `logfile`"
                                    + "LEFT JOIN "
                                    + "    `metadata_value` ON `logfile`.`id` = `metadata_value`.`logfile_id`"
                                    + "LEFT JOIN "
                                    + "    `source_system` ON `logfile`.`source_system_id` = `source_system`.`id`"
                                    + "LEFT JOIN " + "    `category` ON `logfile`.`category_id` = `category`.`id`"
                                    + "LEFT JOIN " + "    `bucket` ON `logfile`.`bucket_id` = `bucket`.`id`"
                                    + "LEFT JOIN " + "    `host` ON `logfile`.`host_id` = `host`.`id`" + "LEFT JOIN "
                                    + "    `{0}`.`host` AS `stream_host` ON `host`.`name` = `stream_host`.`name`"
                                    + "LEFT JOIN "
                                    + "    `{0}`.`log_group` AS `log_group` ON `stream_host`.`gid` = `log_group`.`id`"
                                    + "LEFT JOIN "
                                    + "    `{0}`.`stream` AS `stream` ON `log_group`.`id` = `stream`.`gid`"
                                    + "GROUP BY " + "    `logfile`.`id`" + "LIMIT {1};",
                            tableName.name(), limit
                    );
        }
        else {
            sql = MessageFormat
                    .format(
                            "SELECT " + "`logfile`.`id` AS `id`, UNIX_TIMESTAMP(`logfile`.`logdate`) AS `epoch`,"
                                    + "    `logfile`.`expiration` AS `exp`," + "    `logfile`.`path` AS `pth`,"
                                    + "    `logfile`.`original_filename` AS `orig_nm`,"
                                    + "    UNIX_TIMESTAMP(`logfile`.`archived`) AS `archived`,"
                                    + "    `logfile`.`sha256_checksum` AS `checksum`,"
                                    + "    `logfile`.`archive_etag` AS `etag`," + "    `logfile`.`logtag` AS `logtag`,"
                                    + "    `logfile`.`uncompressed_file_size` AS `uncomp_sz`,"
                                    + "    JSON_OBJECTAGG(`metadata_value`.`value_key`, `metadata_value`.`value`) AS `meta`,"
                                    + "    `source_system`.`name` AS `source_nm`,"
                                    + "    `category`.`name` AS `ctg_nm`," + "    `bucket`.`name` AS `bckt_nm`,"
                                    + "    `host`.`name` AS `host_nm`,"
                                    + "    `{0}`.`stream`.`directory` AS `strm_dir`,"
                                    + "    `{0}`.`stream`.`tag` AS `strm_tag`,"
                                    + "    `{0}`.`log_group`.`name` AS `log_grp_nm`" + "FROM " + "    `logfile`"
                                    + "LEFT JOIN "
                                    + "    `metadata_value` ON `logfile`.`id` = `metadata_value`.`logfile_id`"
                                    + "LEFT JOIN "
                                    + "    `source_system` ON `logfile`.`source_system_id` = `source_system`.`id`"
                                    + "LEFT JOIN " + "    `category` ON `logfile`.`category_id` = `category`.`id`"
                                    + "LEFT JOIN" + "    `bucket` ON `logfile`.`bucket_id` = `bucket`.`id`"
                                    + "LEFT JOIN" + "    `host` ON `logfile`.`host_id` = `host`.`id`" + "LEFT JOIN"
                                    + "    `{0}`.`host` AS `stream_host` ON `host`.`name` = `stream_host`.`name`"
                                    + "LEFT JOIN"
                                    + "    `{0}`.`log_group` AS `log_group` ON `stream_host`.`gid` = `log_group`.`id`"
                                    + "LEFT JOIN"
                                    + "    `{0}`.`stream` AS `stream` ON `log_group`.`id` = `stream`.`gid`" + "GROUP BY"
                                    + "    `logfile`.`id`;",
                            tableName.name()
                    );
        }
        return sql;
    }
}
