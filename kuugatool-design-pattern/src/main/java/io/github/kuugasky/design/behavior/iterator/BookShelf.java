package io.github.kuugasky.design.behavior.iterator;

import io.github.kuugasky.design.behavior.iterator.core.Aggregate;
import io.github.kuugasky.design.behavior.iterator.core.Iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Bookshelf
 * <p>
 * 元素集合，实现了Aggregate接口，内聚了Book元素集合，重写iterator方法，返回一个实现了Iterator接口的书架迭代类，以供调用方进行迭代处理。
 *
 * @author kuuga
 * @since 2023/6/19-06-19 14:42
 */
public class BookShelf implements Aggregate {

    private final List<Book> books;

    /**
     * 注意：使用迭代器模式的时候，不能开放此方法让调用方直接获取books集合对象
     *
     * @return List
     */
    public List<Book> getBooks() {
        return this.books;
    }

    public BookShelf() {
        this.books = new ArrayList<>();
    }

    public Book getBookAt(int index) {
        return books.get(index);
    }

    public void appendBook(Book book) {
        books.add(book);
    }

    public int getLength() {
        return books.size();
    }

    @Override
    public Iterator iterator() {
        return new BookShelfIterator(this);
    }

}
