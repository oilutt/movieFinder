package oilutt.sambatechproject.presentation.presenter;

import android.content.Context;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import oilutt.sambatechproject.R;
import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.model.Configuration;
import oilutt.sambatechproject.presentation.view.SplashActivityPresenterView;
import oilutt.sambatechproject.utils.PreferencesManager;
import oilutt.sambatechproject.utils.integration.RetrofitBase;

@InjectViewState
public class SplashActivityPresenter extends MvpPresenter<SplashActivityPresenterView> {

    private Context mContext;

    public SplashActivityPresenter(Context context) {
        mContext = context;
        getConfiguration();
    }

    private void getConfiguration() {
        RetrofitBase.getInterfaceRetrofit().getConfiguration(Constants.THE_MOVIEDB_API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Configuration>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Configuration configuration) {
                        PreferencesManager.getInstance().setConfiguration(configuration);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, R.string.error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        getViewState().goToMain();
                    }
                });
    }
}
