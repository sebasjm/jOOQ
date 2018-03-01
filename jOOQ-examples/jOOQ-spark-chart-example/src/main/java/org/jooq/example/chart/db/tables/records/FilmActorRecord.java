/**
 * This class is generated by jOOQ
 */
package org.jooq.example.chart.db.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.example.chart.db.tables.FilmActor;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0",
        "schema version:public_2",
    },
    date = "2016-06-30T15:44:15.143Z",
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class FilmActorRecord extends UpdatableRecordImpl<FilmActorRecord> implements Record3<Integer, Integer, Timestamp> {

    private static final long serialVersionUID = 1659900443;

    /**
     * Setter for <code>public.film_actor.actor_id</code>.
     */
    public void setActorId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.film_actor.actor_id</code>.
     */
    public Integer getActorId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.film_actor.film_id</code>.
     */
    public void setFilmId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.film_actor.film_id</code>.
     */
    public Integer getFilmId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>public.film_actor.last_update</code>.
     */
    public void setLastUpdate(Timestamp value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.film_actor.last_update</code>.
     */
    public Timestamp getLastUpdate() {
        return (Timestamp) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Timestamp> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row3<Integer, Integer, Timestamp> valuesRow() {
        return (Row3) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return FilmActor.FILM_ACTOR.ACTOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return FilmActor.FILM_ACTOR.FILM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field3() {
        return FilmActor.FILM_ACTOR.LAST_UPDATE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getActorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getFilmId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value3() {
        return getLastUpdate();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilmActorRecord value1(Integer value) {
        setActorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilmActorRecord value2(Integer value) {
        setFilmId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilmActorRecord value3(Timestamp value) {
        setLastUpdate(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FilmActorRecord values(Integer value1, Integer value2, Timestamp value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FilmActorRecord
     */
    public FilmActorRecord() {
        super(FilmActor.FILM_ACTOR);
    }

    /**
     * Create a detached, initialised FilmActorRecord
     */
    public FilmActorRecord(Integer actorId, Integer filmId, Timestamp lastUpdate) {
        super(FilmActor.FILM_ACTOR);

        set(0, actorId);
        set(1, filmId);
        set(2, lastUpdate);
    }
}
