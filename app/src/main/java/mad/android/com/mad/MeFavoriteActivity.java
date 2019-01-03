package mad.android.com.mad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import mad.android.com.mad.beans.Movie;

public class MeFavoriteActivity extends Activity {

    private ImageView me_info;
    private ImageView info_myfavorite;
    private ImageView info_mycollection;
    private ImageView info_history;
    private CardView info_recommend1;
    private CardView info_recommend2;

    private ImageView myinfo_pic1;
    private TextView myinfo_title1;
    private TextView myinfo_intro1;
    private ImageView myinfo_pic2;
    private TextView myinfo_title2;
    private TextView myinfo_intro2;

    private String str1;
    private String str2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_activity);
        Bmob.initialize(this, "acbc897f711009c562745ec99aacfd49");
        initViews();
        loadData();
    }

    private void initViews() {
        myinfo_pic1 = findViewById(R.id.myinfo_pic1);
        myinfo_title1 = findViewById(R.id.myinfo_title1);
        myinfo_intro1 = findViewById(R.id.myinfo_intro1);
        myinfo_pic2 = findViewById(R.id.myinfo_pic2);
        myinfo_title2 = findViewById(R.id.myinfo_title2);
        myinfo_intro2 = findViewById(R.id.myinfo_intro2);
        ImageButton titleImv = (ImageButton) findViewById(R.id.title_imv);
        titleImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MeFavoriteActivity.this.finish();
            }
        });

        TextView titleCenterTv = (TextView) findViewById(R.id.title_center_text_tv);
        titleCenterTv.setText("个人资料");

        TextView titleRightTv = (TextView) findViewById(R.id.title_right_text_tv);
        titleRightTv.setVisibility(View.GONE);

        me_info = findViewById(R.id.me_info);
        me_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeFavoriteActivity.this, MeInfoActivity.class));
            }
        });

        info_myfavorite = findViewById(R.id.info_myfavorite);
        info_myfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MeFavoriteActivity.this, MyfavoriteActivity.class));
            }
        });

        TextView title_right_text_tv =findViewById(R.id.title_right_text_tv);
        title_right_text_tv.setText("刷新");
        title_right_text_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeFavoriteActivity.this,MeFavoriteActivity.class);
            }
        });
    }

    private void loadData(){
        final BmobQuery<Movie> movieBmobQuery = new BmobQuery<>();
        movieBmobQuery.addWhereEqualTo("like",true).order("-updateAt");
        movieBmobQuery.findObjects(new FindListener<Movie>() {
            @Override
            public void done(List<Movie> list, BmobException e) {
                if(e==null){

                str1=list.get(0).getGenres();
                str2=list.get(1).getGenres();
                    BmobQuery<Movie> movieBmobQuery1 = new BmobQuery<>();
                    movieBmobQuery1.addWhereEqualTo("genres",str1).order("date");
                    movieBmobQuery1.findObjects(new FindListener<Movie>() {
                        @Override
                        public void done(List<Movie> list, BmobException e) {
                            if(e==null){
                                myinfo_title1.setText(list.get(1).getTitle());
                                myinfo_intro1.setText(list.get(1).getCasts());
                                Glide.with(MeFavoriteActivity.this).load(list.get(1).getPosterUrl()).error(R.drawable.my)
                                        .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(myinfo_pic1);
                            }
                            else{
                                Log.e("BMOB", e.toString());
                            }
                        }
                    });

                    BmobQuery<Movie> movieBmobQuery2 = new BmobQuery<>();
                    movieBmobQuery2.addWhereEqualTo("genres",str2).order("date");
                    movieBmobQuery2.findObjects(new FindListener<Movie>() {
                        @Override
                        public void done(List<Movie> list, BmobException e) {
                            if(e==null){
                                myinfo_title2.setText(list.get(1).getTitle());
                                myinfo_intro2.setText(list.get(1).getCasts());
                                Glide.with(MeFavoriteActivity.this).load(list.get(1).getPosterUrl()).error(R.drawable.my)
                                        .centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .into(myinfo_pic2);
                            }
                        }
                    });

                }
                else{
                    Log.e("BMOB", e.toString());
                }
            }
        });



    }
}
