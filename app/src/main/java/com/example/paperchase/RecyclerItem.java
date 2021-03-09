package com.example.paperchase;

public class RecyclerItem {
    private String itemName;
    private int mImageResource;

    public RecyclerItem(int imageResource, String item){
        itemName = item;
        mImageResource = imageResource;
    }

    public String getItem(){
        return itemName;
    }

    public int getImageResource(){ return mImageResource; }

    public void changeImage(int image){ mImageResource = image; }

    public void changeText(String text){itemName = text; }
}
