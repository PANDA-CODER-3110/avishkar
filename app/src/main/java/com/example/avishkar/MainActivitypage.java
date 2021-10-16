package com.example.avishkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.avishkar.Adapters.FragmentAdapters;
import com.example.avishkar.databinding.ActivityMainActivitypageBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivitypage extends AppCompatActivity {

    ActivityMainActivitypageBinding binding;
FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainActivitypageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth =FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentAdapters(getSupportFragmentManager()));
        binding.Tablayout.setupWithViewPager(binding.viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent i =new Intent(MainActivitypage.this,SettingActivity.class);
                startActivity(i);
                break;

            case R.id.logout:
mauth.signOut();
                Intent intent = new Intent(MainActivitypage.this,SigninActivity.class);
                startActivity(intent);


                break;
            case R.id.chatRoom:

                Intent intent1= new Intent(MainActivitypage.this,GroupChatActivity.class);
                startActivity(intent1);

                break;
        }



        return true;
    }
}