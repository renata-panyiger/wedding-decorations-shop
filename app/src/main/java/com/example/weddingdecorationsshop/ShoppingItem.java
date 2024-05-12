package com.example.weddingdecorationsshop;

public class ShoppingItem {
    private String id;
    private String name;
    private String description;
    private String price;
    private float ratedInfo;
    private int imageResource;
    private int cartedCount;

    public ShoppingItem() {
    }

    public ShoppingItem(String name, String description, String price, float ratedInfo, int imageResource, int cartedCount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ratedInfo = ratedInfo;
        this.imageResource = imageResource;
        this.cartedCount = cartedCount;
    }

    public String getName() {return name;}

    public String getDescription() {return description;}

    public String getPrice() {return price;}

    public float getRatedInfo() {return ratedInfo;}

    public int getImageResource() {return imageResource;}

    public int getCartedCount() {return cartedCount;}

    public String _getId() {return id;}

    public void setId(String id) {this.id = id;}
}
