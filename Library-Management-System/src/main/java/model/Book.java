package model;

import javafx.beans.property.SimpleStringProperty;

public class Book {  // Class name capitalized, and singular (Book)

    private SimpleStringProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty author;
    private SimpleStringProperty publisher;

    // Constructor name should be same as class name: Book
    public  Book(String id, String name, String author, String publisher) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.author = new SimpleStringProperty(author);
        this.publisher = new SimpleStringProperty(publisher);
    }

    public String getId() { return id.get(); }
    public String getName() { return name.get(); }
    public String getAuthor() { return author.get(); }
    public String getPublisher() { return publisher.get(); }
}
