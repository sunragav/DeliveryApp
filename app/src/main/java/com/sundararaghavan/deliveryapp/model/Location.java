package com.sundararaghavan.deliveryapp.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Location {
    public static Location.Builder builder() {
        return new AutoValue_Location.Builder();
    }

    public static JsonAdapter<Location> jsonAdapter(Moshi moshi) {
        return new AutoValue_Location.MoshiJsonAdapter(moshi);
    }

    /**
     * "location": {
     * "lat": 22.336093,
     * "lng": 114.155288,
     * "address": "Cheung Sha Wan"
     * }
     */

    public abstract String lat();

    public abstract String lng();

    public abstract String address();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Location.Builder setLat(String value);

        public abstract Location.Builder setLng(String value);

        public abstract Location.Builder setAddress(String value);

        public abstract Location build();
    }
}
