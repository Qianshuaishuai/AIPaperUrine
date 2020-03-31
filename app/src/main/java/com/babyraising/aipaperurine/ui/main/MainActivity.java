package com.babyraising.aipaperurine.ui.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.babyraising.aipaperurine.Constant;
import com.babyraising.aipaperurine.PaperUrineApplication;
import com.babyraising.aipaperurine.R;
import com.babyraising.aipaperurine.base.BaseActivity;
import com.babyraising.aipaperurine.bean.UserBean;
import com.babyraising.aipaperurine.response.PersonResponse;
import com.babyraising.aipaperurine.util.T;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.lang.reflect.Field;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements HomeFragment.OnFragmentInteractionListener, FindFragment.OnFragmentInteractionListener, StoreFragment.OnFragmentInteractionListener, PersonFragment.OnFragmentInteractionListener {

    @ViewInject(R.id.navigation)
    private BottomNavigationView navigation;

    @ViewInject(R.id.layout)
    private FrameLayout layout;

    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private StoreFragment storeFragment;
    private PersonFragment personFragment;
    private Fragment[] fragments;
    private int lastfragment = 0;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNavigationBar();
        getPersonInfo();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            refreshItemIcon();
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    item.setTextColor(getResources().getColor(R.color.colorYellow));
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                        item.setIcon(R.mipmap.icon_shouye);
                    }
                    return true;
                case R.id.navigation_find:
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                        item.setIcon(R.mipmap.icon_faxian_nor);
                    }
                    return true;
                case R.id.navigation_shop:
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                        item.setIcon(R.mipmap.icon_shangcheng_nor);
                    }
                    return true;

                case R.id.navigation_me:
                    if (lastfragment != 3) {
                        switchFragment(lastfragment, 3);
                        lastfragment = 3;
                        item.setIcon(R.mipmap.icon_wode_nor);
                    }
                    return true;
            }
            return false;
        }
    };

    private void initNavigationBar() {
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        Resources resource = (Resources) getBaseContext().getResources();
        ColorStateList csl = (ColorStateList) resource.getColorStateList(R.color.select_menu_color);
        navigation.setItemTextColor(csl);
        MenuItem item1 = navigation.getMenu().findItem(R.id.navigation_home);
        item1.setIcon(R.mipmap.icon_shouye);

        //加入fragment
        homeFragment = new HomeFragment();
        findFragment = new FindFragment();
        storeFragment = new StoreFragment();
        personFragment = new PersonFragment();
        fragments = new Fragment[]{homeFragment, findFragment, storeFragment, personFragment};

        getSupportFragmentManager().beginTransaction().replace(R.id.layout, homeFragment).show(homeFragment).commit();

        BottomNavigationViewHelper.disableShiftMode(navigation);
    }

    /**
     * 切换fragment
     */
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //隐藏上个Fragment
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.layout, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }


    private void refreshItemIcon() {
        MenuItem item1 = navigation.getMenu().findItem(R.id.navigation_home);
        item1.setIcon(R.mipmap.icon_shouye_nor);
        MenuItem item2 = navigation.getMenu().findItem(R.id.navigation_find);
        item2.setIcon(R.mipmap.icon_faxian);
        MenuItem item3 = navigation.getMenu().findItem(R.id.navigation_shop);
        item3.setIcon(R.mipmap.icon_shangcheng);
        MenuItem item4 = navigation.getMenu().findItem(R.id.navigation_me);
        item4.setIcon(R.mipmap.icon_wode);
    }

    private void backItemIcon() {
        MenuItem item1 = navigation.getMenu().findItem(R.id.navigation_home);
        MenuItem item2 = navigation.getMenu().findItem(R.id.navigation_find);
        MenuItem item3 = navigation.getMenu().findItem(R.id.navigation_shop);
        MenuItem item4 = navigation.getMenu().findItem(R.id.navigation_me);
        switch (lastfragment) {
            case 0:
                item1.setIcon(R.mipmap.icon_shouye);
                break;
            case 1:
                item2.setIcon(R.mipmap.icon_faxian_nor);
                break;
            case 2:
                item3.setIcon(R.mipmap.icon_shangcheng_nor);
                break;
            case 3:
                item3.setIcon(R.mipmap.icon_wode_nor);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println(uri);
    }

    public static class BottomNavigationViewHelper {
        @SuppressLint("RestrictedApi")
        public static void disableShiftMode(BottomNavigationView view) {
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
            try {
                Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
                shiftingMode.setAccessible(true);
                shiftingMode.setBoolean(menuView, false);
                shiftingMode.setAccessible(false);
                for (int i = 0; i < menuView.getChildCount(); i++) {
                    BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                    //noinspection RestrictedApi
                    item.setShiftingMode(false);
                    // set once again checked value, so view will be updated
                    //noinspection RestrictedApi
                    item.setChecked(item.getItemData().isChecked());
                }
            } catch (NoSuchFieldException e) {
            } catch (IllegalAccessException e) {
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getPersonInfo(){
        userBean = ((PaperUrineApplication)getApplication()).getUserInfo();

        RequestParams params = new RequestParams(Constant.BASE_URL + Constant.URL_PERSONINFO);
        params.addQueryStringParameter("APPUSER_ID", userBean.getAPPUSER_ID());
        params.addQueryStringParameter("ONLINE_ID", userBean.getONLINE_ID());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                PersonResponse response = gson.fromJson(result, PersonResponse.class);
                switch (response.getResult()) {
                    case 0:
                        ((PaperUrineApplication)getApplication()).savePersonInfo(response.getData());
                        break;

                    default:
                        T.s("获取个人信息失败");
                        break;
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                T.s("请求出错，请检查网络");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
