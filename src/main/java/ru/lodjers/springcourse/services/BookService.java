package ru.lodjers.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lodjers.springcourse.models.Book;
import ru.lodjers.springcourse.models.Person;
import ru.lodjers.springcourse.repositories.BooksRepository;
import ru.lodjers.springcourse.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findByBookName(String bookName) {
        return booksRepository.findByBookName(bookName);
    }

    public List<Book> findByOwner(Person owner) {
        return booksRepository.findByOwner(owner);
    }
    public List<Book> findAll(boolean sort) {
        return sort ? booksRepository.findAll(Sort.by("year")) : booksRepository.findAll();
    }
    public List<Book> findAllWithPaginationAndSort(int page, int booksPerPage, boolean sort) {
        if (sort) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
        }
    }
    public void checkDates(List<Book> bookList) {
        Date now = new Date();
        for (Book book : bookList) {
            if (book.getTakenAt() != null) {
                int diffInDays = (int) (now.getTime() - book.getTakenAt().getTime()) / (1000 * 60 * 60 * 24);
                if (diffInDays > 10) {
                    book.setOverTime(true);
                } else book.setOverTime(false);
            } else {
                book.setOverTime(true);
            }
        }
    }
    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        return foundBook.orElse(null);
    }
    public Person checkBookToPerson(int id) {
        Book bookById = findOne(id);
        return bookById.getOwner();
    }
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void add(Person selectedPerson, int bookId) {

        Optional<Book> foundBook = booksRepository.findById(bookId);
        foundBook.get().setTakenAt(new Date());

        foundBook.get().setOwner(selectedPerson);
    }

    @Transactional
    public void releaseBook(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        foundBook.get().setTakenAt(null);
        foundBook.get().setOwner(null);
    }
    public List<Book> findBooks(String search) {
        return booksRepository.findByBookNameStartingWith(search);
    }
}
