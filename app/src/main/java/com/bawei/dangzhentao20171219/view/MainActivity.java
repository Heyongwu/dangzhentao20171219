package com.bawei.dangzhentao20171219.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.dangzhentao20171219.R;
import com.bawei.dangzhentao20171219.bean.GouBean;
import com.bawei.dangzhentao20171219.bean.JiaBean;
import com.bawei.dangzhentao20171219.presenter.NewsPersenter;
import com.bawei.dangzhentao20171219.utils.GlideImageLoader;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements INews {

    @BindView(R.id.ban)
    Banner ban;
    @BindView(R.id.iv)
    SimpleDraweeView iv;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv01)
    TextView tv01;
    @BindView(R.id.tv02)
    TextView tv02;
    @BindView(R.id.btn01)
    Button btn01;
    @BindView(R.id.btn02)
    Button btn02;
    private NewsPersenter newsPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        newsPersenter = new NewsPersenter(this);
        newsPersenter.getNewss();
        ban.setImageLoader(new GlideImageLoader());
        List<String> img = new ArrayList<String>();
        img.add("https://m.360buyimg.com/n0/jfs/t1930/284/2865629620/390243/e3ade9c4/56f0a08fNbd3a1235.jpg!q70.jpg");
        img.add("https://m.360buyimg.com/n0/jfs/t2137/336/2802996626/155915/e5e90d7a/56f0a09cN33e01bd0.jpg!q70.jpg");
        img.add("https://m.360buyimg.com/n0/jfs/t1882/31/2772215910/389956/c8dbf370/56f0a0a2Na0c86ea6.jpg!q70.jpg");
        ban.setImages(img);
        ban.start();
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsPersenter.getNews();
            }
        });
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GouActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void showBean(JiaBean jiaBean) {
        Toast.makeText(MainActivity.this, jiaBean.getMsg(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showBeans(GouBean gouBean) {
        List<GouBean.DataBean> data = gouBean.getData();
        String sellerName = data.get(1).getSellerName();
        tv02.setText(sellerName);
        iv.setImageURI("https://m.360buyimg.com/n0/jfs/t1930/284/2865629620/390243/e3ade9c4/56f0a08fNbd3a1235.jpg!q70.jpg");
        tv.setText(data.get(1).getList().get(0).getTitle());
        tv01.setText(data.get(1).getList().get(0).getPrice() + "");
    }


}
