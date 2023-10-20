package com.zybang.testadbdemo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;


import com.zybang.testadbdemo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "CsdnApplication AdbRunActivity";

    private EditText cmdEt;

    private Button runCmdBt;

    private String result;
// 测试指令 重启 adb shell reboot

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        runCmdBt = findViewById(R.id.activity_adb_run_cmd_bt);
        cmdEt =findViewById(R.id.activity_adb_run_cmd_et);

        clickEvent();
    }
    private void clickEvent() {
        runCmdBt.setOnClickListener(view -> {
            /*result = runCmd(cmdEt.getText().toString());
            showToast();
            Log.d(TAG, "run result: " + result);*/
            Intent intent = new Intent(this,TestCmdActivity.class);
            startActivity(intent);

        });
    }

    private void showToast() {
        Log.i(TAG, "show Toast");
        String toastText = "执行失败，请检查shell命令!";
        if (!TextUtils.isEmpty(result)) {
            toastText = "执行成功!";
        }
        Toast.makeText(this,toastText, Toast.LENGTH_SHORT).show();
    }

    private String runCmd(String cmd) {
        Log.d(TAG, "runCmd: " + cmd);
        if (TextUtils.isEmpty(cmd) ) {
            return "";
        }

        return execRootCmd(cmd);
    }

    /**
     * 执行命令并且输出结果
     */
    public static String execRootCmd(String cmd) {
        String content = "";
        try {
            cmd = cmd.replace("adb shell","");
            Process process = Runtime.getRuntime().exec(cmd);
            Log.d(TAG,"process " + process.toString());
            content = process.toString();
        } catch (IOException e) {
            Log.d(TAG,"exception " + e.toString());
            e.printStackTrace();
        }
        return content;
    }



}