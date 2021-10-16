package com.example.avishkar.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.avishkar.Fragments.CallsFragment;
import com.example.avishkar.Fragments.ChatsFragment;
import com.example.avishkar.Fragments.statusfragments;

public class FragmentAdapters extends FragmentPagerAdapter {
    public FragmentAdapters(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragmentAdapters(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new ChatsFragment();
            case 1:
                return new CallsFragment();
            case 2:
                return new statusfragments();
            default: return new ChatsFragment();

        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position ==0)
        {
            title="CHATS";
        }
        if(position==1)
        {
            title ="CALLS";
        }
        if(position==2)
        {
            title="STATUS";
        }

        return title;
    }
}
