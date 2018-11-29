package mad.android.com.mad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MySpace extends Activity implements View.OnClickListener {

    private ImageView my_photo;
    private ViewPager my_space;
    private Toolbar my_toolbar;
    private TextView my_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_space);

        initView();
    }

    private void initView() {
        my_photo =findViewById(R.id.my_photo);
        my_space =findViewById(R.id.vp_my_space);
        my_toolbar =findViewById(R.id.my_toolbar);
        my_back =findViewById(R.id.back);
        my_photo.setOnClickListener(this);
        my_back.setOnClickListener(this);
    }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back:
                    Intent info_to_main = new Intent(MySpace.this,MainActivity.class);
                    startActivity(info_to_main);
                    finish();
                    break;
            }
        }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

