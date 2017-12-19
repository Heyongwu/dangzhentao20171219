package com.bawei.dangzhentao20171219.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bawei.dangzhentao20171219.R;
import com.bawei.dangzhentao20171219.adapter.Adapter;
import com.bawei.dangzhentao20171219.bean.GouBean;
import com.bawei.dangzhentao20171219.bean.JiaBean;
import com.bawei.dangzhentao20171219.eventbus.MessageEvent;
import com.bawei.dangzhentao20171219.eventbus.PriceAndCountEvent;
import com.bawei.dangzhentao20171219.presenter.NewsPersenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class GouActivity extends AppCompatActivity implements INews{
    private ExpandableListView mElv;
    private CheckBox mCheckbox2;
    /**
     * 0
     */
    private TextView mTvPrice;
    /**
     * 结算(0)
     */
    private TextView mTvNum;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gou);
        EventBus.getDefault().register(this);
        initView();
        new NewsPersenter(this).getNewss();
        mCheckbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.changeAllListCbState(mCheckbox2.isChecked());
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mCheckbox2 = (CheckBox) findViewById(R.id.checkbox2);
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mTvNum = (TextView) findViewById(R.id.tv_num);
    }
    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        mCheckbox2.setChecked(event.isChecked());
    }

    @Subscribe
    public void onMessageEvent(PriceAndCountEvent event) {
        mTvNum.setText("结算(" + event.getCount() + ")");
        mTvPrice.setText(event.getPrice() + "");
    }

    @Override
    public void showBean(JiaBean jiaBean) {
        
    }

    @Override
    public void showBeans(GouBean gouBean) {
        List<GouBean.DataBean> list = gouBean.getData();
        ArrayList<List<GouBean.DataBean.ListBean>> lists = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            List<GouBean.DataBean.ListBean> datas=list.get(i).getList();
            lists.add(datas);
        }
        adapter = new Adapter(GouActivity.this,list,lists);
        mElv.setAdapter(adapter);
        mElv.setGroupIndicator(null);
        //默认其他全部展开
        for (int i = 0; i < list.size(); i++) {
            mElv.expandGroup(i);
        }
    }
}
