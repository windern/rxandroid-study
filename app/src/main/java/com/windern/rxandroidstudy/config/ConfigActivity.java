package com.windern.rxandroidstudy.config;

import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.windern.rxandroidstudy.R;
import com.windern.rxandroidstudy.databinding.ActivityConfigBinding;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;

public class ConfigActivity extends AppCompatActivity {
    ActivityConfigBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_config);

        test();
    }

    public void test() {
        Flowable.just("哈哈").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                binding.tvTest.setText(s);
            }
        });
    }
}
