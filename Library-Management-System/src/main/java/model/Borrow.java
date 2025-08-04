package model;

import java.time.LocalDate;

public class Borrow {
    private int id;
    private String studentName;
    private int bookid;  // String නම්, database field එක int නම් int කරන්න.
    private String description;
    private LocalDate dateBorrowed;
    private LocalDate dateReturned;

    // Constructor (optional)
    public Borrow() {
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateBorrowed() {
        return dateBorrowed;
    }

    public void setDateBorrowed(LocalDate dateBorrowed) {
        this.dateBorrowed = dateBorrowed;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }

    public void setBorrowedDate(LocalDate localDate) {
        this.dateBorrowed = localDate;
    }

    public void setReturnDate(LocalDate localDate) {
        this.dateReturned = localDate;
    }
}

