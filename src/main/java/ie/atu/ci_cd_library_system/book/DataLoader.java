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
             new Books(null,"WoK","BS","FS",3,3),
                new Books(null,"TW","AS","FS",2,2),
                new Books(null,"GoT","GRRM","FS",5,5),
                new Books(null,"HG","SC","YA",3,3)
        );
        for (Books book : sampleBooks) {
            bookRepository.save(book);
        }
        logger.info("Loading completed");
    }
}
