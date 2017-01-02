package com.jim.account.ui.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jim.account.R;
import com.jim.account.bean.AccountBean;
import com.jim.account.db.AccountDbHelper;
import com.jim.account.model.imp.AccountModelImp;
import com.jim.account.ui.activity.AccountEditActivity;
import com.jim.account.ui.activity.AccountListActivity;
import com.jim.account.utils.AccountUitls;

import java.util.List;

/**
 * Created by jimju on 2016/12/11.
 */

public class AccoutsAdapter extends RecyclerView.Adapter<AccoutsAdapter.ViewHolder> {

    private List<AccountBean> mDatas;
    private AccountListActivity mContext;

    public AccoutsAdapter(List<AccountBean> datas, AccountListActivity context) {
        this.mDatas = datas;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_accounts,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AccountBean bean = mDatas.get(position);
        holder.ivPic.setImageResource(bean.getImageId());
        holder.tvAccount.setText(String.valueOf(bean.getPay()));
        holder.tvName.setText(bean.getProject());
        holder.tvTime.setText(bean.getTime());

        //长按弹出菜单
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showEditDialog(bean.getId());
                return false;
            }
        });
    }

    private void showEditDialog(final int id){
        AccountUitls.editDialog(mContext, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0:
                        Intent intent = new Intent(mContext, AccountEditActivity.class);
                        intent.putExtra(AccountDbHelper.AccountColum.ID,id);
                        mContext.startActivityForResult(intent,mContext.ACCOUNT_CODE);
                        break;
                    case 1:
                        new AccountModelImp(mContext).deleteAccount(id);
                        mContext.refreshList();
                        break;
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDatas.size()>0?mDatas.size():0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivPic;
        TextView tvName,tvTime,tvAccount;
        public ViewHolder(View itemView) {
            super(itemView);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_item_pic);
            tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_item_time);
            tvAccount = (TextView) itemView.findViewById(R.id.tv_item_pay);
        }
    }


}
