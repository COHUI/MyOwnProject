package com.huixinghua.criminalintent;

import androidx.fragment.app.Fragment;

import com.huixinghua.criminalintent.fragment.CrimeListFragment;

public class CrimeListActivity extends  SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
