package oilutt.sambatechproject.presentation.view;

import com.arellomobile.mvp.MvpView;

import oilutt.sambatechproject.view.adapter.MoviesAdapter;

public interface MainActivityPresenterView extends MvpView {

    void setAdapter(MoviesAdapter adapter);

    void showLoading();

    void hideLoading();

    void startShimmer();

    void stopShimmer();
}
