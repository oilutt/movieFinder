package oilutt.sambatechproject.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Configuration implements Parcelable {

    @SerializedName("change_keys")
    private List<String> changeKeys;
    @SerializedName("images")
    private ConfigImages configImages;

    public Configuration() {
    }

    public List<String> getChangeKeys() {
        return changeKeys;
    }

    public void setChangeKeys(List<String> changeKeys) {
        this.changeKeys = changeKeys;
    }

    public ConfigImages getConfigImages() {
        return configImages;
    }

    public void setConfigImages(ConfigImages configImages) {
        this.configImages = configImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.changeKeys);
        dest.writeParcelable(this.configImages, flags);
    }

    protected Configuration(Parcel in) {
        this.changeKeys = in.createStringArrayList();
        this.configImages = in.readParcelable(ConfigImages.class.getClassLoader());
    }

    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel source) {
            return new Configuration(source);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };

    public class ConfigImages implements Parcelable {

        @SerializedName("base_url")
        private String baseUrl;
        @SerializedName("secure_base_url")
        private String secureBaseUrl;
        @SerializedName("backdrop_sizes")
        private List<String> backdropSizes;
        @SerializedName("logo_siizes")
        private List<String> logoSizes;
        @SerializedName("poster_sizes")
        private List<String> posterSizes;
        @SerializedName("profile_sizes")
        private List<String> profileSizes;
        @SerializedName("still_sizes")
        private List<String> stillSizes;

        public ConfigImages() {
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getSecureBaseUrl() {
            return secureBaseUrl;
        }

        public void setSecureBaseUrl(String secureBaseUrl) {
            this.secureBaseUrl = secureBaseUrl;
        }

        public List<String> getBackdropSizes() {
            return backdropSizes;
        }

        public void setBackdropSizes(List<String> backdropSizes) {
            this.backdropSizes = backdropSizes;
        }

        public List<String> getLogoSizes() {
            return logoSizes;
        }

        public void setLogoSizes(List<String> logoSizes) {
            this.logoSizes = logoSizes;
        }

        public List<String> getPosterSizes() {
            return posterSizes;
        }

        public void setPosterSizes(List<String> posterSizes) {
            this.posterSizes = posterSizes;
        }

        public List<String> getProfileSizes() {
            return profileSizes;
        }

        public void setProfileSizes(List<String> profileSizes) {
            this.profileSizes = profileSizes;
        }

        public List<String> getStillSizes() {
            return stillSizes;
        }

        public void setStillSizes(List<String> stillSizes) {
            this.stillSizes = stillSizes;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.baseUrl);
            dest.writeString(this.secureBaseUrl);
            dest.writeStringList(this.backdropSizes);
            dest.writeStringList(this.logoSizes);
            dest.writeStringList(this.posterSizes);
            dest.writeStringList(this.profileSizes);
            dest.writeStringList(this.stillSizes);
        }

        protected ConfigImages(Parcel in) {
            this.baseUrl = in.readString();
            this.secureBaseUrl = in.readString();
            this.backdropSizes = in.createStringArrayList();
            this.logoSizes = in.createStringArrayList();
            this.posterSizes = in.createStringArrayList();
            this.profileSizes = in.createStringArrayList();
            this.stillSizes = in.createStringArrayList();
        }

        public final Creator<ConfigImages> CREATOR = new Creator<ConfigImages>() {
            @Override
            public ConfigImages createFromParcel(Parcel source) {
                return new ConfigImages(source);
            }

            @Override
            public ConfigImages[] newArray(int size) {
                return new ConfigImages[size];
            }
        };
    }
}
