package mad.android.com.mad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MeFavoriteActivity extends Activity {

    private ImageView me_info;
    private ImageView info_myfavorite;
    private ImageView info_mycollection;
    private ImageView info_history;
    private CardView info_recommend1;
    private CardView info_recommend2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfo_activity);

        initViews();
    }

    private void initViews() {
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
    }
}
