package com.michael.todolist;

import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michael.todolist.Adapter.MyDoListAdapter;
import com.michael.todolist.Model.ListUtils;
import com.michael.todolist.Model.TypeUtils;
import com.michael.todolist.Module.getListUtils;
import com.michael.todolist.Module.translate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context mContext;
    private RecyclerView recycleView;
    private ArrayList<ListUtils> arrayList;
    private PopupWindow TypePopupView;
    private int typePopWindowStatus;
    private boolean fabOpened = false;
    private FloatingActionButton fab;
    private TextView headView;
    private RelativeLayout liner_add;
    private RelativeLayout liner_set;
    private int morePopWindowStatus;
    private PopupWindow MorePopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        mContext = this;
        fab = (FloatingActionButton) findViewById(R.id.fab);
        liner_add = (RelativeLayout)findViewById(R.id.liner_fab_add);
        liner_set = (RelativeLayout)findViewById(R.id.liner_fab_set);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fabOpened){
                    closeFloatingMenu(view);
                }
                else{
                    showFloatingMenu(view);
                }
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);
        initRecycleView();
    }

    private void closeFloatingMenu(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"rotation",-135,20,0);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        recycleViewShow();
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(700);
        alphaAnimation.setFillAfter(true);
//        floatingEdit.startAnimation(alphaAnimation);
//        floatingSet.startAnimation(alphaAnimation);
//        textEdit.startAnimation(alphaAnimation);
//        textSet.startAnimation(alphaAnimation);
//        floatingEdit.setVisibility(View.GONE);
//        floatingEdit.setVisibility(View.GONE);
//        textEdit.setVisibility(View.GONE);
//        textSet.setVisibility(View.GONE);
        liner_add.startAnimation(alphaAnimation);
        liner_set.startAnimation(alphaAnimation);
        liner_add.setVisibility(View.GONE);
        liner_set.setVisibility(View.GONE);
        fabOpened = false;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
    }

    private void showFloatingMenu(View view) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"rotation",0,-155,-135);
        objectAnimator.setDuration(700);
        objectAnimator.start();
        recycleViewDismiss();
//        floatingEdit.setVisibility(View.VISIBLE);
//        floatingEdit.setVisibility(View.VISIBLE);
//        textEdit.setVisibility(View.VISIBLE);
//        textSet.setVisibility(View.VISIBLE);
        liner_add.setVisibility(View.VISIBLE);
        liner_set.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setFillAfter(true);
//        floatingEdit.startAnimation(alphaAnimation);
//        floatingSet.startAnimation(alphaAnimation);
//        textSet.startAnimation(alphaAnimation);
//        textEdit.startAnimation(alphaAnimation);
        liner_add.startAnimation(alphaAnimation);
        liner_set.startAnimation(alphaAnimation);
        fabOpened = true;
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
    }

    private void initRecycleView() {
        recycleView = (RecyclerView)findViewById(R.id.recycleView);
        arrayList =getListUtils.getList();
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        headView = (TextView) findViewById(R.id.card_top_title);
        assert recycleView != null;
        assert headView != null;
        final MyDoListAdapter myDoListAdapter = new MyDoListAdapter(arrayList,mContext);
        recycleView.setAdapter(myDoListAdapter);
        typePopWindowStatus =0;
        myDoListAdapter.setClickListener(new MyDoListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                switch (view.getId()){
                    case R.id.more:
                        if(fabOpened){
                            closeFloatingMenu(fab);
                            break;
                        }
                        if(morePopWindowStatus==1){
                            morePopWindowStatus = 0;
                            break;
                        }
                        initMorePopupMenu(view,position);
                        break;
                    case R.id.type_name:
                        if(fabOpened){
                            closeFloatingMenu(fab);
                            break;
                        }
                        if(typePopWindowStatus==1){
                            typePopWindowStatus = 0;
                            break;
                        }
                        initTypePopupView(view,position);
                        break;
                    case R.id.recy_item:
                        if(fabOpened){
                            closeFloatingMenu(fab);
                        }
                        break;
                    default:
                        if(fabOpened){
                            closeFloatingMenu(fab);
                            break;
                        }
                        Toast.makeText(mContext,""+position,Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(fabOpened){
                    closeFloatingMenu(fab);
                }
                // Get the sticky information from the topmost view of the screen.
                View stickyInfoView = recyclerView.findChildViewUnder(headView.getMeasuredWidth(),5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    headView.setText(" "+String.valueOf(stickyInfoView.getContentDescription()).trim());
                    headView.setTranslationY(0);
                }

                // Get the sticky view's translationY by the first view below the sticky's height.
                View transInfoView = recyclerView.findChildViewUnder(
                        headView.getMeasuredWidth() / 2, headView.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                   // Log.d("11111",transViewStatus+"");
                    int dealtY = transInfoView.getTop() - headView.getMeasuredHeight();
                    if (transViewStatus == MyDoListAdapter.HEAD) {
                        //Log.d("11111","int:"+transViewStatus+"");
                        // If the first view below the sticky's height scroll off the screen,
                        // then recovery the sticky view's translationY.
                        if (dealtY> 0) {//transInfoView.getTop()>0 出现了一个bug，会使中间段非leader也上移
                            headView.setTranslationY(dealtY);
                     //       Log.d("11111",dealtY+"");
                        } else {
                            headView.setTranslationY(0);
                        }
                    } else if (transViewStatus == MyDoListAdapter.NO_HEAD) {
                        headView.setTranslationY(0);
                    }
                }
            }
        });

    }

    private void initMorePopupMenu(View view, int position) {
        View popView = getMoreView(view);
        MorePopupMenu = new PopupWindow(popView, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        MorePopupMenu.setTouchable(true);
        MorePopupMenu.setOutsideTouchable(true);
        MorePopupMenu.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(),(Bitmap)null));
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        recycleViewDismiss();
        MorePopupMenu.showAsDropDown(view,-80,0);
        morePopWindowStatus = 1;
        MorePopupMenu.setOnDismissListener(new poponDismissListener());
    }

    private View getMoreView(View view) {
        View moreView = LayoutInflater.from(mContext).inflate(R.layout.more_popup,null);
        LinearLayout liner_add = (LinearLayout)moreView.findViewById(R.id.more_add_alarm);
        LinearLayout liner_set = (LinearLayout)moreView.findViewById(R.id.more_set_alarm);
        LinearLayout liner_del = (LinearLayout)moreView.findViewById(R.id.more_delete_alarm);
//        TextView alarm_add = (TextView) view.findViewById(R.id.alarm_add);
//        alarm_add.setOnClickListener(new MoreClick());
//        TextView alarm_set = (TextView) view.findViewById(R.id.alarm_set);
//        alarm_set.setOnClickListener(new MoreClick());
//        TextView alarm_del = (TextView) view.findViewById(R.id.alarm_del);
//        alarm_del.setOnClickListener(new MoreClick());
        liner_add.setOnClickListener(new MoreClick());
        liner_set.setOnClickListener(new MoreClick());
        liner_del.setOnClickListener(new MoreClick());
        return moreView;
    }

    private void initTypePopupView(View view,int position) {
        View popView = getTypeView(view,position);
        TypePopupView = new PopupWindow(popView, LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        TypePopupView.setTouchable(true);
        TypePopupView.setOutsideTouchable(true);
        TypePopupView.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), (Bitmap) null));
        TypePopupView.setAnimationStyle(R.style.Type_Animation_Style);
        recycleViewDismiss();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        TypePopupView.showAtLocation(view, Gravity.NO_GRAVITY,location[0]+120,location[1]-7);
        typePopWindowStatus =1;
        TypePopupView.setOnDismissListener(new poponDismissListener());
    }

    private void recycleViewDismiss() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(recycleView,"alpha",1.0f,0.1f);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(headView,"alpha",1.0f,0.1f);
        objectAnimator1.setDuration(500);
        objectAnimator1.start();

    }

    private View getTypeView(View view, int position) {
        View typeView = LayoutInflater.from(mContext).inflate(R.layout.type_popup, null);
        TextView type1 = (TextView)typeView.findViewById(R.id.type_name_select1);
        TextView typedes1 =(TextView)typeView.findViewById(R.id.type_des1);
        TextView type2 = (TextView)typeView.findViewById(R.id.type_name_select2);
        TextView typedes2 =(TextView)typeView.findViewById(R.id.type_des2);
        TextView type3 = (TextView)typeView.findViewById(R.id.type_name_select3);
        TextView typedes3 =(TextView)typeView.findViewById(R.id.type_des3);
        TextView type4 = (TextView)typeView.findViewById(R.id.type_name_select4);
        TextView typedes4 =(TextView)typeView.findViewById(R.id.type_des4);
        TypeUtils typeUtils = new TypeUtils(arrayList.get(position).type);
        ArrayList<String> typeList = typeUtils.getList();
        type1.setText(typeList.get(0));
        type2.setText(typeList.get(1));
        type3.setText(typeList.get(2));
        type4.setText(typeList.get(3));
        typedes1.setText(translate.typeNameChange(typeList.get(0)));
        typedes2.setText(translate.typeNameChange(typeList.get(1)));
        typedes3.setText(translate.typeNameChange(typeList.get(2)));
        typedes4.setText(translate.typeNameChange(typeList.get(3)));
        type1.setBackgroundResource(translate.typeChange(typeList.get(0)));
        type2.setBackgroundResource(translate.typeChange(typeList.get(1)));
        type3.setBackgroundResource(translate.typeChange(typeList.get(2)));
        type4.setBackgroundResource(translate.typeChange(typeList.get(3)));
        type1.setOnClickListener(new TypeClick());
        type2.setOnClickListener(new TypeClick());
        type3.setOnClickListener(new TypeClick());
        type4.setOnClickListener(new TypeClick());
        return typeView;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.main_search);
        //searchItem.expandActionView();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        ComponentName componentName = getComponentName();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(componentName));
        searchView.setQueryHint(getString(R.string.search_note));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void backgroundAlpha(float bgAlpha)
    {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
    if (id == R.id.nav_alarm) {
    } else if (id == R.id.nav_back) {

    } else if (id == R.id.nav_chart) {

    } else if (id == R.id.nav_help) {

    } else if (id == R.id.nav_cloud) {

    } else if (id == R.id.nav_encourage) {

    }
        else if(id==R.id.nav_noted){}
        else if(id==R.id.nav_setting){}
        else if(id==R.id.nav_trash){}
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
}


    private class TypeClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.type_name_select1:
                    TypePopupView.dismiss();
                    break;
                case R.id.type_name_select2:
                    TypePopupView.dismiss();
                    break;
                case R.id.type_name_select3:
                    TypePopupView.dismiss();
                    break;
                case R.id.type_name_select4:
                    TypePopupView.dismiss();
                    break;
                default:
                    break;
            }
        }
    }

    private class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            recycleViewShow();
        }
    }

    private void recycleViewShow() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(recycleView, "alpha", 0.1f, 1.0f);
        objectAnimator.setDuration(500);
        objectAnimator.start();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(headView, "alpha", 0.1f, 1.0f);
        objectAnimator1.setDuration(500);
        objectAnimator1.start();
    }

    private class MoreClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.more_add_alarm:
                    MorePopupMenu.dismiss();
                    break;
                case R.id.more_set_alarm:
                    MorePopupMenu.dismiss();
                    break;
                case R.id.more_delete_alarm:
                    MorePopupMenu.dismiss();
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
