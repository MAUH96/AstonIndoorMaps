package com.example.astonindoor.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomWrapper {

        @SerializedName("rooms")
        private List<RoomModel> mData;
        @SerializedName("error")
        private Boolean mError;
        @SerializedName("message")
        private String mMessage;
        @SerializedName("status")
        private String mStatus;

        public List<RoomModel> getRoom() {
            return mData;
        }

        public void setUser(List<RoomModel> data) {
            mData = data;
        }

        public Boolean getError() {
            return mError;
        }

        public void setError(Boolean error) {
            mError = error;
        }

        public String getMessage() {
            return mMessage;
        }

        public void setMessage(String message) {
            mMessage = message;
        }

        public String getStatus() {
            return mStatus;
        }

        public void setStatus(String status) {
            mStatus = status;
        }

    }
