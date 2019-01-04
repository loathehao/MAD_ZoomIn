package mad.android.com.mad.myInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import cn.bmob.v3.BmobUser;
import mad.android.com.mad.R;

import mad.android.com.mad.bean.AppUser;
import mad.android.com.mad.util.FileUtil;
import uk.co.senab.photoview.PhotoViewAttacher;

/*
 图像裁剪
 */
public class MeAvatarShowerAty extends Activity {
    private ImageView avatarImv;
    private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_avatar_shower_layout);

        initView();
        eventDeal();
    }


    private void initView() {
        avatarImv = (ImageView) findViewById(R.id.me_show_avatar_imv);
        ImageButton titlteImv = (ImageButton) findViewById(R.id.title_imv);
        titlteImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeAvatarShowerAty.this.finish();
            }
        });
    }

    private void eventDeal() {
        AppUser appUser = BmobUser.getCurrentUser(AppUser.class);
        String avatarUrl = appUser.getUserAvatarUrl();

        Glide.with(MeAvatarShowerAty.this)
                .load(avatarUrl)
                .error(R.drawable.my)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatarImv);

        photoViewAttacher = new PhotoViewAttacher(avatarImv);
        photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String[] choices = {"保存至本地"};
                //包含多个选项的对话框
                AlertDialog dialog = new AlertDialog.Builder(MeAvatarShowerAty.this)
                        .setItems(choices, onselect).create();
                dialog.show();
                return true;
            }
        });
    }

    /*
     选项的事件监听器
     */
    DialogInterface.OnClickListener onselect = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    Bitmap bitmap = photoViewAttacher.getVisibleRectangleBitmap();
                    String savePath = FileUtil.saveBitmapToJpg(MeAvatarShowerAty.this, bitmap);
                    Toast.makeText(MeAvatarShowerAty.this,
                            "照片已保存（手机相册 -> AbsentM）",
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
}
