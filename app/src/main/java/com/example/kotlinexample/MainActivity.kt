package com.example.kotlinexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val foodList = arrayListOf("smth1", "smth2", "smth3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_selected_food.text = getString(R.string.texas_chicken)
        button_decide.setOnClickListener {
            val random = Random()
            val randomFood = random.nextInt(foodList.count())
            text_selected_food.text = foodList[randomFood]
        }

        button_add_food.setOnClickListener {
            val newFood = edit_text_add_food.text.toString()
            foodList.add(newFood)
            edit_text_add_food.text.clear()
        }
    }
}
