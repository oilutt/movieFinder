package oilutt.sambatechproject.presentation.view;

import com.arellomobile.mvp.MvpView;

import oilutt.sambatechproject.view.adapter.AdapterGenres;

public interface MovieDetailActivityPresenterView extends MvpView {

    void setToolbarTitle(String movieName);

    void setImage(String img);

    void setTitle(String title);

    void setDay(String day);

    void setMonth(String month);

    void setYear(String year);

    void setTagline(String tagline);

    void setDescription(String description);

    void setLength(String length);

    void setRate(String rate);

    void setPopularity(String popularity);

    void setGenres(AdapterGenres adapter);

    void goBack();

    void showImage(String url);

    void changeFavColor();
}
