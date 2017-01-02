package com.jim.account.ui.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jim.account.MainActivity;
import com.jim.account.R;


public abstract class BaseFragment extends Fragment {
    protected Toolbar mToolbar;

    public MainActivity getDrawerActivity(){
        return ((MainActivity) super.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
       initBar(view);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBar(view);
        initViews(view);
    }

    protected void initBar(View view) {
        if(!hasCustomToolbar()) return;
        mToolbar = (Toolbar) view.findViewById(getToolbarId());
        mToolbar.setTitle(getTitle());
        mToolbar.setNavigationIcon(R.mipmap.ic_menu);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDrawerActivity().openDrawer();
            }
        });
    }

    protected @IdRes int getToolbarId(){
        return R.id.toolbar;
    }

    public boolean hasCustomToolbar(){
        return false;
    }

   /* protected @StringRes int getTitle(){
        return R.string.not_title_set;
    }*/

    protected String getTitle(){
        return "";
    }

    protected abstract  @LayoutRes int getLayout();

    protected  void initViews(View view){

    }
}
