package com.ensoft.lesson35

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException

class MainActivity : AppCompatActivity() {

    val list: MutableList<Item> = ArrayList()
    val horizontalList: MutableList<HorizontalItem> = ArrayList()
    lateinit var horizontalRvAdapter: HorizontalRecyclerAdapter
    lateinit var recyclerview: RecyclerView
    lateinit var horizontalRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById(R.id.recyclerview)
        horizontalRecyclerView = findViewById(R.id.horizontalRecyclerView)

        for (i in 0 until 20){
            list.add(Item("Item $i"))
        }
        val rvAdapter = RecyclerViewAdapter(list)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = rvAdapter

        initHorizontalListItems()

        horizontalRvAdapter = HorizontalRecyclerAdapter(horizontalList)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        horizontalRecyclerView.layoutManager = layoutManager
        horizontalRecyclerView.adapter = horizontalRvAdapter

        horizontalRvAdapter.setOnItemClickedListener(object :
            HorizontalRecyclerAdapter.OnItemClickedListener {
            override fun onItemClicked(position: Int) {
                if (position == 0) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(Intent.createChooser(intent, "Select picture"), 100)
                }else{
                    Toast.makeText(
                        applicationContext,
                        horizontalList[position].title,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        })

    }
    private fun initHorizontalListItems(){
        horizontalList.add(HorizontalItem("Gallery", resources.getDrawable(R.drawable.ic_gallery)))
        horizontalList.add(HorizontalItem("Austria", resources.getDrawable(R.drawable.austria)))
        horizontalList.add(HorizontalItem("California", resources.getDrawable(R.drawable.california)))
        horizontalList.add(HorizontalItem("Indonesia", resources.getDrawable(R.drawable.indonesia)))
        horizontalList.add(HorizontalItem("Thailand", resources.getDrawable(R.drawable.thailand)))
        horizontalList.add(HorizontalItem("Usa", resources.getDrawable(R.drawable.usa)))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK){
            if (data != null){
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(application.contentResolver, data.data)
                    val drawable = BitmapDrawable(resources, bitmap)
                    horizontalList.add(1, HorizontalItem("Item", drawable))
                    horizontalRvAdapter.notifyDataSetChanged()

                }catch (ioException: IOException){
                    ioException.printStackTrace()
                }
            }
        }

    }
}