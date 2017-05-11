  # LibraryMgmt
Mybrary

PROJECT DESCRIPTION:

Mybrary is a simple library management system that allows users to add and check out books to users.

Features include local search capability, book status reports, book/customer creation and editing, and payment handling.

COOL FEATURES:
Mybrary uses Google Books API to auto-fill form fields when adding a new book. If a field is not found, the program notes the absence and asks for manual entry. The program is capable of creating multiple copies of the same book using a single ISBN. Each copy is assigned a unique MyBook ID number for tracking purposes.

The program also tracks overdue books and updates all overdue charges when the program is first opened, and displays a notification window if any updates were made. If a book is more than two weeks overdue, the book is automatically moved out of the active library and the customer associated with that book ID is charged the full Mybrary price (calculated at $0.04 per page). The "sold" record is then placed in an archive table in the library database for future reference. The program includes a "Reports" window where sold records can be brought up and viewed in detail.

Through a series of listeners, the program also prevents a book from being checked out more than once at the same time. Checkouts are for two weeks; renewals are for 7 days; then the book must be returned or it is considered overdue. A book cannot be checked out for another 14 days while in checked-out or renewal status; it must be fully checked in first. The program also prevents editing of due dates or which customer has a book once it is checked out.


KNOWN BUGS/PROBLEMS:
Beyond ISBN numbers or Credit Card numbers, data validation is not enforced in the program. A phone number could be entered as aaa-asdfasdfasdfasf and the program would track it as valid. The same holds true for names and email addresses. I have JFormattedTextFields in place for data validation, but I ran out of time and just had to get the program working.

The card-charging feature is not very efficient at present because it does not connect to any actual credit card services. The total amount a card has been charged is tracked in each card object. The amount due on each book is tracked in the book object. When checked in (or sold), the amount owed on the book is transferred to the card value. It's not too sophisticated, but it works.

