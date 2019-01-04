package mad.android.com.mad.myInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import mad.android.com.mad.R;
import mad.android.com.mad.bean.AppUser;
import mad.android.com.mad.register_login.LoginActivity;
import mad.android.com.mad.util.FileUtil;

//信息界面Activity
public class MeInfoActivity extends Activity {
    private static final String LOG = "MeInfoActivity";
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";
    private static final int RESULT_CODE = 1;
    private static final int REQUEST_CODE_NICKNAME_1 = 1;
    private static final int REQUEST_CODE_MESSAGE_8 = 8;
    private static final int REQUEST_CODE_CAPTURE_IMAGE_10 = 10;
    private static final int REQUEST_CODE_ALBUM_11 = 11;
    private static final int REQUEST_CODE_CROP_12 = 12;

    // 界面控件初始化声明
    private TextView logOut;
    private RelativeLayout meDetailtAvatarRout;
    private ImageView meDetailtAvatarImv;
    private CustomAvatarDialog customAvatarDialog;
    private String avatarBmobPathStr;

    private RelativeLayout meDetailtNickNameRout;
    private TextView meDetailtNickNameTv;

    private RelativeLayout meDetailtSexRout;
    private TextView meDetailtSexTv;
    private String changedSexStr;

    private RelativeLayout meDetailtBirthRout;
    private TextView meDetailtBirthTv;
    private String changedBirthStr;
    private Calendar calendar = Calendar.getInstance();
    private int currentYear;
    private int currentMonth;
    private int currentDay;

    private TextView meDetailtEmailTv;

    private RelativeLayout meDetailtMessageRout;
    private TextView meDetailtMessageTv;

    private final static int[] dayArr = new int[]{20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22};
    private final static String[] constellationArr = new String[]{"摩羯座", "水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座"};
    private Intent mPicToView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_space);

        initViews();
        fillUserDatas();
        eventsDeal();
    }

    private void initViews() {
        ImageButton titleImv = (ImageButton) findViewById(R.id.title_imv);
        titleImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUser appUser = BmobUser.getCurrentUser(AppUser.class);
                String avatarUrl = appUser.getUserAvatarUrl();
                Log.i(LOG, "avatarUrl" + avatarUrl);

                String nicknameStr = meDetailtNickNameTv.getText().toString();
                String messageStr = meDetailtMessageTv.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("MeInfoDetailActivity.nicknameStr", nicknameStr);
                intent.putExtra("MeInfoDetailActivity.messageStr", messageStr);
                intent.putExtra("MeInfoDetailActivity.avatarUrl", avatarUrl);
                MeInfoActivity.this.setResult(RESULT_CODE, intent);

                MeInfoActivity.this.finish();
            }
        });
        TextView titleCenterTv = (TextView) findViewById(R.id.title_center_text_tv);
        titleCenterTv.setText("个人资料");

        TextView titleRightTv = (TextView) findViewById(R.id.title_right_text_tv);
        titleRightTv.setVisibility(View.GONE);

        meDetailtAvatarRout = (RelativeLayout) findViewById(R.id.me_detailt_avatar_rout);
        meDetailtAvatarImv = (ImageView) findViewById(R.id.me_detail_avatar_imv);

        meDetailtNickNameRout = (RelativeLayout) findViewById(R.id.me_detail_nickname_rout);
        meDetailtNickNameTv = (TextView) findViewById(R.id.me_detail_nickname_tv);

        meDetailtSexRout = (RelativeLayout) findViewById(R.id.me_detail_sex_rout);
        meDetailtSexTv = (TextView) findViewById(R.id.me_detail_sex_tv);

        meDetailtBirthRout = (RelativeLayout) findViewById(R.id.me_detail_birth_rout);
        meDetailtBirthTv = (TextView) findViewById(R.id.me_detail_birth_tv);

        RelativeLayout meDetailtEmailRout = (RelativeLayout) findViewById(R.id.me_detail_email_rout);
        meDetailtEmailTv = (TextView) findViewById(R.id.me_detail_email_tv);

        meDetailtMessageRout = (RelativeLayout) findViewById(R.id.me_deatil_message_rout);
        meDetailtMessageTv = (TextView) findViewById(R.id.me_detail_message_tv);

        logOut = findViewById(R.id.logout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUser.logOut();
                startActivity(new Intent(MeInfoActivity.this,LoginActivity.class));
                finish();
            }
        });

    }

    private void fillUserDatas() {
        AppUser appUser = BmobUser.getCurrentUser(AppUser.class);

        if (appUser != null) {
            meDetailtNickNameTv.setText(appUser.getUserNickName());
            meDetailtEmailTv.setText(appUser.getEmail());

            Log.i(LOG, "appUser.getUserAvatarUrl() = " + appUser.getUserAvatarUrl());

            if ("".equals(appUser.getUserAvatarUrl()) || null == appUser.getUserAvatarUrl()) {
                meDetailtAvatarImv.setImageResource(R.drawable.my);
            } else {
                Glide.with(MeInfoActivity.this)
                        .load(appUser.getUserAvatarUrl())
                        .error(R.drawable.my)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(meDetailtAvatarImv);
            }

            Log.i(LOG, ">>> appUser.getUserSex(): " + appUser.getUserSex());
            if ("".equals(appUser.getUserSex()) || appUser.getUserSex() == null) {
                meDetailtSexTv.setText("未设置");
            } else {
                meDetailtSexTv.setText(appUser.getUserSex());
            }

            if ("".equals(appUser.getUserBirthday()) || appUser.getUserBirthday() == null) {
                meDetailtBirthTv.setText("未设置");
            } else {
                meDetailtBirthTv.setText(appUser.getUserBirthday());
            }

            if ("".equals(appUser.getUserMessage()) || null == appUser.getUserMessage()) {
                meDetailtMessageTv.setText("什么都没写过。");
            } else {
                meDetailtMessageTv.setText(appUser.getUserMessage());
            }
        }
    }

    private static String getConstellation(int month, int day) {
        return day < dayArr[month - 1] ? constellationArr[month - 1] : constellationArr[month];
    }

    private void eventsDeal() {
        avatarClickEvents();
        nickNameClickEvents();
        sexClickEvents();
        birthDayClickEvents();
        messageClickEvents();

    }

    private void avatarClickEvents() {
        meDetailtAvatarRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customAvatarDialog = new CustomAvatarDialog(MeInfoActivity.this,
                        onAvatarClickListener);
                customAvatarDialog.show();
            }
        });

        meDetailtAvatarImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeInfoActivity.this, MeAvatarShowerAty.class));
            }
        });
    }

    private View.OnClickListener onAvatarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.me_editor_avatar_takephoto_btn:
                    customAvatarDialog.dismiss();
                    captureImageEvents();
                    break;
                case R.id.me_editor_avatar_album_btn:
                    customAvatarDialog.dismiss();
                    selectAlbumEvents();
                    break;
                default:
                    break;
            }
        }
    };

    /*
     获取相机服务
     */
    private void captureImageEvents() {
        Intent intentCaptureImage = new Intent("android.media.action.IMAGE_CAPTURE");
        intentCaptureImage.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        IMAGE_FILE_NAME)));
        try {
            startActivityForResult(intentCaptureImage, REQUEST_CODE_CAPTURE_IMAGE_10);
        } catch (Exception e) {
            Toast.makeText(MeInfoActivity.this, "无相机服务", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     获取相册服务
     */
    private void selectAlbumEvents() {
        Intent intentAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        intentAlbum.addCategory(Intent.CATEGORY_OPENABLE);
        intentAlbum.setType("image/*");
        intentAlbum.putExtra("return-data", true);
        startActivityForResult(intentAlbum, REQUEST_CODE_ALBUM_11);
    }

    private void nickNameClickEvents() {
        meDetailtNickNameRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nicknameStr = meDetailtNickNameTv.getText().toString();

                Intent intent1 = new Intent();
                intent1.putExtra("MeInfoDetailActivity.nickname", nicknameStr);
                intent1.setClass(MeInfoActivity.this, MeEditorNicknameAty.class);
                MeInfoActivity.this.startActivityForResult(intent1, REQUEST_CODE_NICKNAME_1);
            }
        });
    }

    /*
     "性别"的点击事件处理
     */
    private void sexClickEvents() {
        meDetailtSexRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] sex = {"保密", "男", "女"};
                int sexFlag;
                String currentSexStr = meDetailtSexTv.getText().toString();

                switch (currentSexStr) {
                    case "男":
                        sexFlag = 1;
                        break;
                    case "女":
                        sexFlag = 2;
                        break;
                    default:
                        sexFlag = 0;
                        break;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MeInfoActivity.this);
                builder.setTitle("请选择性别");
                builder.setSingleChoiceItems(sex, sexFlag, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        changedSexStr = sex[which];
                    }
                });

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppUser newAppUser = new AppUser();
                        newAppUser.setUserSex(changedSexStr);
                        AppUser currentAppUser = BmobUser.getCurrentUser(AppUser.class);
                        newAppUser.update(currentAppUser.getObjectId(),
                                new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            meDetailtSexTv.setText(changedSexStr);
                                            Toast.makeText(MeInfoActivity.this, "修改成功!",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MeInfoActivity.this, "Error: " +
                                                            e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    /*
     "生日"的点击事件处理
     */
    private void birthDayClickEvents() {
        meDetailtBirthRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 初始化设置
                String getItemTime = meDetailtBirthTv.getText().toString();
                if (getItemTime.equals("未设置")) {
                    currentYear = calendar.get(Calendar.YEAR);
                    currentMonth = calendar.get(Calendar.MONTH);
                    currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                    Log.i(LOG, "currentYear0 = " + currentYear);
                    Log.i(LOG, "currentMonth0 = " + currentMonth);
                    Log.i(LOG, "currentDay0 = " + currentDay);
                } else {
                    String[] itemStr = getItemTime.split("    ");
                    Log.i(LOG, "itemStr[0] = " + itemStr[0]);
                    String birthStr[] = itemStr[0].split("-");

                    currentYear = Integer.parseInt(birthStr[0]);
                    currentMonth = Integer.parseInt(birthStr[1]);
                    currentDay = Integer.parseInt(birthStr[2]);

                    Log.i(LOG, "currentYear1 = " + currentYear);
                    Log.i(LOG, "currentMonth1 = " + currentMonth);
                    Log.i(LOG, "currentDay1 = " + currentDay);
                }

                final DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MeInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view,
                                                  int year,
                                                  int monthOfYear,
                                                  int dayOfMonth) {
                                changedBirthStr = String.valueOf(year) + "-" +
                                        String.valueOf(monthOfYear + 1) + "-" +
                                        String.valueOf(dayOfMonth) + "    " +
                                        getConstellation(monthOfYear + 1, dayOfMonth + 1);

                                Log.i(LOG, "year = " + year);
                                Log.i(LOG, "monthOfYear = " + monthOfYear);
                                Log.i(LOG, "dayOfMonth = " + dayOfMonth);
                                Log.i(LOG, "changedBirthStr = " + changedBirthStr);

                                AppUser newAppUser = new AppUser();
                                newAppUser.setUserBirthday(changedBirthStr);
                                AppUser currentAppUser = BmobUser.getCurrentUser(AppUser.class);
                                newAppUser.update(currentAppUser.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            meDetailtBirthTv.setText(changedBirthStr);
                                            Toast.makeText(MeInfoActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(MeInfoActivity.this, "ERROR! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        },
                        currentYear, currentMonth - 1, currentDay);
                datePickerDialog.show();
            }
        });
    }

    private void messageClickEvents() {
        meDetailtMessageRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageStr = meDetailtMessageTv.getText().toString();

                Intent intent1 = new Intent();
                intent1.putExtra("MeInfoDetailActivity.messageStr", messageStr);
                intent1.setClass(MeInfoActivity.this, MeEditorMessageAty.class);
                MeInfoActivity.this.startActivityForResult(intent1, REQUEST_CODE_MESSAGE_8);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NICKNAME_1:
                    String nicknameStr = data.getStringExtra("MeEditorNicknameAty.nickname");
                    Log.i(LOG, "MeInfo, nicknameStr = " + nicknameStr);
                    Log.i(LOG, "MeInfo, data = " + data);
                    meDetailtNickNameTv.setText(nicknameStr);
                    break;

                case REQUEST_CODE_MESSAGE_8:
                    String messageStr = data.getStringExtra("MeEditorMessageAty.messageStr");
                    Log.i(LOG, "MeInfo, nicknameStr = " + messageStr);
                    Log.i(LOG, "MeInfo, data = " + data);
                    meDetailtMessageTv.setText(messageStr);
                    break;

                case REQUEST_CODE_CAPTURE_IMAGE_10:
                    File temp = new File(Environment.getExternalStorageDirectory() + File.separator
                            + IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(temp));
                    break;

                case REQUEST_CODE_ALBUM_11:
                    try {
                        startPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        Log.i(LOG, e.getMessage());
                    }
                    break;

                case REQUEST_CODE_CROP_12:   // 取得裁剪后的图片
                    if (data != null) {
                        setPicToView(data);
                        Log.i(LOG, ">> " + REQUEST_CODE_CROP_12);
                    }
                    break;

                default:
                    break;
            }
        }
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");  // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("aspectX", 1);   // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);  // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputY", 300);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", false);
        startActivityForResult(intent, REQUEST_CODE_CROP_12);
    }

    /*
     保存裁剪之后的图片数据
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        Log.i(LOG, ">> " + extras.toString());
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap bmobAvatarBitmap = extras.getParcelable("data");
            String avatarFilePathStr = FileUtil.saveFile(MeInfoActivity.this,
                    "tempAvatar.jpg", bmobAvatarBitmap);
            Log.i(LOG, "avatarFilePathStr = " + avatarFilePathStr);
            final BmobFile bmobFile = new BmobFile(new File(avatarFilePathStr));
            bmobFile.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        avatarBmobPathStr = bmobFile.getFileUrl();
                        Log.i(LOG, "getFileUrl = " + avatarBmobPathStr);

                        // 修改云端头像地址
                        AppUser newAppUser = new AppUser();
                        newAppUser.setUserAvatarUrl(avatarBmobPathStr);
                        Log.i(LOG, "avatarBmobPathStr = " + avatarBmobPathStr);
                        AppUser currentAppUser = BmobUser.getCurrentUser(AppUser.class);
                        newAppUser.update(currentAppUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e != null) {
                                    Toast.makeText(MeInfoActivity.this, "Error! " +
                                                    e.getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(MeInfoActivity.this, "头像上传失败, 请稍后再试!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            meDetailtAvatarImv.setImageBitmap(bmobAvatarBitmap);
        }
    }

}
