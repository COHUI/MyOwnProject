package com.huixinghua.criminalintent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.huixinghua.criminalintent.fragment.CrimeFragment;

public  abstract class SingleFragmentActivity  extends AppCompatActivity {
   protected   abstract   Fragment  createFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);//相当于一个父容器，可以用来容纳各种碎片，加强了复用性

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
