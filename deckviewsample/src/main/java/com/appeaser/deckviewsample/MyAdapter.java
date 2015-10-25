package com.appeaser.deckviewsample;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 龙翔 on 2015/9/16.
 */
public class MyAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Infos> myList = new ArrayList<>();
    private  MyViewHolder mvh;
    //构造方法
    public MyAdapter(Context context){
        this.context = context;
    }
    //添加数据列表
    public void addAll(List<Infos> lists){
        myList.addAll(lists);
        notifyDataSetChanged();
    }
    public void Clear(){
        myList.clear();
        notifyDataSetChanged();
    }

    @Override
    //在这里设置我们下面自己设置的ViewHolder并返回
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_cell,null));
        return myViewHolder;
    }

    public interface OnItemClickLitener
    {void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    //通常在这里添加数据，动态修改布局
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        //强制转换为自己的viewholder
       mvh = (MyViewHolder) viewHolder;
        Infos jb = myList.get(i);

        mvh.GetPidTextView().setText(jb.getPid());

        if (mOnItemClickLitener != null) {
            mvh.list_cell_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = mvh.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(mvh.list_cell_view, pos);
                }
            });
        }

        }
    //返回数据的数量
    @Override
    public int getItemCount() {
        return myList.size();
    }
    //继承自RecyclerView的Viewholder
    public class MyViewHolder extends RecyclerView.ViewHolder{
        //自定义的xml的View
        private View list_cell_view;
        private TextView tv_pid;
        //参数就是自定义的XML的布局View，构造方法必须实现
        public MyViewHolder(View list_cell_view) {
            super(list_cell_view);
            this.list_cell_view = list_cell_view;
            tv_pid = (TextView) list_cell_view.findViewById(R.id.tv_pid);
        }

        public TextView GetPidTextView(){
            return tv_pid;
        }
    }
}