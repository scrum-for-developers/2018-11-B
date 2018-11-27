package de.codecentric.psd.worblehat.web.formdata;

import de.codecentric.psd.worblehat.web.validation.ISBN;
import de.codecentric.psd.worblehat.web.validation.Numeric;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class represent the form data of the add book form.
 */
public class BookDataFormData {

	@NotEmpty(message = "{empty.bookDataFormData.title}")
	private String title;

	@NotEmpty(message = "{empty.bookDataFormData.edition}")
	@Numeric(message = "{notvalid.bookDataFormData.edition}")
	private String edition;

	@NotEmpty(message = "{empty.bookDataFormData.yearOfPublication}")
	@Numeric(message = "{notvalid.bookDataFormData.yearOfPublication}")
	@Length(message = "{invalid.length.bookDataFormData.yearOfPublication}", min = 4, max = 4)
	private String yearOfPublication;

	@NotEmpty(message = "{empty.bookDataFormData.isbn}")
	@ISBN(message = "{notvalid.bookDataFormData.isbn}")
	private String isbn;

	@NotEmpty(message = "{empty.bookDataFormData.author}")
	private String author;

	private String description;


	public String getDescription() { return description; }

	public void setDescription(String description) { this.description = description; }

	public String getYearOfPublication() {
		return yearOfPublication;
	}

	public void setYearOfPublication(String yearOfPublication) {
		yearOfPublication = trimInput(yearOfPublication);
		this.yearOfPublication = yearOfPublication;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		isbn = trimInput(isbn.trim());
		this.isbn = isbn;
	}

	public String getAuthor() { return author; }

	public void setAuthor(String author) {
		author = trimInput(author);
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		title = trimInput(title);
		this.title = title;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		edition = trimInput(edition);
		this.edition = edition;
	}

	private String trimInput(String inputToTrim) {
		return inputToTrim.trim();
	}

	@Override
	public String toString() {
		return "BookDataFormData [title=" + title + ", edition=" + edition
				+ ", yearOfPublication=" + yearOfPublication + ", isbn=" + isbn + ", author=" + author
				+ ", description=" + description
				+ "]";
	}

}
