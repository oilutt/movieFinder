package oilutt.sambatechproject.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.List;

import oilutt.sambatechproject.model.Movie;

public class RetainedFragment extends Fragment {

    private List<Movie> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setData(List<Movie> data) {
        this.data = data;
    }

    public List<Movie> getData() {
        return data;
    }
}