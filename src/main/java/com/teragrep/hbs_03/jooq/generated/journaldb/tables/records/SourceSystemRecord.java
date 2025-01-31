/*
 * This file is generated by jOOQ.
 */
package com.teragrep.hbs_03.jooq.generated.journaldb.tables.records;


import com.teragrep.hbs_03.jooq.generated.journaldb.tables.SourceSystem;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UShort;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.4"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SourceSystemRecord extends UpdatableRecordImpl<SourceSystemRecord> implements Record2<UShort, String> {

    private static final long serialVersionUID = 1190089057;

    /**
     * Setter for <code>journaldb.source_system.id</code>.
     */
    public void setId(UShort value) {
        set(0, value);
    }

    /**
     * Getter for <code>journaldb.source_system.id</code>.
     */
    public UShort getId() {
        return (UShort) get(0);
    }

    /**
     * Setter for <code>journaldb.source_system.name</code>. Source system's name
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>journaldb.source_system.name</code>. Source system's name
     */
    public String getName() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<UShort> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<UShort, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<UShort, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<UShort> field1() {
        return SourceSystem.SOURCE_SYSTEM.ID;
    }

    @Override
    public Field<String> field2() {
        return SourceSystem.SOURCE_SYSTEM.NAME;
    }

    @Override
    public UShort component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public UShort value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public SourceSystemRecord value1(UShort value) {
        setId(value);
        return this;
    }

    @Override
    public SourceSystemRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public SourceSystemRecord values(UShort value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SourceSystemRecord
     */
    public SourceSystemRecord() {
        super(SourceSystem.SOURCE_SYSTEM);
    }

    /**
     * Create a detached, initialised SourceSystemRecord
     */
    public SourceSystemRecord(UShort id, String name) {
        super(SourceSystem.SOURCE_SYSTEM);

        set(0, id);
        set(1, name);
    }
}
