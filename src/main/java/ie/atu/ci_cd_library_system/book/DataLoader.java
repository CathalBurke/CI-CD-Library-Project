package ie.atu.ci_cd_library_system.book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component

public class DataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
    private final BookRepository bookRepository;
    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        logger.info("Loading Books");
        //sample books to test on instead of creating new books each time
        List<Books> sampleBooks = Arrays.asList(
                new Books(null,"The Way of Kings","Brandon Sanderson","Fantasy",3,24),
                new Books(null,"The Witcher","Andrzej Sapkowski","Fantasy",2,18),
                new Books(null,"Game of Thrones","George R.R. Martin","Fantasy",5,26),
                new Books(null,"The Hunger Games","Suzanne Collins","Fantasy",3,20),
                new Books(null,"1984","George Orwell","Dystopian",7,30),
                new Books(null,"The Martian","Andy Weir","Science Fiction",2,16),
                new Books(null,"The Shining","Stephen King","Horror",3,22),
                new Books(null,"To Kill a Mockingbird","Harper Lee","Classic",6,28)
        );
        for (Books book : sampleBooks) {
            bookRepository.save(book);
        }
        logger.info("Loading completed");
    }
}
