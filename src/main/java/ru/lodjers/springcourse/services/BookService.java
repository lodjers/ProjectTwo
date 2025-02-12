package ru.lodjers.springcourse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lodjers.springcourse.models.Book;
import ru.lodjers.springcourse.models.Person;
import ru.lodjers.springcourse.repositories.BooksRepository;
import ru.lodjers.springcourse.repositories.PeopleRepository;

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
    public List<Book> findAll() {
        return booksRepository.findAll();
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

        foundBook.get().setOwner(selectedPerson);
    }

    @Transactional
    public void releaseBook(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);
        foundBook.get().setOwner(null);
    }
}
