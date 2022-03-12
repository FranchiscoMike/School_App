package uz.pdp.school_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.school_app.entity.Book;
import uz.pdp.school_app.entity.Cart;
import uz.pdp.school_app.repository.BookRepository;
import uz.pdp.school_app.repository.CartRepository;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    final CartRepository cartRepository;
    final BookRepository bookRepository;


    @GetMapping
    public HttpEntity<?> all() {
        return ResponseEntity.ok(cartRepository.findAll());
    }

    @PostMapping
    public HttpEntity<?> addToCard(@RequestParam(name = "cart", defaultValue = "0") Integer cartId,
                                   @RequestParam(name = "book", defaultValue = "0") Integer bookId) {
        if (bookRepository.existsById(bookId)) {
            Book book = bookRepository.getById(bookId);

            if (cartRepository.existsById(cartId)) {
                Cart cart = cartRepository.getById(cartId);

                Set<Book> books = cart.getBooks();

                if (!books.add(book)) {
                    bookRepository.delete(book);
                    cart.setBooks(books);
                    cartRepository.save(cart);

                    return ResponseEntity.ok().body("successfully removed from the cart");
                }
                cart.setBooks(books);
                cartRepository.save(cart);
                return ResponseEntity.ok().body("successfully added to the cart");


            }
        }
        return ResponseEntity.notFound().build();
    }


}
