package com.zybang.testadbdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.logging.Logger

/**
 * create by shenqinwei on 2023/10/19
 * tip:
 */
class TestCmdActivity : AppCompatActivity() {

    val command26 = "echo 1193046 > /sys/wiegand/wiegand/wiegand26"
    val command34 = "echo  897634759 > /sys/wiegand/wiegand/wiegand34"
    val command36 = "echo  897634759 1 > /sys/wiegand/wiegand/wiegand36"
    val command66 = "echo  1311768467463790320 > /sys/wiegand/wiegand/wiegand66"

    val look_command26 = "cat /sys/wiegand/wiegand/wiegand26"
    val look_command34 = "cat /sys/wiegand/wiegand/wiegand34"
    val look_command36 = "cat /sys/wiegand/wiegand/wiegand36"
    val look_command66 = "cat /sys/wiegand/wiegand/wiegand66"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testcmd)
        val btn_send26 = findViewById<Button>(R.id.btn_send26)
        val btn_send34 = findViewById<Button>(R.id.btn_send34)
        val btn_send36 = findViewById<Button>(R.id.btn_send36)
        val btn_send66 = findViewById<Button>(R.id.btn_send66)
        val btn_look26 = findViewById<Button>(R.id.btn_look26)
        val btn_look34 = findViewById<Button>(R.id.btn_look34)
        val btn_look36 = findViewById<Button>(R.id.btn_look36)
        val btn_look66 = findViewById<Button>(R.id.btn_look66)
        val btn_su = findViewById<Button>(R.id.btn_su)
        btn_su.setOnClickListener{
            // 执行su指令获取root权限
            val suCommand = "su"
            runCommand(suCommand)
            //execRootCmd(suCommand)
        }
        btn_send26.setOnClickListener {


            // 执行命令
            val output = runCommand(command26)
            println(output)
        }
        btn_look26.setOnClickListener {
            val output = runCommand(look_command26)
            println(output)
        }
        btn_send34.setOnClickListener {


            GlobalScope.launch(context = Dispatchers.IO) {
                //延时一秒
                val output = runCommand(command34)
              //  val output = execRootCmd(command34)
                println(output.toString())
            }
        }

        btn_look34.setOnClickListener {

            GlobalScope.launch(context = Dispatchers.IO) {
                //延时一秒
                val output = runCommand(look_command34)
               // val output = execRootCmd(look_command34)
                println(output)
            }



        }
        btn_send36.setOnClickListener {
            val output = runCommand(command36)
            println(output)
        }

        btn_look36.setOnClickListener {
            val output = runCommand(look_command36)
            println(output)
        }
        btn_send66.setOnClickListener {
            val output = runCommand(command66)
            println(output)
        }

        btn_look66.setOnClickListener {
            val output = runCommand(look_command66)
            println(output)
        }


    }
    

        val result = StringBuilder()

        fun runCommand(cmd: String): Boolean {
            var bufferedReader: BufferedReader? = null
            var dos: DataOutputStream? = null
            var receive = ""

            try {
                Runtime.getRuntime().exec("su")?.run { // 经过Root处理的android系统即有su命令
                    Log.d("MainActivity.TAG","Cmd run: $cmd")
                    bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    dos = DataOutputStream(outputStream).apply {
                        writeBytes(cmd + "\n")
                        flush()
                        writeBytes("exit\n")
                        flush()
                    }
                    bufferedReader?.run {
                        while (readLine().also { receive = it } != null) {
                            result.append("\n").append(receive)
                        }
                    }

                    waitFor()
                }
            } catch (e: Exception) {
                println("==----$e.message")
                return false
            }

            try {
                dos?.close()
                bufferedReader?.close()
            } catch (e: Exception) {
                println("1111==----$e.message")
                return false
            }

            return true
        }

}