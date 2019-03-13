package bw.com.movie.basea;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import bw.com.movie.utils.NetWorkUtils;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initlayout());

        initView();
        initData();
    }
  /*  private void isConn() {
        boolean conn = ConnectionUtil.isConn(CircleActivity.this);
        //如果没有网络
        if (!conn) {
            Toast.makeText(this, "请链接网络", Toast.LENGTH_SHORT).show();
            //调用网络工具类中的方法，跳转到设置网络的界面
            ConnectionUtil.setNetworkMethod(CircleActivity.this);
        } else {
            //有的话就做自己的操作
            initData();
        }*/




    protected abstract void initData();

    protected abstract void initView();

    protected abstract int initlayout();

    @Override
    protected void onResume() {
        super.onResume();
        NetWorkUtils.getConnectivityManager(BaseActivity.this);
    }
}
