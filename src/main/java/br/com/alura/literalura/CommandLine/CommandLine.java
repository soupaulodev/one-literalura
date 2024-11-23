package br.com.alura.literalura.CommandLine;

import org.springframework.stereotype.Component;

import br.com.alura.literalura.ConsumerAPI.ConsumerAPI;
import br.com.alura.literalura.ConsumerAPI.DataConverter;
import br.com.alura.literalura.ConsumerAPI.ResponseAPI;
import br.com.alura.literalura.Model.Author;
import br.com.alura.literalura.Model.DataBook;
import br.com.alura.literalura.Model.Book;
import br.com.alura.literalura.Repository.AuthorRepository;
import br.com.alura.literalura.Repository.BookRepository;

import java.util.List;
import java.util.Scanner;

@Component
public class CommandLine {
    private static final String ADRESS = "https://gutendex.com/books/?search=";
    private ConsumerAPI consumer = new ConsumerAPI();
    private DataConverter converter = new DataConverter();
    private Scanner scan = new Scanner(System.in);
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public CommandLine(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - Find book by title
                    2 - List registered books
                    3 - List registered authors
                    4 - List living authors in a given year
                    5 - List books in a particular language
                    0 - Quit                                 
                    """;

            System.out.println(menu);
            option = scan.nextInt();
            scan.nextLine();

            switch (option) {
                case 1:
                    findBookByTitle();
                    break;
                case 2:
                    listRegisteredBooks();
                    break;
                case 3:
                    listRegisteredAuthors();
                    break;
                case 4:
                    listLivingAuthorsPerYear();
                    break;
                case 5:
                    listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("Quitting...");
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }
    
    private void findBookByTitle() {
        System.out.println("Enter the name of the book to search:");
        String bookName = scan.nextLine();

        var bookExistent = bookRepository.findByTitleContainingIgnoreCase(bookName);
        if (bookExistent.isPresent()) {
            System.out.println("Book found in the database:");
            System.out.println(bookExistent.get());
            return;
        }

        DataBook data = getDadosLivro(bookName);
        if (data == null) {
            System.out.println("Book not found.");
            return;
        }

        saveBook(data);
        System.out.println("Book saved in the database.");
        System.out.println(data);
    }

    private DataBook getDadosLivro(String bookName) {
        String adressSearch = ADRESS + bookName.replace(" ", "+");
        System.out.println(adressSearch);
        
        var json = consumer.obterDados(adressSearch);

        if (json.contains("{\"count\":0,\"next\":null,\"previous\":null,\"results\":[]}")) {
            return null;
        } else {
            ResponseAPI response = converter.getData(json, ResponseAPI.class);
            List<DataBook> books = response.getResults();

            if (!books.isEmpty()) {
                return books.get(0);
            } else {
                return null;
            }
        }
    }

    private void saveBook(DataBook dataBook) {
        Book book = new Book();
        book.setTitle(dataBook.title());
        book.setDownloads(dataBook.downloads());
        book.setLanguage(dataBook.languages().get(0));

        System.out.println(dataBook.authors().get(0).name());
        Author author ;
        var authorDb = authorRepository.findByName(dataBook.authors().get(0).name());

        if (authorDb.isPresent()) {
            author = authorDb.get();

        } else {
            author = new Author();
            author.setName(dataBook.authors().get(0).name());
            author.setYearBirth(dataBook.authors().get(0).birthYear());
            author.setYearDeath(dataBook.authors().get(0).deathYear());
            authorRepository.save(author);

       }
        book.setAuthor(author);
        bookRepository.save(book);
    }

    private void listRegisteredBooks () {
        var books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

    private void listRegisteredAuthors () {
        var authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    }

    private void listLivingAuthorsPerYear() {
        System.out.println("Enter the year you want to know if the author is alive:");
        var year = scan.nextInt();
        List<Author> livinAuthors =  authorRepository.findByDeathYearGreaterThan(year);
        livinAuthors.forEach(System.out::println);
    }

    private void listBooksByLanguage() {
        System.out.println("Enter the language you want to list the books in: Portuguese, English or Spanish:");
        var language = scan.nextLine();
       if (language.equals("Portuguese")){
           language = "pt";
       } else if(language.equals("English")) {
           language = "en";
       } else if(language.equals("Spanish")){
            language = "es";
        } else {
           System.out.println("Lingua not found.");
           return;
       }
        List<Book> bookByLanguage = bookRepository.findBooksByLanguage(language);
        bookByLanguage.forEach(System.out::println);
    }
}
