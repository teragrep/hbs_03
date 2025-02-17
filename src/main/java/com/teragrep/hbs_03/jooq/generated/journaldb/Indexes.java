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
 *//*
 * This file is generated by jOOQ.
 */
package com.teragrep.hbs_03.jooq.generated.journaldb;


import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Bucket;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Category;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Host;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Logfile;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.MetadataValue;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.SourceSystem;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>journaldb</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index BUCKET_PRIMARY = Indexes0.BUCKET_PRIMARY;
    public static final Index BUCKET_UIX_BUCKET_NAME = Indexes0.BUCKET_UIX_BUCKET_NAME;
    public static final Index CATEGORY_PRIMARY = Indexes0.CATEGORY_PRIMARY;
    public static final Index CATEGORY_UIX_CATEGORY_NAME = Indexes0.CATEGORY_UIX_CATEGORY_NAME;
    public static final Index HOST_PRIMARY = Indexes0.HOST_PRIMARY;
    public static final Index HOST_UIX_HOST_NAME = Indexes0.HOST_UIX_HOST_NAME;
    public static final Index LOGFILE_BUCKET_ID = Indexes0.LOGFILE_BUCKET_ID;
    public static final Index LOGFILE_CATEGORY_ID = Indexes0.LOGFILE_CATEGORY_ID;
    public static final Index LOGFILE_CIX_LOGFILE_LOGDATE_HOST_ID_LOGTAG = Indexes0.LOGFILE_CIX_LOGFILE_LOGDATE_HOST_ID_LOGTAG;
    public static final Index LOGFILE_HOST_ID = Indexes0.LOGFILE_HOST_ID;
    public static final Index LOGFILE_IX_LOGFILE_EXPIRATION = Indexes0.LOGFILE_IX_LOGFILE_EXPIRATION;
    public static final Index LOGFILE_IX_LOGFILE__SOURCE_SYSTEM_ID = Indexes0.LOGFILE_IX_LOGFILE__SOURCE_SYSTEM_ID;
    public static final Index LOGFILE_PRIMARY = Indexes0.LOGFILE_PRIMARY;
    public static final Index LOGFILE_UIX_LOGFILE_OBJECT_HASH = Indexes0.LOGFILE_UIX_LOGFILE_OBJECT_HASH;
    public static final Index METADATA_VALUE_LOGFILE_ID = Indexes0.METADATA_VALUE_LOGFILE_ID;
    public static final Index METADATA_VALUE_PRIMARY = Indexes0.METADATA_VALUE_PRIMARY;
    public static final Index SOURCE_SYSTEM_PRIMARY = Indexes0.SOURCE_SYSTEM_PRIMARY;
    public static final Index SOURCE_SYSTEM_UIX_SOURCE_SYSTEM_NAME = Indexes0.SOURCE_SYSTEM_UIX_SOURCE_SYSTEM_NAME;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index BUCKET_PRIMARY = Internal.createIndex("PRIMARY", Bucket.BUCKET, new OrderField[] { Bucket.BUCKET.ID }, true);
        public static Index BUCKET_UIX_BUCKET_NAME = Internal.createIndex("uix_bucket_name", Bucket.BUCKET, new OrderField[] { Bucket.BUCKET.NAME }, true);
        public static Index CATEGORY_PRIMARY = Internal.createIndex("PRIMARY", Category.CATEGORY, new OrderField[] { Category.CATEGORY.ID }, true);
        public static Index CATEGORY_UIX_CATEGORY_NAME = Internal.createIndex("uix_category_name", Category.CATEGORY, new OrderField[] { Category.CATEGORY.NAME }, true);
        public static Index HOST_PRIMARY = Internal.createIndex("PRIMARY", Host.HOST, new OrderField[] { Host.HOST.ID }, true);
        public static Index HOST_UIX_HOST_NAME = Internal.createIndex("uix_host_name", Host.HOST, new OrderField[] { Host.HOST.NAME }, true);
        public static Index LOGFILE_BUCKET_ID = Internal.createIndex("bucket_id", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.BUCKET_ID }, false);
        public static Index LOGFILE_CATEGORY_ID = Internal.createIndex("category_id", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.CATEGORY_ID }, false);
        public static Index LOGFILE_CIX_LOGFILE_LOGDATE_HOST_ID_LOGTAG = Internal.createIndex("cix_logfile_logdate_host_id_logtag", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.LOGDATE, Logfile.LOGFILE.HOST_ID, Logfile.LOGFILE.LOGTAG }, false);
        public static Index LOGFILE_HOST_ID = Internal.createIndex("host_id", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.HOST_ID }, false);
        public static Index LOGFILE_IX_LOGFILE_EXPIRATION = Internal.createIndex("ix_logfile_expiration", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.EXPIRATION }, false);
        public static Index LOGFILE_IX_LOGFILE__SOURCE_SYSTEM_ID = Internal.createIndex("ix_logfile__source_system_id", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.SOURCE_SYSTEM_ID }, false);
        public static Index LOGFILE_PRIMARY = Internal.createIndex("PRIMARY", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.ID }, true);
        public static Index LOGFILE_UIX_LOGFILE_OBJECT_HASH = Internal.createIndex("uix_logfile_object_hash", Logfile.LOGFILE, new OrderField[] { Logfile.LOGFILE.OBJECT_KEY_HASH }, true);
        public static Index METADATA_VALUE_LOGFILE_ID = Internal.createIndex("logfile_id", MetadataValue.METADATA_VALUE, new OrderField[] { MetadataValue.METADATA_VALUE.LOGFILE_ID }, false);
        public static Index METADATA_VALUE_PRIMARY = Internal.createIndex("PRIMARY", MetadataValue.METADATA_VALUE, new OrderField[] { MetadataValue.METADATA_VALUE.ID }, true);
        public static Index SOURCE_SYSTEM_PRIMARY = Internal.createIndex("PRIMARY", SourceSystem.SOURCE_SYSTEM, new OrderField[] { SourceSystem.SOURCE_SYSTEM.ID }, true);
        public static Index SOURCE_SYSTEM_UIX_SOURCE_SYSTEM_NAME = Internal.createIndex("uix_source_system_name", SourceSystem.SOURCE_SYSTEM, new OrderField[] { SourceSystem.SOURCE_SYSTEM.NAME }, true);
    }
}
