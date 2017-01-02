/*
 * Copyright (c) 2014, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jim.account.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 对ViewHolder的封装，以及更方便的控制ListView滑动过程中不加载图片
 *
 * @param <T>
 * @author kymjs (https://www.kymjs.com/)
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected final int mItemLayoutId;
    protected AbsListView mList;
    protected boolean isScrolling;
    protected Context mCxt;
    protected LayoutInflater mInflater;

    private AbsListView.OnScrollListener listener;

    public BaseListAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        if (mDatas == null) {
            mDatas = new ArrayList<T>(0);
        }
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
        mCxt = context;
        mInflater = LayoutInflater.from(mCxt);
    }

    public void refresh(List<T> datas) {
        if (datas == null) {
            datas = new ArrayList<T>(0);
        }
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void addOnScrollListener(AbsListView.OnScrollListener l) {
        this.listener = l;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {

        return mDatas.get(position);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AdapterHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();

    }

    private AdapterHolder getViewHolder(int position, View convertView,
                                        ViewGroup parent) {
        return AdapterHolder.get(convertView, parent, mItemLayoutId, position);
    }

    public void convert(AdapterHolder helper, T item) {
    }

    public void convert(AdapterHolder helper, T item,
                        int position) {
        convert(helper, getItem(position));
    }


}
