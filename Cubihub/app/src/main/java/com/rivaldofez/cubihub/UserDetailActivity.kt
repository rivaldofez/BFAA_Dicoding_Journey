package com.rivaldofez.cubihub

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.databinding.ActivityUserDetailBinding
import com.rivaldofez.cubihub.model.User
import java.lang.Exception

class UserDetailActivity : AppCompatActivity() {
    lateinit var user:User;
    private lateinit var binding:ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        binding.btnEmail.setOnClickListener {
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
        binding.btnShare.setOnClickListener {
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

        binding.btnDial.setOnClickListener {
            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${user.phone}"))
            startActivity(dialPhoneIntent)
        }
    }

    private fun initView() {
        user = intent.getParcelableExtra<User>("Key") as User

        binding.tvFullname.text = user.fullname
        binding.tvUsername.text = user.username
        binding.tvAddress.text = user.address
        binding.tvAboutMe.text = user.biography
        binding.tvFollower.text = user.num_follower.toString()
        binding.tvFollowing.text = user.num_following.toString()
        binding.tvRepository.text = "${user.num_repository.toString()} Repo"

        val imageResource = resources.getIdentifier(user.avatar,null, packageName)
        Glide.with(applicationContext).load(imageResource).into(binding.imgContent)

        binding.btnDial.setText(user.phone)
    }
}