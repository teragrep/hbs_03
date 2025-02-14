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

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.JSON;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.impl.DSL;
import org.jooq.types.ULong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;

import static com.teragrep.hbs_03.jooq.generated.journaldb.Journaldb.JOURNALDB;
import static com.teragrep.hbs_03.jooq.generated.journaldb.Tables.LOGFILE;

public final class LogfileTableDayQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogfileTableDayQuery.class);

    private final DSLContext ctx;
    private final String name;
    private final Condition dateCondition;

    public LogfileTableDayQuery(final DSLContext ctx, final Date day) {
        this(ctx, "inner_table", day);
    }

    public LogfileTableDayQuery(final DSLContext ctx, final String name, final Date day) {
        this(ctx, name, LOGFILE.LOGDATE.eq(DSL.date(day)));
    }

    public LogfileTableDayQuery(final DSLContext ctx, final String name, final Condition dateCondition) {
        this.ctx = ctx;
        this.name = name;
        this.dateCondition = dateCondition;
    }

    private Table<Record> table() {
        return DSL.table(DSL.name(name));
    }

    public TableLike<Record1<ULong>> toTableStatement() {
        LOGGER.debug("Select from logfile where <{}>", dateCondition);
         return ctx.select(
                        JOURNALDB.LOGFILE.ID
                )
                .from(JOURNALDB.LOGFILE)
                .where(dateCondition)
                .asTable(table());
    }

    public Field<ULong> idField() {
        return DSL.field(DSL.name(table().getName(), "id"), ULong.class);
    }
}
