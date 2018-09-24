package com.sundararaghavan.deliveryapp.persistence.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "repodb",
        indices = {@Index("id")})
public class RepoEntity {
    @PrimaryKey
    @NonNull
    private Integer id = 0;
    private String description;
    private String imageUrl;
    private String lat;
    private String lng;
    private String address;

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "{" +
                "\'id\'='" + id + '\'' +
                ", \'description\'='" + description + '\'' +
                ", \'imageUrl\'='" + imageUrl + '\'' +
                "\'location\':{" +
                ", \'lat\'='" + lat + '\'' +
                ", \'lng\'='" + lng + '\'' +
                ", \'address\'='" + address + '\'' +
                "}}";
    }
    /*
     *  {
     "id": 0,
     "description": "Deliver documents to Andrio",
     "imageUrl": "https://www.what-dog.net/Images/faces2/scroll0015.jpg",
     "location": {
     "lat": 22.336093,
     "lng": 114.155288,
     "address": "Cheung Sha Wan"
     }
     }
     */


}
