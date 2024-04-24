package com.example.zhanahope;
import static android.R.layout.simple_list_item_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import org.jetbrains.annotations.Contract;
import java.util.ArrayList;
import java.util.HashSet;


public class ChildHouse {
    public String name;
    public String address;
    public ArrayList<ImageView> photos;
    public String description;
    public String coordinates;
    public String nediness;
    public String phoneNumber;
    public String email;

    protected HashSet <String> children;

    private String verificationCode;



    protected ChildHouse(String name, String address, String coordinates, String verificationCode, String description, String nediness, String phoneNumber, String email){
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
        this.verificationCode = verificationCode;
        this.description = description;
        this.nediness = nediness;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    //Chidren access
    protected boolean addChild(String userId, @NonNull String verificationCode){
        if(verificationCode.equals(this.verificationCode)){
            children.add(userId);
            return true;
        }
        return false;
    }

    private void removeChild(String userId){
        children.remove(userId);
    }


    //-----------------------------------


    //Get Methods
    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCoordinates(){
        return coordinates;
    }

    @Contract
    private boolean changeVerificationCode(String newVerificationCode, @NonNull  String previousVerificationCode){
        if (previousVerificationCode.equals(verificationCode)) {
            verificationCode = newVerificationCode;
            return true;
        }
        return false;
    }


    //Users add photos
    protected void addPhotos(ImageView newPhoto, HorizontalScrollView houseImages, Context context) {
        photos.add(newPhoto);

        // Crop and resize the new photo to 100dp x 100dp
        Bitmap croppedBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background), 100, 100, true);

        // Create a new ImageView with the cropped Bitmap
        ImageView croppedImageView = null;
        croppedImageView.setImageBitmap(croppedBitmap);

        // Add the cropped photo to the HorizontalScrollView
        houseImages.addView(croppedImageView);
    }

    private void removePhoto(ImageView photo){
        photos.remove(photo);
    }

    private boolean changeDescription(String newDescription, String verificationCode){
        if(this.verificationCode.equals(verificationCode)){
            description = newDescription;
            return true;
        }
        return false;
    }


    void showInfoLayout(TextView houseAdress, TextView houseNidiness, TextView housePhoneNumber, TextView houseEmail){
        houseAdress.setText(address);
        houseNidiness.setText(nediness);
        housePhoneNumber.setText(phoneNumber);
        houseEmail.setText(email);
    }
}
