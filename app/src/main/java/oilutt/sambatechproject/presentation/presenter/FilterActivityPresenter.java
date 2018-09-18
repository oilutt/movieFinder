package oilutt.sambatechproject.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import oilutt.sambatechproject.app.Constants;
import oilutt.sambatechproject.presentation.view.FilterActivityPresenterView;
import oilutt.sambatechproject.utils.PreferencesManager;

@InjectViewState
public class FilterActivityPresenter extends MvpPresenter<FilterActivityPresenterView> {

    public FilterActivityPresenter() {
        if(PreferencesManager.getInstance().getInt(Constants.SharedPreferences.ORDER_BY_INT, -1) != -1) {
            getViewState().setOrderBySelected(PreferencesManager.getInstance().getInt(Constants.SharedPreferences.ORDER_BY_INT, -1));
        }
        if(PreferencesManager.getInstance().getBoolean(Constants.SharedPreferences.FAV_SELECTED, false)) {
            getViewState().setFavSelected();
        }
    }

    public void clickFilter(int rbId, boolean favSelected) {
        PreferencesManager.getInstance().setValue(Constants.SharedPreferences.ORDER_BY_INT, rbId);
        PreferencesManager.getInstance().setValue(Constants.SharedPreferences.FAV_SELECTED, favSelected);
        getViewState().clickFilter();
    }
}
