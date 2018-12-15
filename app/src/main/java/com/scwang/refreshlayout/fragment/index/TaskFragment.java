package com.scwang.refreshlayout.fragment.index;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.scwang.refreshlayout.R;
import com.scwang.refreshlayout.activity.Award.MainActivity;
import com.scwang.refreshlayout.activity.FragmentActivity;

import com.scwang.refreshlayout.activity.MenuButton.SrcMenu;
import com.scwang.refreshlayout.activity.Task.ProfilePracticeActivity;
import com.scwang.refreshlayout.activity.Task.DayTaskActivity;

import com.scwang.refreshlayout.adapter.BaseRecyclerAdapter;
import com.scwang.refreshlayout.adapter.SmartViewHolder;
import com.scwang.refreshlayout.countDown.CountdownActivity;
import com.scwang.refreshlayout.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.R.layout.simple_list_item_2;
import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class TaskFragment extends Fragment implements AdapterView.OnItemClickListener {

    private SrcMenu mSrcMenu;
    private TextView textView;
    private int stars;
    private AVObject avObject;

    private enum Item {
        Task(R.string.index_practice_repast, DayTaskActivity.class),
        Profile(R.string.index_practice_profile, ProfilePracticeActivity.class),
        ;
        @StringRes
        public int name;
        public Class<?> clazz;
        Item(@StringRes int name, Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_refresh_practive, container, false);
        initView(view);
        return view;
    }
    private void initView(View view) {
        String title= (String) getArguments().get("scores");
        textView= (TextView) view.findViewById(R.id.scores_tv);
        textView.setText(title);
    }


    @Override
    public void onViewCreated(@NonNull View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        StatusBarUtil.setPaddingSmart(getContext(), root.findViewById(R.id.toolbar));

        View view = root.findViewById(R.id.recyclerView);
        if (view instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), VERTICAL));
            recyclerView.setAdapter(new BaseRecyclerAdapter<Item>(Arrays.asList(Item.values()), simple_list_item_2,this) {
                @Override
                protected void onBindViewHolder(SmartViewHolder holder, Item model, int position) {
                    holder.text(android.R.id.text1, model.name());
                    holder.text(android.R.id.text2, model.name);
                    holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
                }
            });
        }
        mSrcMenu = (SrcMenu) root.findViewById(R.id.src_menu);
        mSrcMenu.setOnMenuItemClickListener(new SrcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case 1:
                        //
                        break;
                    case 2:
                       //
                        break;
                    case 3:
//                        Toast.makeText(TaskFragment.this,"",Toast.LENGTH_LONG).show();
                        break;
                    case 4:
                        startActivity(new Intent(getContext(),CountdownActivity.class));
                        break;
                }
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = Item.values()[position];
        if (Activity.class.isAssignableFrom(item.clazz)) {
            startActivity(new Intent(getContext(), item.clazz));
        } else if (Fragment.class.isAssignableFrom(item.clazz)) {
            FragmentActivity.start(this, item.clazz);
        }
    }

}
