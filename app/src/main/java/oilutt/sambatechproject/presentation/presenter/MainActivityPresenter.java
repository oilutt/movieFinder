package oilutt.sambatechproject.presentation.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.model.DiscoverResponse;
import oilutt.sambatechproject.presentation.view.MainActivityPresenterView;
import oilutt.sambatechproject.utils.integration.RetrofitBase;
import oilutt.sambatechproject.view.adapter.MoviesAdapter;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityPresenterView> {

    private int page = 1;
    private Context mContext;
    private MoviesAdapter adapter;
    private int totalPages;
    private int orderBy = 0;

    public MainActivityPresenter(Context context) {
        mContext = context;
        getViewState().startShimmer();
        RetrofitBase.getInterfaceRetrofit().getMovies(Constants.THE_MOVIEDB_API_KEY, Constants.LANGUAGE, false, page)
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
            }, 1000);
        }
    }

    public void getNextPage() {
        getViewState().showLoading();
        switch (orderBy) {
            case 0:
                getMovies();
                break;
            case R.id.rb_popularity:
                getMoviesOrderByPopularity();
                break;
            case R.id.rb_average_votes:
                getMoviesOrderByRate();
                break;
            case R.id.rb_release:
                getMoviesOrderByRelease();
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == Constants.IntentCode.FILTER) {
                adapter = null;
                getViewState().startShimmer();
                orderBy = data.getIntExtra(Constants.Extras.ORDER_BY, 0);
                page = 0;
                switch (orderBy) {
                    case 0:
                        getMovies();
                        break;
                    case R.id.rb_popularity:
                        getMoviesOrderByPopularity();
                        break;
                    case R.id.rb_average_votes:
                        getMoviesOrderByRate();
                        break;
                    case R.id.rb_release:
                        getMoviesOrderByRelease();
                        break;
                }
            }
        }
    }

    private void getMovies() {
        if (page <= totalPages) {
            page++;
            RetrofitBase.getInterfaceRetrofit().getMovies(Constants.THE_MOVIEDB_API_KEY, Constants.LANGUAGE, false, page)
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

    private void getMoviesOrderByPopularity() {
        page++;
        RetrofitBase.getInterfaceRetrofit().getMoviesOrderBy(Constants.THE_MOVIEDB_API_KEY, Constants.SortBy.POPULARITY, Constants.LANGUAGE, false, page)
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

    private void getMoviesOrderByRate() {
        page++;
        RetrofitBase.getInterfaceRetrofit().getMoviesOrderBy(Constants.THE_MOVIEDB_API_KEY, Constants.SortBy.RATE, Constants.LANGUAGE, false, page)
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

    private void getMoviesOrderByRelease() {
        page++;
        RetrofitBase.getInterfaceRetrofit().getMoviesOrderBy(Constants.THE_MOVIEDB_API_KEY, Constants.SortBy.RELEASE, Constants.LANGUAGE, false, page)
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
}
