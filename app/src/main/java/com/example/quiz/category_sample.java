package com.example.quiz;

public class category_sample {
    private String categoryID,categoryName,categoryImage;
      //constructor//
    public category_sample(String categoryID,String categoryName, String categoryImage) {
        this.categoryName = categoryName;
        this.categoryImage = categoryImage;
        this.categoryID = categoryID;
    }

    //empty-constructor firebase//
    public category_sample(){}

    //get and set//
    public String getCategoryID(){
        return categoryID;
    }
    public void setCategoryID(String categoryID){
        this.categoryID=categoryID;
    }
    public String getCategoryName(){
        return categoryName;
    }
    public void setCategoryName(String categoryName){
        this.categoryName=categoryName;
    }

    public String getCategoryImage() {
        return categoryImage;
    }
    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
