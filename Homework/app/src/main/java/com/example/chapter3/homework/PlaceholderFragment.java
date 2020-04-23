package com.example.chapter3.homework;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;

public class PlaceholderFragment extends Fragment {
    private static final String Tag = "fregment";

    //使用有封装的ListView
    private ListView lvItems;
    private ArrayAdapter<Item> adapterItems;
    private View view;
    private LottieAnimationView animationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //先创建List
        ArrayList<Item> items = Item.getItems();
        adapterItems = new ArrayAdapter<Item>(getActivity(),
                android.R.layout.simple_list_item_activated_1, items);
        Log.d(Tag, "ArrayList 创建成功");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        view = inflater.inflate(R.layout.fragment_placeholder, container,
                false);
        Log.d(Tag, "fregment渲染成功");
        //绑定ListView
        lvItems = view.findViewById(R.id.lvItems);
        if(adapterItems == null)
            Log.d(Tag, "adapterItems is null");
        if(lvItems == null)
            Log.d(Tag, "lvItems is null");
        lvItems.setAdapter(adapterItems);
        Log.d(Tag, "ListView绑定成功");
        //播放动画
        animationView = view.findViewById(R.id.fragment_animation_view);
        animationView.setProgress(0f);
        animationView.setAlpha(1f);
        animationView.playAnimation();
        //隐藏列表
        lvItems.setAlpha(0f);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(Tag, "onActivityCreated is executing");
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(animationView,
                        "Alpha", 1f, 0f);
                animator1.setRepeatCount(0);
                animator1.setInterpolator(new LinearInterpolator());
                animator1.setDuration(1000);
                animator1.setRepeatMode(ObjectAnimator.REVERSE);

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(lvItems,
                        "Alpha", 0f, 1f);
                animator1.setRepeatCount(0);
                animator1.setInterpolator(new LinearInterpolator());
                animator1.setDuration(1000);
                animator1.setRepeatMode(ObjectAnimator.REVERSE);

                AnimatorSet animatorSet;
                animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator1, animator2);
                animatorSet.setDuration(1000);

                animatorSet.start();

                animationView.setAlpha(0f);
                animationView.pauseAnimation();

                Log.d(Tag, "postDelay is executing");
            }
        }, 5000);
    }
}
