import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    private String ID;
    private String Author;
    private String Title;
    private String Genre;
    private double Price;
    private Date Publish_Date;
    private String Description;

    public Book() {
        this.ID="";
        this.Author="";
        this.Title="";
        this.Genre="";
        this.Price=0;
        this.Publish_Date= null;
        this.Description="";
    }

    public Book(String ID, String author, String title, String genre, double price, Date publish_Date, String description) {
        this.ID = ID;
        this.Author = author;
        this.Title = title;
        this.Genre = genre;
        this.Price = price;
        this.Publish_Date = publish_Date;
        this.Description = description;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public Date getPublish_Date() {
        return Publish_Date;
    }

    public void setPublish_Date(Date publish_Date) {
        Publish_Date = publish_Date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void Print_Book()
    {
        System.out.println("Book ID: "+this.ID);
        System.out.println("Author: "+this.Author);
        System.out.println("Title: "+this.Title);
        System.out.println("Genre: "+this.Genre);
        System.out.println("Price: "+this.Price);
        System.out.println("Publishing_Date: "+ new SimpleDateFormat("yyyy-MM-dd").format(Publish_Date));
        System.out.println("Description: "+this.Description);
    }
}

