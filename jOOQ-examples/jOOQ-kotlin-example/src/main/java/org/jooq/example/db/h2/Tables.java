/*
 * This file is generated by jOOQ.
 */
package org.jooq.example.db.h2;


import javax.annotation.Generated;

import org.jooq.example.db.h2.tables.Author;
import org.jooq.example.db.h2.tables.Book;
import org.jooq.example.db.h2.tables.BookStore;
import org.jooq.example.db.h2.tables.BookToBookStore;


/**
 * Convenience access to all tables in PUBLIC
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

    /**
     * The table <code>PUBLIC.AUTHOR</code>.
     */
    public static final Author AUTHOR = org.jooq.example.db.h2.tables.Author.AUTHOR;

    /**
     * The table <code>PUBLIC.BOOK</code>.
     */
    public static final Book BOOK = org.jooq.example.db.h2.tables.Book.BOOK;

    /**
     * The table <code>PUBLIC.BOOK_STORE</code>.
     */
    public static final BookStore BOOK_STORE = org.jooq.example.db.h2.tables.BookStore.BOOK_STORE;

    /**
     * The table <code>PUBLIC.BOOK_TO_BOOK_STORE</code>.
     */
    public static final BookToBookStore BOOK_TO_BOOK_STORE = org.jooq.example.db.h2.tables.BookToBookStore.BOOK_TO_BOOK_STORE;
}
