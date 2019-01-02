package mad.android.com.mad.register_login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import mad.android.com.mad.R;
import mad.android.com.mad.beans.AppUser;

public class RegisterActivity extends Activity {
    private static final String LOG_MSG = "RegisterActivity";

    boolean isHidden = true;

    private AppUser appUser;
    //控件初始化
    private ImageButton titleImv;
    private TextView titleCenterTv;
    private TextView titleRightTv;
    private EditText nickNameEt;
    private EditText emailAddressEt;
    private EditText passwordEt;
    private ImageView nickNameWarnImv;
    private ImageView emailWarnImv;
    private ImageView passwordEyeImv;
    private Button registerBtn;

    private String nickNameStr;
    private String emailAddressStr;
    private String passwordStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        Bmob.initialize(this, "acbc897f711009c562745ec99aacfd49");

        initView(); // 初始化界面控件

        userRegister();  // 用户注册

    }

    private void initView() {
        titleImv = (ImageButton) findViewById(R.id.title_imv);
        titleImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.this.finish();
            }
        });

        titleCenterTv = (TextView) findViewById(R.id.title_center_text_tv);
        titleCenterTv.setText("用户注册");

        titleRightTv = (TextView) findViewById(R.id.title_right_text_tv);
        titleRightTv.setVisibility(View.GONE);

        nickNameEt = (EditText) findViewById(R.id.nickname_et);
        emailAddressEt = (EditText) findViewById(R.id.email_et);
        passwordEt = (EditText) findViewById(R.id.passwd_et);

        nickNameWarnImv = (ImageView) findViewById(R.id.reg_nickname_warning_imv);
        emailWarnImv = (ImageView) findViewById(R.id.reg_email_warning_imv);
        passwordEyeImv = (ImageView) findViewById(R.id.reg_password_view_off_imv);

        registerBtn = (Button) findViewById(R.id.register_btn);
    }

    private void userRegister() {
        passwordEyeImv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //实现密码是否可见控件的处理

                if(isHidden){
                    //不需要见密码
                    passwordEyeImv.setImageResource(R.drawable.ic_visibility_off);

                    passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                else{
                    //需要见密码
                    passwordEyeImv.setImageResource(R.drawable.ic_visibility);
                    passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

                isHidden = !isHidden;
                passwordEt.postInvalidate();

                CharSequence charSequence = passwordEt.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spannable = (Spannable) charSequence;
                    Selection.setSelection(spannable, charSequence.length());
                }
            }
        });

        //注册控件处理
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickNameStr = nickNameEt.getText().toString().trim();
                emailAddressStr = emailAddressEt.getText().toString().trim();
                passwordStr = passwordEt.getText().toString().trim();

                if (nickNameStr.equals("")) {
                    nickNameWarnImv.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "请输入昵称!", Toast.LENGTH_LONG).show();
                } else if ("".equals(emailAddressStr)) {
                    emailWarnImv.setVisibility(View.VISIBLE);
                    Toast.makeText(RegisterActivity.this, "请输入注册邮箱!", Toast.LENGTH_LONG).show();
                } else if ("".equals(passwordStr)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码!", Toast.LENGTH_LONG).show();
                } else if (!"".equals(nickNameStr) && !"".equals(emailAddressStr) && !"".equals(passwordStr)) {
                    nickNameWarnImv.setVisibility(View.GONE);
                    emailWarnImv.setVisibility(View.GONE);

                    appUser = new AppUser();
                    appUser.setUserNickName(nickNameStr);
                    appUser.setUsername(emailAddressStr);
                    appUser.setPassword(passwordStr);
                    appUser.setEmail(emailAddressStr);

                    appUser.signUp(new SaveListener<AppUser>() {
                        @Override
                        public void done(AppUser appUser, BmobException e) {
                            if (e == null) {
                                Log.i(LOG_MSG, "$$$$$$: 注册成功");
                                Toast.makeText(RegisterActivity.this, "注册成功!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(RegisterActivity.this, "注册失败! " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

    }

}
