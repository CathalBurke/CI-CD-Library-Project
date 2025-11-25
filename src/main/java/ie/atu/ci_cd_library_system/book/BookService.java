package ie.atu.ci_cd_library_system.book;

import jakarta.transaction.Transactional;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Transactional
    public Books buyBook(Long bookId) {
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Book out of stock");
        }
    }
}
