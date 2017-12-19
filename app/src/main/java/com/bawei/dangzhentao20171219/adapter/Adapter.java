package com.bawei.dangzhentao20171219.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.dangzhentao20171219.R;
import com.bawei.dangzhentao20171219.bean.GouBean;
import com.bawei.dangzhentao20171219.eventbus.MessageEvent;
import com.bawei.dangzhentao20171219.eventbus.PriceAndCountEvent;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by DangByMySide on 2017/12/19.
 */

public class Adapter extends BaseExpandableListAdapter {
    private Context context;
    private List<GouBean.DataBean> groupList;
    private List<List<GouBean.DataBean.ListBean>> childList;
    private LayoutInflater inflater;

    public Adapter(Context context, List<GouBean.DataBean> groupList, List<List<GouBean.DataBean.ListBean>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        final GroupViewHolder holder;
        if(convertView==null){
            holder=new GroupViewHolder();
            view=inflater.inflate(R.layout.item_parent_market,null);
            holder.cbGroup = (CheckBox) view.findViewById(R.id.cb_parent);
            holder.tv_number = (TextView) view.findViewById(R.id.tv_number);
            view.setTag(holder);
        }else {
            view=convertView;
            holder = (GroupViewHolder) view.getTag();
        }
        final GouBean.DataBean dataBean = groupList.get(groupPosition);
        holder.cbGroup.setChecked(dataBean.isChecked());
        holder.tv_number.setText(dataBean.getSellerName());
        //给cbGroup设置点击事件
        holder.cbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBean.setChecked(holder.cbGroup.isChecked());
                changeChildCbState(groupPosition, holder.cbGroup.isChecked());
                EventBus.getDefault().post(compute());
                changeAllCbState(isAllGroupCbSelected());
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        final ChildViewHolder holder;
        if(convertView==null){
            holder=new ChildViewHolder();
            view=inflater.inflate(R.layout.item_child_market,null);
            holder.cbChild = (CheckBox) view.findViewById(R.id.cb_child);
            holder.iiv = view.findViewById(R.id.iiv);
            holder.tv_tel = (TextView) view.findViewById(R.id.tv_tel);
            holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
            holder.tv_pri = (TextView) view.findViewById(R.id.tv_pri);
            holder.tv_num = (TextView) view.findViewById(R.id.tv_num);
            holder.iv_add = (ImageView) view.findViewById(R.id.iv_add);
            holder.iv_del = (ImageView) view.findViewById(R.id.iv_del);
            holder.tv_del = (TextView) view.findViewById(R.id.tv_del);
            view.setTag(holder);
        }else {
            view=convertView;
            holder = (ChildViewHolder) view.getTag();
        }
        final GouBean.DataBean.ListBean listBean = childList.get(groupPosition).get(childPosition);
        holder.iiv.setImageURI(listBean.getImages());
        holder.cbChild.setChecked(listBean.isChecked());
        holder.tv_tel.setText(listBean.getTitle());
        holder.tv_time.setText(listBean.getCreatetime());
        holder.tv_pri.setText(listBean.getPrice()+"");
        holder.tv_num.setText(listBean.getNum() + "");
        //给cbChild设置点击事件
        holder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置点击的条目的属性值
                listBean.setChecked(holder.cbChild.isChecked());
                PriceAndCountEvent priceAndCountEvent = compute();
                EventBus.getDefault().post(priceAndCountEvent);
                //判断点击的是否选中
                if(holder.cbChild.isChecked()){
                    //点击时cbClild是选中状态
                    if(isAllChildListChecked(groupPosition)){
                        changGroupCbState(groupPosition,true);
                        changeAllCbState(isAllGroupCbSelected());
                    }
                }else {
                    changGroupCbState(groupPosition, false);
                    changeAllCbState(isAllGroupCbSelected());
                }
                notifyDataSetChanged();

            }
        });
        //加号
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = listBean.getNum();
                holder.tv_num.setText(++num + "");
                listBean.setNum(num);
                if (holder.cbChild.isChecked()) {
                    PriceAndCountEvent priceAndCountEvent = compute();
                    EventBus.getDefault().post(priceAndCountEvent);
                }
            }
        });
        //减号
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = listBean.getNum();
                if (num == 1) {
                    return;
                }
                holder.tv_num.setText(--num + "");
                listBean.setNum(num);
                if (holder.cbChild.isChecked()) {
                    PriceAndCountEvent priceAndCountEvent = compute();
                    EventBus.getDefault().post(priceAndCountEvent);
                }
            }
        });
        //删除
        holder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GouBean.DataBean.ListBean> listBeans = childList.get(groupPosition);
                GouBean.DataBean.ListBean remove = listBeans.remove(childPosition);
                if (listBeans.size() == 0) {
                    childList.remove(groupPosition);
                    groupList.remove(groupPosition);
                }
                EventBus.getDefault().post(compute());
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class GroupViewHolder{
        CheckBox cbGroup;
        TextView tv_number;
    }
    class ChildViewHolder{
        CheckBox cbChild;
        TextView tv_tel;
        TextView tv_time;
        TextView tv_pri;
        TextView tv_num;
        ImageView iv_del;
        ImageView iv_add;
        TextView tv_del;
        SimpleDraweeView iiv;
    }
    //设置全选、反选
    public void changeAllListCbState(boolean flag) {
        for (int i = 0; i < groupList.size(); i++) {
            changGroupCbState(i, flag);
            changeChildCbState(i, flag);
        }
        EventBus.getDefault().post(compute());
        notifyDataSetChanged();
    }
    //改变二级列表状态
    private void changeChildCbState(int groupPosition, boolean flag) {
        List<GouBean.DataBean.ListBean> listBeans = childList.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            GouBean.DataBean.ListBean listBean = listBeans.get(i);
            listBean.setChecked(flag);
        }
    }
    //计算列表中，选中的钱和数量
    private PriceAndCountEvent compute() {
        int count = 0;
        int price = 0;
        for (int i = 0; i < childList.size(); i++) {
            List<GouBean.DataBean.ListBean> listBeans = childList.get(i);
            for (int j = 0; j < listBeans.size(); j++) {
                GouBean.DataBean.ListBean listBean = listBeans.get(j);
                if (listBean.isChecked()) {
                    price += listBean.getNum() * listBean.getPrice();
                    count += listBean.getNum();
                }
            }
        }
        PriceAndCountEvent priceAndCountEvent = new PriceAndCountEvent();
        priceAndCountEvent.setCount(count);
        priceAndCountEvent.setPrice(price);
        return priceAndCountEvent;
    }
    //判断一级列表是否全部选中
    private boolean isAllGroupCbSelected() {
        for (int i = 0; i < groupList.size(); i++) {
            GouBean.DataBean dataBean = groupList.get(i);
            if (!dataBean.isChecked()) {
                return false;
            }
        }
        return true;
    }
    //改变全选的状态
    private void changeAllCbState(boolean flag){
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setChecked(flag);
        EventBus.getDefault().post(messageEvent);
    }
    //改变一级列表状态
    private void changGroupCbState(int groupPosition, boolean flag){
        GouBean.DataBean dataBean = groupList.get(groupPosition);
        dataBean.setChecked(flag);
    }
    //遍历一级列表是否全选
    private boolean isAllGroupListChecked() {
        for (int i = 0; i < groupList.size(); i++) {
            GouBean.DataBean dataBean = groupList.get(i);
            if (!dataBean.isChecked()) {
                return false;
            }
        }
        return true;
    }
    //遍历二级列表，判断其他是否选中
    private boolean isAllChildListChecked(int groupPostion){
        List<GouBean.DataBean.ListBean> listBeans = childList.get(groupPostion);
        for(int i=0;i<listBeans.size();i++){
            GouBean.DataBean.ListBean listBean = listBeans.get(i);
            if(!listBean.isChecked()){
                return false;
            }
        }
        return true;
    }
}
