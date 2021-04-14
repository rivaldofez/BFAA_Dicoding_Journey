package com.rivaldofez.cubihub

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.model.User
import kotlinx.android.synthetic.main.activity_user_detail.*
import java.lang.Exception

class UserDetailActivity : AppCompatActivity() {
    lateinit var user:User;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)

        initView()
        btn_email.setOnClickListener {
            val emailSendIntent = Intent(Intent.ACTION_SEND)
            emailSendIntent.data = Uri.parse("mailto:")
            emailSendIntent.type ="text/plain"
            emailSendIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
            emailSendIntent.putExtra(Intent.EXTRA_SUBJECT, "Judul Email")
            emailSendIntent.putExtra(Intent.EXTRA_TEXT, "Pesan Email")

            try {
                startActivity(Intent.createChooser(emailSendIntent, "Choose Email Provider"))
            }catch (e : Exception){
                Toast.makeText(this, e.message , Toast.LENGTH_SHORT).show()
            }
        }
        btn_share.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(Intent.EXTRA_TEXT, user.fullname)
            shareIntent.type = "text/plain"
            try {
                startActivity(Intent.createChooser(shareIntent, "Share To..."))
            }catch (e : Exception){
                Toast.makeText(this, e.message ,Toast.LENGTH_SHORT).show()
            }
        }

        btn_dial.setOnClickListener {
            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${user.phone}"))
            startActivity(dialPhoneIntent)
        }
    }

    private fun initView() {
        user = intent.getParcelableExtra<User>("Key") as User

        tv_fullname.text = user.fullname
        tv_username.text = user.username
        tv_address.text = user.address
        tv_about_me.text = user.biography
        tv_follower.text = user.num_follower.toString()
        tv_following.text = user.num_following.toString()
        tv_repository.text = "${user.num_repository.toString()} Repo"

        val imageResource = resources.getIdentifier(user.avatar,null, packageName)
        Glide.with(applicationContext).load(imageResource).into(img_content)

        btn_dial.setText(user.phone)
    }
}