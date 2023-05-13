package it.tn.spoilers.plugins.database

import it.tn.spoilers.database.models.Books
import it.tn.spoilers.database.models.BooksData


fun Books.toBooksData(): BooksData =
    BooksData(
        id = this.id.toString(),
        Book_ISBN = this.Book_ISBN,
        Book_Title = this.Book_Title,
        Book_Author = this.Book_Author,
        Book_Category = this.Book_Category,
        Book_Publishers = Book_Publishers,
        Book_School_Type = this.Book_School_Type,
        Book_SchoolCode = this.Book_SchoolCode,
        Book_Study_Year = this.Book_Study_Year,
        Book_YearSelection = this.Book_YearSelection
    )

fun BooksData.toBooks(): Books =
    Books(
        Book_ISBN = this.Book_ISBN,
        Book_Title = this.Book_Title,
        Book_Author = this.Book_Author,
        Book_Category = this.Book_Category,
        Book_Publishers = Book_Publishers,
        Book_School_Type = this.Book_School_Type,
        Book_SchoolCode = this.Book_SchoolCode,
        Book_Study_Year = this.Book_Study_Year,
        Book_YearSelection = this.Book_YearSelection
    )
