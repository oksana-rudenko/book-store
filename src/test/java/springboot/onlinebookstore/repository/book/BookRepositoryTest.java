package springboot.onlinebookstore.repository.book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import springboot.onlinebookstore.model.Book;
import springboot.onlinebookstore.model.Category;
import springboot.onlinebookstore.repository.category.CategoryRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("""
            Find all books by category id with pagination
            """)
    @Sql(scripts = {
            "classpath:database/books/remove-books-categories-from-table.sql",
            "classpath:database/books/remove-categories-from-category-table.sql",
            "classpath:database/books/remove-books-from-book-table.sql",
            "classpath:database/books/add-books-to-book-table.sql",
            "classpath:database/books/add-categories-to-category-table.sql",
            "classpath:database/books/add-category-to-book-in-book-category-table.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/books/remove-books-categories-from-table.sql",
            "classpath:database/books/remove-categories-from-category-table.sql",
            "classpath:database/books/remove-books-from-book-table.sql"
    },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findBooksByCategoryId_ExistingCategoryId_ReturnsList() {
        Category historyCategory = new Category();
        historyCategory.setId(1L);
        historyCategory.setName("History");
        historyCategory.setDescription("Europe History");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Bloodland");
        book1.setAuthor("Timothy Snyder");
        book1.setIsbn("978-1541600065");
        book1.setPrice(BigDecimal.valueOf(25.95));
        book1.setDescription("Europe between Hitler and Stalin");
        book1.setCoverImage("https://m.media-amazon.com/images/I/818gorntorL._SL1500_.jpg");
        book1.setDeleted(false);
        book1.setCategories(Set.of(historyCategory));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("The Red Prince");
        book2.setAuthor("Timothy Snyder");
        book2.setIsbn("978-1845951207");
        book2.setPrice(BigDecimal.valueOf(31.25));
        book2.setDescription("Life of Wilhelm von Habsburg, a Habsburg archduke");
        book2.setCoverImage("https://m.media-amazon.com/images/I/716rUFPputL._SL1360_.jpg");
        book2.setDeleted(false);
        book2.setCategories(Set.of(historyCategory));

        Book book3 = new Book();
        book3.setId(3L);
        book3.setTitle("The Gates of Europe");
        book3.setAuthor("Serhii Plohy");
        book3.setIsbn("978-0465094868");
        book3.setPrice(BigDecimal.valueOf(27.45));
        book3.setDescription("A History of Ukraine");
        book3.setCoverImage("https://m.media-amazon.com/images/I/812JAY5J35L._SL1500_.jpg");
        book3.setDeleted(false);
        book3.setCategories(Set.of(historyCategory));

        Pageable pageable = PageRequest.of(0, 10);

        List<Book> expected = new ArrayList<>();
        expected.add(book1);
        expected.add(book2);
        expected.add(book3);

        List<Book> actual = bookRepository.findBooksByCategoryId(1L, pageable);

        Assertions.assertEquals(3, actual.size());
        System.out.println(actual);
        System.out.println(expected);
        Assertions.assertEquals(expected, actual);

    }
}
