package mad.android.com.mad;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import mad.android.com.mad.MySpace;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener {

    private View view_status;
    private ImageView iv_recently;
    private ImageView iv_comment;
    private ImageView iv_rank;
    private ViewPager main_content;
    private Toolbar bottom_toolbar;
    private ImageView myInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initContentFragment();
    }

    private void initView() {
        view_status =findViewById(R.id.view_status);
        iv_recently =findViewById(R.id.iv_recently);
        iv_comment =findViewById(R.id.iv_comment);
        iv_rank = findViewById(R.id.iv_rank);
        myInfo =findViewById(R.id.myInfo);
        main_content =findViewById(R.id.main_content);
        bottom_toolbar =findViewById(R.id.bottom_toolbar);

        myInfo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
            Toast toast = Toast.makeText(MainActivity.this,"要显示的内容",Toast.LENGTH_LONG);
            toast.show();
            startActivity(new Intent(MainActivity.this,MySpace.class));
        }
        });
        iv_recently.setOnClickListener(this);
        iv_comment.setOnClickListener(this);
        iv_rank.setOnClickListener(this);

    }

    private void initContentFragment() {
        ArrayList<Fragment> mFragmentList =new ArrayList<>();
        mFragmentList.add(new FgRecentlyFragment());
        mFragmentList.add(new FgCommentFragment());
        mFragmentList.add(new FgRankFragment());

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),mFragmentList);
        main_content.setAdapter(adapter);
        main_content.setOffscreenPageLimit(1);
        main_content.addOnPageChangeListener(this);

        setSupportActionBar(bottom_toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayShowTitleEnabled(false);
        }
        setCurrentItem(0);

    }



    private void setCurrentItem(int i){
        main_content.setCurrentItem(i);
        iv_rank.setSelected(false);
        iv_comment.setSelected(false);
        iv_recently.setSelected(false);
        switch (i){
            case 0:
                iv_recently.setSelected(true);
                break;
            case 1:
                iv_comment.setSelected(true);
            case 2:
                iv_rank.setSelected(true);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.iv_recently:
                if(main_content.getCurrentItem() != 0){
                    setCurrentItem(0);
                }
                break;
            case R.id.iv_comment:
                if(main_content.getCurrentItem() != 1){
                    setCurrentItem(1);
                }
                break;
            case R.id.iv_rank:
                if(main_content.getCurrentItem() !=2){
                    setCurrentItem(2);
                }
                break;

            case R.id.myInfo:
                Intent main_to_info = new Intent(MainActivity.this,MySpace.class);
                startActivity(main_to_info);
                finish();
                break;
        }



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
