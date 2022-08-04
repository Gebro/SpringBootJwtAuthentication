package com.bass.jwtauthentication.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity(name = "order")
@Table(name="orders")
public class Order {
    //define fields
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")

    private int id;

    @Column(name="title")
    private String title;

    @Column(name="date")
    private String date;

    @Column(name="amount")
    private String amount;

    @Column(name="total_price")
    private int total_price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //define constructors
    public Order() {

    }
    public Order( String title, String date, String amount, int total_price) {
        this.title = title;
        this.date = date;
        this.amount = amount;
        this.total_price = total_price;
    }

    //define getter/setter

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public int getTotal_price() {
        return total_price;
    }
    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    //define tostring
    @Override
    public String toString() {
        return "Order [id=" + id + ", title=" + title + ", date=" + date + ", amount=" + amount + ", total_price="
                + total_price + "]";
    }






}
