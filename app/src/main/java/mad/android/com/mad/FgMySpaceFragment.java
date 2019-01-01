package mad.android.com.mad;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FgMySpaceFragment extends Fragment {

    public static final int MY_FVR=0;
    public static final int MY_CMT=1;
    public static final int MY_ATC=2;
    public static final int MY_TAG=3;
    private List<Fragment> fragments=new ArrayList<>();
    private List<String> fragmentTitles=new ArrayList<>();
    private TabLayout tab_my_space;
    private ViewPager vp_my_space;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fg_my_space_list,null);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        //tab_my_space=(TabLayout) view.findViewById(R.id.tab_my_space);
        //vp_my_space=(ViewPager) view.findViewById(R.id.vp_my_space);
        setViewPager();
        vp_my_space.setOffscreenPageLimit(2);
        tab_my_space.setupWithViewPager(vp_my_space);
    }

    private void setViewPager(){
        fragments.add(FgMySpaceListFragment.newInstance(MY_FVR));
        fragments.add(FgMySpaceListFragment.newInstance(MY_CMT));
        fragments.add(FgMySpaceListFragment.newInstance(MY_ATC));
        fragments.add(FgMySpaceListFragment.newInstance(MY_TAG));
        fragmentTitles.add("收藏");
        fragmentTitles.add("评论");
        fragmentTitles.add("影评");
        fragmentTitles.add("标签");
        MyFragmentAdapter adapter=new MyFragmentAdapter(getChildFragmentManager(),fragments,fragmentTitles);
        vp_my_space.setAdapter(adapter);
    }
}
