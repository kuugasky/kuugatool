package io.github.kuugasky.design.behavior.iterator;

import io.github.kuugasky.design.behavior.iterator.core.Iterator;

import java.util.List;

/**
 * Main
 *
 * @author kuuga
 * @since 2023/6/19-06-19 14:46
 */
public class Main {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf();
        bookShelf.appendBook(new Book("Around the World in 80 Days"));
        bookShelf.appendBook(new Book("Bible"));
        bookShelf.appendBook(new Book("Cinderella"));
        bookShelf.appendBook(new Book("Daddy-Long-Legs"));

        Iterator it = bookShelf.iterator();
        while (it.hasNext()) {
            Book book = (Book) it.next();
            System.out.println(book.getName());
        }

        System.out.println("--------------------");

        List<Book> books = bookShelf.getBooks();
        for (Book book : books) {
            System.out.println(book.getName());
        }

    }
}
