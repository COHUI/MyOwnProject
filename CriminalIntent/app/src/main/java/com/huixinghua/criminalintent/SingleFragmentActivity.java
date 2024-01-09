package com.huixinghua.criminalintent;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.huixinghua.criminalintent.fragment.CrimeFragment;

public  abstract class SingleFragmentActivity  extends AppCompatActivity {
   protected   abstract   Fragment  createFragment();
     @LayoutRes
   protected  int getLayoutResId(){//如果不想使用固定的R.layout.activity_fragment，子类可以覆盖这个方法返回所需的布局
       return  R.layout.activity_fragment;
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_fragment);//相当于一个父容器，可以用来容纳各种碎片，加强了复用性
      setContentView(getLayoutResId());
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment==null){
         /*   fragment=new CrimeFragment();//这里可以优化，复用代码*/
           fragment=createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
}
