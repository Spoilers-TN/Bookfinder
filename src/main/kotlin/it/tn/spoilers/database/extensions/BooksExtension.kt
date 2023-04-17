package it.tn.spoilers.plugins.database

import it.tn.spoilers.database.models.Books
import it.tn.spoilers.database.models.BooksData


fun Books.toBooksData(): BooksData =
    BooksData(
        id = this.id.toString(),
        Book_ISBN = this.Book_ISBN,
        Book_Title = this.Book_Title,
        Book_Author = this.Book_Author,
        Book_Publishers = this.Book_Publishers,
        Book_Year = this.Book_Year,
        Book_Digital = this.Book_Digital,
        Book_Edition = this.Book_Edition,
        Book_Study_Year = this.Book_Study_Year,
        Book_Price = this.Book_Price,
        Book_Category = this.Book_Category
    )

fun BooksData.toBooks(): Books =
    Books(
        Book_ISBN = this.Book_ISBN,
        Book_Title = this.Book_Title,
        Book_Author = this.Book_Author,
        Book_Publishers = this.Book_Publishers,
        Book_Year = this.Book_Year,
        Book_Digital = this.Book_Digital,
        Book_Edition = this.Book_Edition,
        Book_Study_Year = this.Book_Study_Year,
        Book_Price = this.Book_Price,
        Book_Category = this.Book_Category
    )