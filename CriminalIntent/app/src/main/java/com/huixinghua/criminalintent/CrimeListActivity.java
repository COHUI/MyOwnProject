package com.huixinghua.criminalintent;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.huixinghua.criminalintent.bean.Crime;
import com.huixinghua.criminalintent.fragment.CrimeFragment;
import com.huixinghua.criminalintent.fragment.CrimeListFragment;

public class CrimeListActivity extends  SingleFragmentActivity  implements CrimeListFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }


    @Override
    public void onCrimeSelected(Crime crime) {
if (findViewById(R.id.detail_fragment_container)==null){
    Intent  intent=CrimePagerActivity.newIntent(this,crime.getId());
    startActivity(intent);
}else {
    Fragment  newDetail=CrimeFragment.newInstance(crime.getId());
    FragmentManager fm=getSupportFragmentManager();
    fm.beginTransaction()
            .add(R.id.detail_fragment_container,newDetail)
            .commit();

}
    }

    @Override
    protected int getLayoutResId(){
      //  return R.layout.activity_twopane;
        return R.layout.activity_masterdetail;
    }
}
