package guru.springframework.spring5webapp.bootstrap;

import guru.springframework.spring5webapp.model.Author;
import guru.springframework.spring5webapp.model.Book;
import guru.springframework.spring5webapp.model.Publisher;
import guru.springframework.spring5webapp.repositories.AuthorRepository;
import guru.springframework.spring5webapp.repositories.BookRepository;
import guru.springframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository,
                         BookRepository bookRepository,
                         PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Started in Bootstrap");

        Publisher publisher = new Publisher("SFG Publishing",
                "123 Some Str",
                "St Petersburg",
                "FL",
                "55555");

        publisherRepository.save(publisher);

        System.out.println("Publisher count: " + publisherRepository.count());

        System.out.println("New Book Count: " + addBook(
                "Domain Driven Design",
                "123123",
                "Eric",
                "Evens",
                publisher
        ));

        System.out.println("New Book Count: " + addBook(
                "J2EE Development without EJB",
                "61465468",
                "Rod",
                "Johnson",
                publisher
        ));

        System.out.println("Number of Books: " + bookRepository.count());

    }

    public long addBook(String bookName,
                       String bookIsbn,
                       String authorFirstName,
                       String authorLastName,
                       Publisher publisher) {
        Author author = new Author(authorFirstName, authorLastName);
        Book book = new Book(bookName, bookIsbn);
        author.getBooks().add(book);
        book.getAuthors().add(author);
        publisher.getBooks().add(book);
        book.setPublisher(publisher);

        authorRepository.save(author);
        bookRepository.save(book);
        publisherRepository.save(publisher);

        return bookRepository.count();
    }

}
