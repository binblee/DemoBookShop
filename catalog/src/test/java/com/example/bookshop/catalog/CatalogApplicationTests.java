package com.example.bookshop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.bookshop.catalog.domain.Book;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void contextLoads() {
		assert true;
	}

	@Test
	void whenPostRequestThenBookCreated() {
		var expectedBook = Book.of("9783161484100", "The Catcher in the Rye", "J.D. Salinger", 8.99, null);
		webTestClient.post().uri("/books")
				.bodyValue(expectedBook)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(actualBook -> {
					assertThat(actualBook).isNotNull();
					assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn());
					assertThat(actualBook.title()).isEqualTo(expectedBook.title());
					assertThat(actualBook.author()).isEqualTo(expectedBook.author());
					assertThat(actualBook.price()).isEqualTo(expectedBook.price());
					assertThat(actualBook.publisher()).isEqualTo(expectedBook.publisher());	
				});
	}

	@SuppressWarnings("null")
	@Test
	void whenGetRequestWithIdThenBookReturned(){
		var isbn = "9783161484101";
		var bookToCreate = Book.of(isbn, "The Catcher in the Rye", "J.D. Salinger", 8.99,"Publisher");
		Book expectedBook = webTestClient.post().uri("/books")
				.bodyValue(bookToCreate)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(book -> assertThat(book).isNotNull())
				.returnResult().getResponseBody();
		webTestClient.get().uri("/books/{isbn}", isbn)
				.exchange().expectStatus().is2xxSuccessful()
				.expectBody(Book.class).value(book -> {
					assertThat(book).isNotNull();
					assertThat(book.isbn()).isEqualTo(expectedBook.isbn());
					assertThat(book.title()).isEqualTo(expectedBook.title());
					assertThat(book.author()).isEqualTo(expectedBook.author());
					assertThat(book.price()).isEqualTo(expectedBook.price());
					assertThat(book.publisher()).isEqualTo(expectedBook.publisher());
				});
	}

	@Test
	void whenGetRequestWithNonExistingIdThenNotFound(){
		String isbn = "9783161484102";
		webTestClient.get().uri("/books/{isbn}", isbn)
				.exchange().expectStatus().isNotFound();
	}

	@SuppressWarnings("null")
	@Test
	void whenPutRequestThenBookUpdated(){
		String isbn = "9783161484103";
		Book bookToCreate = Book.of(isbn, "The Catcher in the Rye", "J.D. Salinger", 8.99,"");
		Book expectedBook = webTestClient.post().uri("/books")
				.bodyValue(bookToCreate)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(book -> assertThat(book).isNotNull())
				.returnResult().getResponseBody();
		Book bookToUpdate = Book.of(isbn, "The Catcher in the Rye", "J.D. Salinger", 9.99, "");
		webTestClient.put().uri("/books/{isbn}", isbn)
				.bodyValue(bookToUpdate)
				.exchange().expectStatus().is2xxSuccessful()
				.expectBody(Book.class).value(book -> {
					assertThat(book).isNotNull();
					assertThat(book.isbn()).isEqualTo(expectedBook.isbn());
					assertThat(book.title()).isEqualTo(expectedBook.title());
					assertThat(book.author()).isEqualTo(expectedBook.author());
					assertThat(book.price()).isEqualTo(bookToUpdate.price());
					assertThat(book.publisher()).isEqualTo(expectedBook.publisher());
				});
	}

	@Test
	void whenDeleteRequestThenBookDeleted(){
		String isbn = "9783161484104";
		Book bookToCreate = Book.of(isbn, "The Catcher in the Rye", "J.D. Salinger", 8.99, null);
		webTestClient.post().uri("/books")
				.bodyValue(bookToCreate)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(Book.class).value(book -> assertThat(book).isNotNull());
		webTestClient.delete().uri("/books/{isbn}", isbn)
				.exchange().expectStatus().isNoContent();
		webTestClient.get().uri("/books/{isbn}", isbn)
				.exchange().expectStatus().isNotFound();
	}

}
