/*
 * This file is generated by jOOQ.
 */
package com.teragrep.hbs_03.jooq.generated.journaldb;


import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Bucket;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Category;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Host;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.Logfile;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.MetadataValue;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.SourceSystem;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.records.BucketRecord;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.records.CategoryRecord;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.records.HostRecord;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.records.LogfileRecord;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.records.MetadataValueRecord;
import com.teragrep.hbs_03.jooq.generated.journaldb.tables.records.SourceSystemRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;
import org.jooq.types.ULong;
import org.jooq.types.UShort;


/**
 * A class modelling foreign key relationships and constraints of tables of 
 * the <code>journaldb</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------

    public static final Identity<BucketRecord, UShort> IDENTITY_BUCKET = Identities0.IDENTITY_BUCKET;
    public static final Identity<CategoryRecord, UShort> IDENTITY_CATEGORY = Identities0.IDENTITY_CATEGORY;
    public static final Identity<HostRecord, UShort> IDENTITY_HOST = Identities0.IDENTITY_HOST;
    public static final Identity<LogfileRecord, ULong> IDENTITY_LOGFILE = Identities0.IDENTITY_LOGFILE;
    public static final Identity<MetadataValueRecord, ULong> IDENTITY_METADATA_VALUE = Identities0.IDENTITY_METADATA_VALUE;
    public static final Identity<SourceSystemRecord, UShort> IDENTITY_SOURCE_SYSTEM = Identities0.IDENTITY_SOURCE_SYSTEM;

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<BucketRecord> KEY_BUCKET_PRIMARY = UniqueKeys0.KEY_BUCKET_PRIMARY;
    public static final UniqueKey<BucketRecord> KEY_BUCKET_UIX_BUCKET_NAME = UniqueKeys0.KEY_BUCKET_UIX_BUCKET_NAME;
    public static final UniqueKey<CategoryRecord> KEY_CATEGORY_PRIMARY = UniqueKeys0.KEY_CATEGORY_PRIMARY;
    public static final UniqueKey<CategoryRecord> KEY_CATEGORY_UIX_CATEGORY_NAME = UniqueKeys0.KEY_CATEGORY_UIX_CATEGORY_NAME;
    public static final UniqueKey<HostRecord> KEY_HOST_PRIMARY = UniqueKeys0.KEY_HOST_PRIMARY;
    public static final UniqueKey<HostRecord> KEY_HOST_UIX_HOST_NAME = UniqueKeys0.KEY_HOST_UIX_HOST_NAME;
    public static final UniqueKey<LogfileRecord> KEY_LOGFILE_PRIMARY = UniqueKeys0.KEY_LOGFILE_PRIMARY;
    public static final UniqueKey<LogfileRecord> KEY_LOGFILE_UIX_LOGFILE_OBJECT_HASH = UniqueKeys0.KEY_LOGFILE_UIX_LOGFILE_OBJECT_HASH;
    public static final UniqueKey<MetadataValueRecord> KEY_METADATA_VALUE_PRIMARY = UniqueKeys0.KEY_METADATA_VALUE_PRIMARY;
    public static final UniqueKey<SourceSystemRecord> KEY_SOURCE_SYSTEM_PRIMARY = UniqueKeys0.KEY_SOURCE_SYSTEM_PRIMARY;
    public static final UniqueKey<SourceSystemRecord> KEY_SOURCE_SYSTEM_UIX_SOURCE_SYSTEM_NAME = UniqueKeys0.KEY_SOURCE_SYSTEM_UIX_SOURCE_SYSTEM_NAME;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<LogfileRecord, BucketRecord> LOGFILE_IBFK_1 = ForeignKeys0.LOGFILE_IBFK_1;
    public static final ForeignKey<LogfileRecord, HostRecord> LOGFILE_IBFK_2 = ForeignKeys0.LOGFILE_IBFK_2;
    public static final ForeignKey<LogfileRecord, SourceSystemRecord> FK_LOGFILE__SOURCE_SYSTEM_ID = ForeignKeys0.FK_LOGFILE__SOURCE_SYSTEM_ID;
    public static final ForeignKey<LogfileRecord, CategoryRecord> LOGFILE_IBFK_4 = ForeignKeys0.LOGFILE_IBFK_4;
    public static final ForeignKey<MetadataValueRecord, LogfileRecord> METADATA_VALUE_IBFK_1 = ForeignKeys0.METADATA_VALUE_IBFK_1;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Identities0 {
        public static Identity<BucketRecord, UShort> IDENTITY_BUCKET = Internal.createIdentity(Bucket.BUCKET, Bucket.BUCKET.ID);
        public static Identity<CategoryRecord, UShort> IDENTITY_CATEGORY = Internal.createIdentity(Category.CATEGORY, Category.CATEGORY.ID);
        public static Identity<HostRecord, UShort> IDENTITY_HOST = Internal.createIdentity(Host.HOST, Host.HOST.ID);
        public static Identity<LogfileRecord, ULong> IDENTITY_LOGFILE = Internal.createIdentity(Logfile.LOGFILE, Logfile.LOGFILE.ID);
        public static Identity<MetadataValueRecord, ULong> IDENTITY_METADATA_VALUE = Internal.createIdentity(MetadataValue.METADATA_VALUE, MetadataValue.METADATA_VALUE.ID);
        public static Identity<SourceSystemRecord, UShort> IDENTITY_SOURCE_SYSTEM = Internal.createIdentity(SourceSystem.SOURCE_SYSTEM, SourceSystem.SOURCE_SYSTEM.ID);
    }

    private static class UniqueKeys0 {
        public static final UniqueKey<BucketRecord> KEY_BUCKET_PRIMARY = Internal.createUniqueKey(Bucket.BUCKET, "KEY_bucket_PRIMARY", Bucket.BUCKET.ID);
        public static final UniqueKey<BucketRecord> KEY_BUCKET_UIX_BUCKET_NAME = Internal.createUniqueKey(Bucket.BUCKET, "KEY_bucket_uix_bucket_name", Bucket.BUCKET.NAME);
        public static final UniqueKey<CategoryRecord> KEY_CATEGORY_PRIMARY = Internal.createUniqueKey(Category.CATEGORY, "KEY_category_PRIMARY", Category.CATEGORY.ID);
        public static final UniqueKey<CategoryRecord> KEY_CATEGORY_UIX_CATEGORY_NAME = Internal.createUniqueKey(Category.CATEGORY, "KEY_category_uix_category_name", Category.CATEGORY.NAME);
        public static final UniqueKey<HostRecord> KEY_HOST_PRIMARY = Internal.createUniqueKey(Host.HOST, "KEY_host_PRIMARY", Host.HOST.ID);
        public static final UniqueKey<HostRecord> KEY_HOST_UIX_HOST_NAME = Internal.createUniqueKey(Host.HOST, "KEY_host_uix_host_name", Host.HOST.NAME);
        public static final UniqueKey<LogfileRecord> KEY_LOGFILE_PRIMARY = Internal.createUniqueKey(Logfile.LOGFILE, "KEY_logfile_PRIMARY", Logfile.LOGFILE.ID);
        public static final UniqueKey<LogfileRecord> KEY_LOGFILE_UIX_LOGFILE_OBJECT_HASH = Internal.createUniqueKey(Logfile.LOGFILE, "KEY_logfile_uix_logfile_object_hash", Logfile.LOGFILE.OBJECT_KEY_HASH);
        public static final UniqueKey<MetadataValueRecord> KEY_METADATA_VALUE_PRIMARY = Internal.createUniqueKey(MetadataValue.METADATA_VALUE, "KEY_metadata_value_PRIMARY", MetadataValue.METADATA_VALUE.ID);
        public static final UniqueKey<SourceSystemRecord> KEY_SOURCE_SYSTEM_PRIMARY = Internal.createUniqueKey(SourceSystem.SOURCE_SYSTEM, "KEY_source_system_PRIMARY", SourceSystem.SOURCE_SYSTEM.ID);
        public static final UniqueKey<SourceSystemRecord> KEY_SOURCE_SYSTEM_UIX_SOURCE_SYSTEM_NAME = Internal.createUniqueKey(SourceSystem.SOURCE_SYSTEM, "KEY_source_system_uix_source_system_name", SourceSystem.SOURCE_SYSTEM.NAME);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<LogfileRecord, BucketRecord> LOGFILE_IBFK_1 = Internal.createForeignKey(com.teragrep.hbs_03.jooq.generated.journaldb.Keys.KEY_BUCKET_PRIMARY, Logfile.LOGFILE, "logfile_ibfk_1", Logfile.LOGFILE.BUCKET_ID);
        public static final ForeignKey<LogfileRecord, HostRecord> LOGFILE_IBFK_2 = Internal.createForeignKey(com.teragrep.hbs_03.jooq.generated.journaldb.Keys.KEY_HOST_PRIMARY, Logfile.LOGFILE, "logfile_ibfk_2", Logfile.LOGFILE.HOST_ID);
        public static final ForeignKey<LogfileRecord, SourceSystemRecord> FK_LOGFILE__SOURCE_SYSTEM_ID = Internal.createForeignKey(com.teragrep.hbs_03.jooq.generated.journaldb.Keys.KEY_SOURCE_SYSTEM_PRIMARY, Logfile.LOGFILE, "fk_logfile__source_system_id", Logfile.LOGFILE.SOURCE_SYSTEM_ID);
        public static final ForeignKey<LogfileRecord, CategoryRecord> LOGFILE_IBFK_4 = Internal.createForeignKey(com.teragrep.hbs_03.jooq.generated.journaldb.Keys.KEY_CATEGORY_PRIMARY, Logfile.LOGFILE, "logfile_ibfk_4", Logfile.LOGFILE.CATEGORY_ID);
        public static final ForeignKey<MetadataValueRecord, LogfileRecord> METADATA_VALUE_IBFK_1 = Internal.createForeignKey(com.teragrep.hbs_03.jooq.generated.journaldb.Keys.KEY_LOGFILE_PRIMARY, MetadataValue.METADATA_VALUE, "metadata_value_ibfk_1", MetadataValue.METADATA_VALUE.LOGFILE_ID);
    }
}
