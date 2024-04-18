package test;

import database.DAOBook;
import model.Book;

public class TestDAOBook {
	public static void main(String[] args) {
		Book book = new Book();
		book.setName("ZERO TO ONE");
		book.setImageSrc("book4.png");
		book.setAuthor("Peter Thiel");
		book.setBookID("0804139298");
		book.setPublishDate(2014);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Money Master The Game");
		book.setImageSrc("book5.png");
		book.setAuthor("Tony Robbins");
		book.setBookID("1476757801");
		book.setPublishDate(2014);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("The 100$ Startup");
		book.setImageSrc("book6.png");
		book.setAuthor("Chris Guillebeau");
		book.setBookID("0307951529");
		book.setPublishDate(2012);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Cracking The Coding Interview");
		book.setImageSrc("book7.png");
		book.setAuthor("Gayle Laakmann McDowell");
		book.setBookID("0984782869");
		book.setPublishDate(2015);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Neon Genesis Evangelion , Vol. 3");
		book.setImageSrc("book8.png");
		book.setAuthor("Anno Hideaki & Yoshiyuki Sadamoto");
		book.setBookID("1421553627");
		book.setPublishDate(2013);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Bakemonogatari");
		book.setImageSrc("book9.png");
		book.setAuthor("Nisio Isin");
		book.setBookID("1647292972");
		book.setPublishDate(2024);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Java For Beginners Guide");
		book.setImageSrc("book10.png");
		book.setAuthor("Josh Thompsons");
		book.setBookID("1260463559");
		book.setPublishDate(2022);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Man's search for meaning");
		book.setImageSrc("book11.png");
		book.setAuthor("Viktor E. Frankl");
		book.setBookID("9780807014271");
		book.setPublishDate(2006);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("The Intelligent Investor");
		book.setImageSrc("book12.png");
		book.setAuthor("Benjamin Graham");
		book.setBookID("0805139298");
		book.setPublishDate(2014);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("The Art of War");
		book.setImageSrc("book13.png");
		book.setAuthor("Sun Tzu");
		book.setBookID("1599869772");
		book.setPublishDate(2007);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Sense And Sensibility");
		book.setImageSrc("book14.png");
		book.setAuthor("Jane Austen");
		book.setBookID("0141439661");
		book.setPublishDate(2003);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Great Expectations");
		book.setImageSrc("book15.png");
		book.setAuthor("Charles Dickens");
		book.setBookID(" 0593448235");
		book.setPublishDate(2024);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Jane Eyre");
		book.setImageSrc("book16.png");
		book.setAuthor("Charlotte Brontë");
		book.setBookID("9780141441146");
		book.setPublishDate(2006);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("The Canterville Ghost");
		book.setImageSrc("book17.png");
		book.setAuthor("Oscar Wilde");
		book.setBookID("B0C3XX1C4V");
		book.setPublishDate(2012);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Anna Karenina");
		book.setImageSrc("book18.png");
		book.setAuthor("Leo Tolstoy");
		book.setBookID("B0C3SJDLK8");
		book.setPublishDate(2024);
		DAOBook.getInstance().insert(book);
		
		book = new Book();
		book.setName("Re:ZERO, Vol. 2 - light novel (Re:ZERO -Starting Life in Another World-, 2)");
		book.setImageSrc("book19.png");
		book.setAuthor("Tappei Nagatsuki");
		book.setBookID("0316398373");
		book.setPublishDate(2016);
		DAOBook.getInstance().insert(book);
	}
}
