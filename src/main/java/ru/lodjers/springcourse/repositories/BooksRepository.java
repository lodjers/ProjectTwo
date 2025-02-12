package ru.lodjers.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lodjers.springcourse.models.Book;
import ru.lodjers.springcourse.models.Person;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByBookName(String bookName);

    List<Book> findByOwner(Person owner);
}
