package lesson7;

import lombok.Data;

@Data
@Table(name = "books")
public class Book {

    @TableField(sqlType = "INTEGER")
    private int id;

    @TableField(sqlType = "INTEGER")
    private int price;

    @TableField(sqlType = "TEXT")
    private String title;

    @TableField(sqlType = "TEXT")
    private String author;

    public Book(int id, int price, String title, String author) {
        this.id = id;
        this.price = price;
        this.title = title;
        this.author = author;
    }
}
