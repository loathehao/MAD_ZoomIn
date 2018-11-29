package mad.android.com.mad;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FgMySpaceListFragment extends Fragment {
    private int type;
    private TextView text_my_space;

    public static FgMySpaceListFragment newInstance(int type){
        Bundle args=new Bundle();
        FgMySpaceListFragment fragment=new FgMySpaceListFragment();
        args.putInt("type",type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fg_my_space_list,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        type=getArguments().getInt("type");
        text_my_space=view.findViewById(R.id.text_my_space);
        switch(type){
            case FgMySpaceFragment.MY_FVR:
                text_my_space.setText("收藏");
                break;
            case FgMySpaceFragment.MY_CMT:
                text_my_space.setText("评论");
                break;
            case FgMySpaceFragment.MY_ATC:
                text_my_space.setText("影评");
                break;
            case FgMySpaceFragment.MY_TAG:
                text_my_space.setText("标签");
                break;
        }
    }
}
