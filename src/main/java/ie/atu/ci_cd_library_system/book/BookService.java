package ie.atu.ci_cd_library_system.book;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    //transactional makes sure either all of it or nothing goes to database
    @Transactional
    public Books buyBook(String title) {
        Books book = bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Title does not exist"));

        if (book.getQuantity() > 0 && book.getMaxQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            book.setMaxQuantity(book.getMaxQuantity() -1);
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Book out of stock");
        }
    }

    @Transactional
    public Books rentBook(String title) {
        Books book = bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Book not available for rent");
        }
    }

    @Transactional
    public Books returnBook(String title) {
        Books book  = bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        if(book.getQuantity() >= book.getMaxQuantity()){
            throw new RuntimeException("All books have been returned");
        }
        book.setQuantity(book.getQuantity() + 1);
        return bookRepository.save(book);
    }
}
