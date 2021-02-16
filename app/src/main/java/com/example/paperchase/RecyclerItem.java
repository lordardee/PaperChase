package com.example.paperchase;

public class RecyclerItem {
    private String itemName;

    public void changeText(String text){
        itemName = text;
    }

    public RecyclerItem(String item){
        itemName = item;
    }

    public String getItem(){
        return itemName;
    }
}
