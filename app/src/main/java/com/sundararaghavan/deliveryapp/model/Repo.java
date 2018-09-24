package com.sundararaghavan.deliveryapp.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Repo {
    public static Builder builder() {
        return new AutoValue_Repo.Builder();
    }

    public static JsonAdapter<Repo> jsonAdapter(Moshi moshi) {
        return new AutoValue_Repo.MoshiJsonAdapter(moshi);
    }

    /***
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
    public abstract Integer id();

    public abstract String description();

    public abstract String imageUrl();

    public abstract Location location();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(Integer value);

        public abstract Builder setImageUrl(String value);

        public abstract Builder setDescription(String value);

        public abstract Builder setLocation(Location value);

        public abstract Repo build();
    }

}
