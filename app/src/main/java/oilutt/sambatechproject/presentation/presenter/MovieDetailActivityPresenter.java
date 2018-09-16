package oilutt.sambatechproject.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.model.Configuration;
import oilutt.sambatechproject.model.Movie;
import oilutt.sambatechproject.presentation.view.MovieDetailActivityPresenterView;
import oilutt.sambatechproject.utils.DateTimeUtil;
import oilutt.sambatechproject.utils.PreferencesManager;
import oilutt.sambatechproject.utils.integration.RetrofitBase;
import oilutt.sambatechproject.view.adapter.AdapterGenres;

@InjectViewState
public class MovieDetailActivityPresenter extends MvpPresenter<MovieDetailActivityPresenterView> {

    private Context mContext;
    private Movie mMovie;

    public MovieDetailActivityPresenter(Intent intent, Context context) {
        mContext = context;
        getViewState().setToolbarTitle(intent.getStringExtra(Constants.Extras.NAME_MOVIE));
        RetrofitBase.getInterfaceRetrofit().getMovieDetails(intent.getStringExtra(Constants.Extras.ID_MOVIE),
                Constants.THE_MOVIEDB_API_KEY, Constants.LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Movie movie) {
                        handleMovie(movie);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().goBack();
                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void handleMovie(Movie movie) {
        mMovie = movie;
        getViewState().setTitle(movie.getTitle());
        getViewState().setDescription(movie.getOverview());
        getViewState().setTagline(movie.getTagline());
        getViewState().setLength(String.valueOf(movie.getRuntime()) + " min");
        getViewState().setPopularity(String.valueOf(movie.getPopularity()));
        getViewState().setRate(String.valueOf(movie.getVoteAverage()) + "/10");

        Configuration.ConfigImages configImages = PreferencesManager.getInstance().getConfiguration().getConfigImages();
        String urlImage = configImages.getSecureBaseUrl() + configImages.getPosterSizes().get(4) + "/" + movie.getPosterPath();
        getViewState().setImage(urlImage);

        try {
            Date movieDate = DateTimeUtil.parseDate(movie.getReleaseDate(), DateTimeUtil.PATTERN_DATE_DB);
            getViewState().setDay(DateTimeUtil.getDay(movieDate));
            getViewState().setMonth(DateTimeUtil.getAbreviatedMonth(DateTimeUtil.toCalendar(movieDate)));
            getViewState().setYear(DateTimeUtil.getYear(movieDate));
        } catch (Exception e) {
            if (!movie.getReleaseDate().equals("")) {
                String[] auxDate = movie.getReleaseDate().split("-");
                getViewState().setYear(auxDate[0]);
                getViewState().setMonth(auxDate[1]);
                getViewState().setDay(auxDate[2]);
            }
        }

        getViewState().setGenres(new AdapterGenres(movie.getGenres(), mContext));
    }

    public void clickImage() {
        Configuration.ConfigImages configImages = PreferencesManager.getInstance().getConfiguration().getConfigImages();
        String urlImage = configImages.getSecureBaseUrl() + configImages.getPosterSizes().get(5) + "/" + mMovie.getPosterPath();
        getViewState().showImage(urlImage);
    }
}
