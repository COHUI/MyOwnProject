package com.huixinghua.criminalintent.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huixinghua.criminalintent.CrimeActivity;
import com.huixinghua.criminalintent.CrimePagerActivity;
import com.huixinghua.criminalintent.R;
import com.huixinghua.criminalintent.bean.Crime;
import com.huixinghua.criminalintent.bean.CrimeLab;

import java.util.List;

public class CrimeListFragment extends Fragment {
private RecyclerView  mCrimeRecyclerView;
private   CrimeAdapter  mAdapter;
private static   final  int REQUEST_CRIME=1;
private static   final  String SAVED_SUBTITLE_VISIBLE="subtitle";
private boolean  mSubtitleVisible;//用户点击show  subtitle时，重建工具栏，可以解决旋转后文字消失
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.fragment_crime_list,container,false);
        mCrimeRecyclerView=(RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (savedInstanceState!=null){
            mSubtitleVisible=savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();
        return   view;
    }
    //定义ViewHolder内部类，ViewHolder用来容纳view视图的
    private class  CrimeHolder extends  RecyclerView.ViewHolder   implements View.OnClickListener{
      private TextView  mTitleTextView;
      private  TextView  mDateTextView;
      private ImageView   mSolvedImageView;
      private Crime  mCrime;
        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.list_item_crime,parent,false));//基类ViewHolder实际引用这个list_item_crime这个视图，如果需要，可以在ViewHolder的itemView变量里找到它
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView=(TextView)itemView.findViewById(R.id.crime_date);
            mSolvedImageView=(ImageView)itemView.findViewById(R.id.crime_solved);
        }

       public   void bind(Crime  crime){
            mCrime=crime;
           mTitleTextView.setText(mCrime.getTitle());
            mDateTextView.setText(mCrime.getDate().toString());
            mSolvedImageView.setVisibility(crime.isSolved()?View.VISIBLE:View.GONE);
       }
        @Override
        public void onClick(View view) {
      /*      Toast.makeText(getActivity(),
                            mCrime.getTitle() + " clicked!", Toast.LENGTH_SHORT)
                    .show();*/
      /*      Intent intent = new Intent(getActivity(), CrimeActivity.class);//需要的Context对象由CrimeListFragment通过使用getActivity()方法传入它托管的activity来满足的
            startActivity(intent);*/

           // Intent intent = CrimeActivity.newIntent(getActivity(),mCrime.getId());


          //  startActivity(intent);
            //startActivityForResult(intent,REQUEST_CRIME);
            Intent  intent = CrimePagerActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }


    }

    //创建Adapter内部类      Adapter负责创建必要的ViewHolder,绑定ViewHolder至模型层数据
    private  class  CrimeAdapter  extends   RecyclerView.Adapter<CrimeHolder>{
        private  List<Crime>  mCrimes;
        public CrimeAdapter(List<Crime> crimes){
            mCrimes=crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          LayoutInflater  layoutInflater=LayoutInflater.from(getActivity());
          return   new CrimeHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            Crime crime = mCrimes.get(position);
             holder.bind(crime);
        }
    public   void setCrimes(List<Crime> crimes){
            mCrimes=crimes;
    }
        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    private   void updateUI(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        List<Crime> crimes = crimeLab.getCrimes();
        if (mAdapter==null){
            mAdapter = new CrimeAdapter(crimes);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setCrimes(crimes);
            mAdapter.notifyDataSetChanged();
        }
        updateSubtitle();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
//onSaveInstanceState()方法是在Android活动（Activity）被暂停或销毁之前调用的。
// 它通常用于保存和恢复与活动相关联的临时状态数据，以便在活动恢复时可以重新创建该状态。
// 这种情况可能发生在设备配置更改（例如屏幕旋转）或系统内存不足时。
// 当调用onSaveInstanceState()方法时，您应该将需要保持的数据写入Bundle对象中，
// 并返回该Bundle对象作为方法的结果。稍后，在活动重新创建时，
// 您可以使用onCreate()或onRestoreInstanceState()方法来恢复保存的状态数据。
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
     outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list,menu);
        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible){
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);//首先展示这个
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Crime crime = new Crime();
                CrimeLab.get(getActivity()).addCrime(crime);
                Intent intent = CrimePagerActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible=!mSubtitleVisible;
               getActivity().invalidateOptionsMenu();//更新标题是hide还是show
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void updateSubtitle(){
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        int crimeCount=crimeLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);
        if (!mSubtitleVisible){
            subtitle=null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
  if (requestCode==REQUEST_CRIME){

  }

    }
}
