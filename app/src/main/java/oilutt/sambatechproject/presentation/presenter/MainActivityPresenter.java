package oilutt.sambatechproject.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.model.DiscoverResponse;
import oilutt.sambatechproject.model.Movie;
import oilutt.sambatechproject.presentation.view.MainActivityPresenterView;
import oilutt.sambatechproject.utils.DateTimeUtil;
import oilutt.sambatechproject.utils.PreferencesManager;
import oilutt.sambatechproject.utils.integration.RetrofitBase;
import oilutt.sambatechproject.view.adapter.MoviesAdapter;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityPresenterView> {

    private int page = 1;
    private Context mContext;
    private MoviesAdapter adapter;
    private int totalPages;
    private int orderBy = 0;
    private boolean fav = false;

    public MainActivityPresenter(Context context) {
        mContext = context;
        getViewState().startShimmer();
        RetrofitBase.getInterfaceRetrofit().getMovies(Constants.THE_MOVIEDB_API_KEY, Constants.LANGUAGE,
                false, DateTimeUtil.getDateNowString(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscoverResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DiscoverResponse response) {
                        handleResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void handleResponse(DiscoverResponse response) {
        if (adapter == null) {
            new Handler().postDelayed(() -> {
                getViewState().stopShimmer();
                totalPages = response.getTotalPages();
                adapter = new MoviesAdapter(response.getResults(), mContext);
                getViewState().setAdapter(adapter);
            }, 1500);
        } else {
            new Handler().postDelayed(() -> {
                adapter.addMovies(response.getResults());
            }, 1500);
        }
    }

    public void getNextPage() {
        if(!fav) {
            getViewState().showLoading();
            switch (orderBy) {
                case 0:
                    getMovies();
                    break;
                case R.id.rb_popularity:
                    getMoviesOrderBy(Constants.SortBy.POPULARITY);
                    break;
                case R.id.rb_average_votes:
                    getMoviesOrderBy(Constants.SortBy.RATE);
                    break;
                case R.id.rb_release:
                    getMoviesOrderBy(Constants.SortBy.RELEASE);
                    break;
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == Constants.IntentCode.FILTER) {
                adapter = null;
                getViewState().startShimmer();
                orderBy = data.getIntExtra(Constants.Extras.ORDER_BY, 0);
                fav = data.getBooleanExtra(Constants.Extras.FILTER_BY, false);
                page = 0;
                if (!fav) {
                    switch (orderBy) {
                        case 0:
                            getMovies();
                            break;
                        case R.id.rb_popularity:
                            getMoviesOrderBy(Constants.SortBy.POPULARITY);
                            break;
                        case R.id.rb_average_votes:
                            getMoviesOrderBy(Constants.SortBy.RATE);
                            break;
                        case R.id.rb_release:
                            getMoviesOrderBy(Constants.SortBy.RELEASE);
                            break;
                    }
                } else {
                    switch (orderBy) {
                        case -1:
                            getFavMovies();
                            break;
                        case R.id.rb_popularity:
                            getFavMoviesOrderBy(Constants.SortBy.POPULARITY);
                            break;
                        case R.id.rb_average_votes:
                            getFavMoviesOrderBy(Constants.SortBy.RATE);
                            break;
                        case R.id.rb_release:
                            getFavMoviesOrderBy(Constants.SortBy.RELEASE);
                            break;
                    }
                }
            }
        }
    }

    private void getMovies() {
        if (page <= totalPages) {
            page++;
            RetrofitBase.getInterfaceRetrofit().getMovies(Constants.THE_MOVIEDB_API_KEY, Constants.LANGUAGE,
                    false, DateTimeUtil.getDateNowString(), page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<DiscoverResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(DiscoverResponse response) {
                            handleResponse(response);
                        }

                        @Override
                        public void onError(Throwable e) {
                            new Handler().postDelayed(() -> {
                                Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                                getViewState().hideLoading();
                            }, 1000);
                        }

                        @Override
                        public void onComplete() {
                            new Handler().postDelayed(() -> {
                                getViewState().hideLoading();
                            }, 1000);
                        }
                    });
        } else {
            getViewState().hideLoading();
        }
    }

    private void getMoviesOrderBy(String orderBy) {
        page++;
        RetrofitBase.getInterfaceRetrofit().getMoviesOrderBy(Constants.THE_MOVIEDB_API_KEY, orderBy, Constants.LANGUAGE,
                false, DateTimeUtil.getDateNowString(), page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DiscoverResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DiscoverResponse response) {
                        handleResponse(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        new Handler().postDelayed(() -> {
                            getViewState().hideLoading();
                        }, 1000);
                    }
                });
    }

    private void getFavMovies() {
        new Handler().postDelayed(() -> {
            adapter = new MoviesAdapter(PreferencesManager.getInstance().getFavMovies(), mContext);
            getViewState().stopShimmer();
            getViewState().setAdapter(adapter);
        }, 1000);
    }

    private void getFavMoviesOrderBy(String orderBy) {
        List<Movie> list = PreferencesManager.getInstance().getFavMovies();
        switch (orderBy) {
            case Constants.SortBy.POPULARITY:
                Collections.sort(list, (o1, o2) -> Double.compare(o1.getPopularity(), o2.getPopularity()));
                break;
            case Constants.SortBy.RATE:
                Collections.sort(list, (o1, o2) -> Double.compare(o1.getVoteAverage(), o2.getVoteAverage()));
                break;
            case Constants.SortBy.RELEASE:
                Collections.sort(list, (o1, o2) -> {
                    try {
                        return DateTimeUtil.parseDate(o1.getReleaseDate(), DateTimeUtil.PATTERN_DATE_DB)
                                .compareTo(DateTimeUtil.parseDate(o2.getReleaseDate(), DateTimeUtil.PATTERN_DATE_DB));
                    } catch (Exception e) {
                        return -1;
                    }
                });
                break;
        }
        new Handler().postDelayed(() -> {
            adapter = new MoviesAdapter(list, mContext);
            getViewState().stopShimmer();
            getViewState().setAdapter(adapter);
        }, 1000);
    }
}
