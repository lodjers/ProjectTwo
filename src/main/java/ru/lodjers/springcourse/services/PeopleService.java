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
@Transactional(readOnly = true)
public class PeopleService{

    private final PeopleRepository peopleRepository;
    private final BooksRepository booksRepository;

    private final BookService bookService;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, BooksRepository booksRepository, BookService bookService) {
        this.peopleRepository = peopleRepository;
        this.booksRepository = booksRepository;
        this.bookService = bookService;

    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {

        Optional<Person> foundPerson = peopleRepository.findById(id);

        return foundPerson.orElse(null);
    }
    @Transactional
    public void save(Person person) {

        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
    public List<Book> booksOfPerson(Person owner) {
        bookService.checkDates(booksRepository.findByOwner(owner));
        return booksRepository.findByOwner(owner);
    }
}
