package io.github.kuugasky.design.behavior.iterator;

import io.github.kuugasky.design.behavior.iterator.core.Iterator;

/**
 * BookShelfIterator
 *
 * @author kuuga
 * @since 2023/6/19-06-19 14:44
 */
public class BookShelfIterator implements Iterator {

    private final BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    public boolean hasNext() {
        return index < bookShelf.getLength();
    }

    public Object next() {
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }

}
