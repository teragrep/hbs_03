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
package com.teragrep.hbs_03.sql;

import org.jooq.CreateIndexIncludeStep;
import org.jooq.CreateTableColumnStep;
import org.jooq.DSLContext;
import org.jooq.DropTableStep;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.types.UInteger;
import org.jooq.types.UShort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.teragrep.hbs_03.jooq.generated.journaldb.Journaldb.JOURNALDB;
import static com.teragrep.hbs_03.jooq.generated.streamdb.Streamdb.STREAMDB;

public final class HostMappingTempTable {

    private static final Logger LOGGER = LoggerFactory.getLogger(HostMappingTempTable.class);

    private final DSLContext ctx;
    private final String name;

    public HostMappingTempTable(final DSLContext ctx) {
        this(ctx, "host_mapping_temp_table");
    }

    public HostMappingTempTable(final DSLContext ctx, final String name) {
        this.ctx = ctx;
        this.name = name;
    }

    private void dropIfExists() {
        try (final DropTableStep dropStep = ctx.dropTemporaryTableIfExists(name)) {
            dropStep.execute();
            LOGGER.info("Drop temp table <{}>", dropStep);
        }
    }

    public Table<Record> table() {
        return DSL.table(name);
    }

    public Field<UShort> hostIdField() {
        return DSL.field(DSL.name(name, "host_id"), UShort.class);
    }

    public Field<UInteger> groupIdField() {
        return DSL.field(DSL.name(name, "gid"), UInteger.class);
    }

    public void createIfNotExists() {
        try (
                final SelectSelectStep<Record2<UShort, UInteger>> selectStep = ctx
                        .select(JOURNALDB.HOST.ID.as(hostIdField()), STREAMDB.HOST.GID.as(groupIdField()));
                final SelectOnConditionStep<Record2<UShort, UInteger>> joinStep = selectStep.from(JOURNALDB.HOST).join(STREAMDB.HOST).on(JOURNALDB.HOST.NAME.eq(STREAMDB.HOST.NAME));

        ) {
            try (final CreateTableColumnStep createTempTable = ctx.createTemporaryTableIfNotExists(table())) {

                createTempTable.as(joinStep).execute();
                LOGGER.info("Create temp table <{}>", createTempTable);

                final CreateIndexIncludeStep hostIdIndex = ctx
                        .createUniqueIndexIfNotExists("ix_host_mapping_temp_table_host_id")
                        .on(table(), hostIdField());
                hostIdIndex.execute();
                LOGGER.debug("Create host_id index <{}>", hostIdIndex);

                final CreateIndexIncludeStep hostGidCompoundIndex = ctx
                        .createUniqueIndexIfNotExists("cix_host_mapping_temp_table_host_id_gid")
                        .on(table(), hostIdField(), groupIdField());

                hostGidCompoundIndex.execute();
                LOGGER.debug("Create compound index <{}>", hostGidCompoundIndex);
            }
        }

    }
}
