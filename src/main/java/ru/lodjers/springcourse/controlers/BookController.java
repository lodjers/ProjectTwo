package ru.lodjers.springcourse.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.lodjers.springcourse.models.Book;
import ru.lodjers.springcourse.models.Person;
import ru.lodjers.springcourse.services.BookService;
import ru.lodjers.springcourse.services.PeopleService;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "books/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) throws SQLException {
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("personToBook", bookService.checkBookToPerson(id));
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "books/new";

        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) throws SQLException {
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) throws SQLException {

        if (bindingResult.hasErrors()) {
            return "books/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) throws SQLException {
        bookService.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/add")
    public String add(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) throws SQLException {
        bookService.add(selectedPerson, id);
        return "redirect:/books/" + id;
    }
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.releaseBook(id);
        return "redirect:/books/" + id;
    }
}


