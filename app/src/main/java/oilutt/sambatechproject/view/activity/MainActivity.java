package oilutt.sambatechproject.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.presentation.presenter.MainActivityPresenter;
import oilutt.sambatechproject.presentation.view.MainActivityPresenterView;
import oilutt.sambatechproject.utils.PreferencesManager;
import oilutt.sambatechproject.view.adapter.MoviesAdapter;

import static android.view.View.GONE;

public class MainActivity extends BaseActivity implements MainActivityPresenterView {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;
    @BindView(R.id.shimmer_layout)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.loading)
    AVLoadingIndicatorView loading;
    @BindView(R.id.view_helper)
    View viewHelper;

    @InjectPresenter
    MainActivityPresenter presenter;

    @ProvidePresenter
    MainActivityPresenter createPresenter() {
        return new MainActivityPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setUpToolbarText(R.string.app_name, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferencesManager.getInstance().remove(Constants.SharedPreferences.FAV_SELECTED);
        PreferencesManager.getInstance().remove(Constants.SharedPreferences.ORDER_BY_INT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                goToActivityCircleRevealForResult(this, FilterActivity.class, viewHelper, Constants.IntentCode.FILTER);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rvMovies.setLayoutManager(layoutManager);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            rvMovies.setLayoutManager(layoutManager);
        }
    }

    @Override
    public void setAdapter(MoviesAdapter adapter) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvMovies.setLayoutManager(layoutManager);
        rvMovies.setVisibility(View.VISIBLE);
        rvMovies.setAdapter(adapter);
        rvMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    presenter.getNextPage();

                }
            }
        });
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(GONE);
    }

    @Override
    public void startShimmer() {
        rvMovies.setVisibility(GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void stopShimmer() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(GONE);
        rvMovies.setVisibility(View.VISIBLE);
    }
}
