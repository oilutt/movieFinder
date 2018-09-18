package oilutt.sambatechproject.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.model.Configuration;
import oilutt.sambatechproject.model.Movie;

/**
 * Created by oilut on 21/08/2017.
 */
public final class PreferencesManager {

    private static final String PREF_NAME = Constants.SharedPreferences.PREFERENCES_NAME;
    private static final String mDelimiter = "-";

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void setValue(String key, long value) {
        mPref.edit()
                .putLong(key, value)
                .apply();
    }

    public void setValue(String key, int value) {
        mPref.edit()
                .putInt(key, value)
                .apply();
    }

    public void setValue(String key, float value) {
        mPref.edit()
                .putFloat(key, value)
                .apply();
    }

    public void setValue(String key, String value) {
        mPref.edit()
                .putString(key, value)
                .apply();
    }

    public void setValue(String key, boolean value) {
        mPref.edit()
                .putBoolean(key, value)
                .apply();
    }

    public void setValue(String key, List<String> stringList) {
        if (key == null || stringList == null) {
            return;
        }
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        mPref.edit().putString(key, TextUtils.join(mDelimiter, myStringList)).apply();
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList<>(Arrays.asList(TextUtils.split(mPref.getString(key, ""), mDelimiter)));
    }

    public long getLong(String key) {
        return mPref.getLong(key, 0);
    }


    public int getInt(String key) {
        return mPref.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return mPref.getInt(key, defaultValue);
    }

    public float getFloat(String key) {
        return mPref.getFloat(key, 0);
    }


    public String getString(String key) {
        return mPref.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return mPref.getBoolean(key, false);
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        return mPref.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        mPref.edit()
                .remove(key)
                .apply();
    }

    public void clear() {
        mPref.edit()
                .clear()
                .apply();
    }

    public void setConfiguration(Configuration configuration) {
        Gson gson = new Gson();
        String json = gson.toJson(configuration);
        mPref.edit().putString(Constants.SharedPreferences.CONFIG, json).apply();
    }

    public Configuration getConfiguration() {
        Gson gson = new Gson();
        String json = mPref.getString(Constants.SharedPreferences.CONFIG, "");
        return gson.fromJson(json, Configuration.class);
    }

    public void setFavMovie(Movie movie) {
        List<Movie> list = getFavMovies();
        if(list == null) {
            list = new ArrayList<>();
        }
        list.add(movie);
        Gson gson = new Gson();
        String json = gson.toJson(list);
        mPref.edit().putString(Constants.SharedPreferences.LIST_FAV_MOVIES, json).apply();
    }

    public void removeMovie(int movieId) {
        List<Movie> list = getFavMovies();
        for (int x=0; x < list.size(); x++) {
            if(list.get(x).getId() == movieId) {
                list.remove(x);
            }
        }
        Gson gson = new Gson();
        String json = gson.toJson(list);
        mPref.edit().putString(Constants.SharedPreferences.LIST_FAV_MOVIES, json).apply();
    }

    public List<Movie> getFavMovies() {
        Gson gson = new Gson();
        String json = mPref.getString(Constants.SharedPreferences.LIST_FAV_MOVIES, "");
        return gson.fromJson(json, new TypeToken<ArrayList<Movie>>() {
        }.getType());
    }
}
