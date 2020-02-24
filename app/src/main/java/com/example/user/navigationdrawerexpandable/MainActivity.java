package com.example.user.navigationdrawerexpandable;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.user.navigationdrawerexpandable.Adapter.CustomExpandableListAdapter;
import com.example.user.navigationdrawerexpandable.Helper.FragmentNavigationManager;
import com.example.user.navigationdrawerexpandable.Interface.NavigationManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> lstTitle;
    private Map<String ,List<String>> lstChild;
    private NavigationManager navigationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);


        //Init view
        mDrawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle=getTitle().toString();
        expandableListView=(ExpandableListView)findViewById(R.id.navList);
        navigationManager=FragmentNavigationManager.getmInstance(this);
        
        initItem();

       /* Display newDisplay = getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();
        expandableListView.setIndicatorBounds(width-5, width);*/
//expandableListView.setIndicatorBounds(140, 150);


        View listHeaderView= getLayoutInflater().inflate(R.layout.nav_header,null,false);
        expandableListView.addHeaderView(listHeaderView);

        genData();

        addDrawersItem();
        setupDrawer();

        if (savedInstanceState == null)
            selectFirstItemAsDefault();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Bipul Sarkar");
    }

    //Button navigation Bar
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch(item.getItemId()){
                case R.id.add:
                    Toast.makeText(MainActivity.this, "You have clicked at add bottom", Toast.LENGTH_SHORT).show();
                    return true;
                case  R.id.setting:
                        Toast.makeText(MainActivity.this, "You have clicked at setting botton", Toast.LENGTH_SHORT).show();
                        return  true;
                case  R.id.share:
                    Toast.makeText(MainActivity.this,"You have clicked at share bottom",Toast.LENGTH_LONG).show();
                    return true;

                case R.id.folder:
                    Toast.makeText(MainActivity.this, "You have clicked at folder botton", Toast.LENGTH_SHORT).show();
                    return true;
            }
                return  false;
        }
    };



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if(navigationManager !=null)
        {
            String firstItem = lstTitle.get(0);
            navigationManager.showFragment(firstItem);
            getSupportActionBar().setTitle(firstItem);
        }

    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // getSupportActionBar().setTitle("Bipul Sarkar");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
               // getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    private void addDrawersItem() {
        adapter=new CustomExpandableListAdapter(this,lstTitle,lstChild);
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(lstTitle.get(groupPosition).toString());//set title for Toolbar when open menu
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Bipul Sarkar");
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //change fragment when click on item
                String selectedItem=((List)(Objects.requireNonNull(lstChild.get(lstTitle.get(groupPosition)))))
                        .get(childPosition).toString();

                Objects.requireNonNull(getSupportActionBar()).setTitle(selectedItem);

                if (items[0].equals(lstTitle.get(groupPosition)))
                    navigationManager.showFragment(selectedItem);
              /*  else
                    throw new IllegalArgumentException("Not support fragment");
                */
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

    }

    private void genData() {
         List<String> title=Arrays.asList(" Barishal Division"," Chittagong Division", " Dhaka Division", " Khulna Division", " Mymensingh Division"," Rajshahi Division"," Rangpur Division"," Sylhet Division");
       // List<String> title=Arrays.asList( "  Android Programming","  Xamarin Programming","  iOS Programming");
       List<String> childitem1=Arrays.asList( "  Beginner","  Intermediate","  Advanced","  Profession");
         List<String> childitem2=Arrays.asList( "  Dhaka","  Thakurgoan","  Barisal","  Dinajpur");
        List<String> childitem3=Arrays.asList( "  Khulna","  Ponchogor","  Nilfamari","  Profession");
        List<String> childitem4=Arrays.asList( "  a","  b","  c","  d");
     /*   List<String> childitem5=Arrays.asList( "  e","  f","  g","  h");
        List<String> childitem6=Arrays.asList( "  i","  j","  k","  l");
        List<String> childitem7=Arrays.asList( "  m","  n","  o","  p");
        List<String> childitem8=Arrays.asList( "  q","  r","  s","  t");*/

        lstChild=new TreeMap<>();
        lstChild.put(title.get(0),childitem1);
        lstChild.put(title.get(1),childitem2);
        lstChild.put(title.get(2),childitem3);
        lstChild.put(title.get(3),childitem4);
      /*  lstChild.put(title.get(4),childitem5);
        lstChild.put(title.get(5),childitem6);
        lstChild.put(title.get(6),childitem7);
        lstChild.put(title.get(7),childitem8);*/
        lstTitle=new ArrayList<>(lstChild.keySet());

    }
  /*  private void initItem() {
        items=new String[] { " Android Programming"," Xamarin Programming"," iOS Programming"};
    }*/

    private void initItem() {
        items = new String[]{" Barishal Division", " Chittagong Division", " Dhaka Division", " Khulna Division", " Mymensingh Division", " Rajshahi Division", " Rangpur Division", " Sylhet Division"};
        }

            @Override
            public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main_manu, menu);
            return true;
        }

            @Override
            public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            if (mDrawerToggle.onOptionsItemSelected(item))
                return true;
            return super.onOptionsItemSelected(item);
        }

    }