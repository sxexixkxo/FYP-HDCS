package com.example.fypsystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HelpfulAdvice extends AppCompatActivity implements View.OnClickListener{

    CardView bathroomCard, fittingRoomCard, hotelRoomCard, outdoorCard;
    Intent intent_bathroom, intent_fitting_room, intent_hotel_room, intent_outdoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpful_advice);

        bathroomCard= (CardView) findViewById(R.id.bathroom_card);
        fittingRoomCard= (CardView) findViewById(R.id.fitting_room_card);
        hotelRoomCard= (CardView) findViewById(R.id.hotel_room_card);
        outdoorCard= (CardView) findViewById(R.id.outdoor_card);

        bathroomCard.setOnClickListener(this);
        fittingRoomCard.setOnClickListener(this);
        hotelRoomCard.setOnClickListener(this);
        outdoorCard.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){

        switch(v.getId()){
            case R.id.bathroom_card:
                intent_bathroom= new Intent(this, Bathroom.class);
                startActivity(intent_bathroom);
                break;

            case R.id.fitting_room_card:
                intent_fitting_room= new Intent(this, FittingRoom.class);
                startActivity(intent_fitting_room);
                break;

            case R.id.hotel_room_card:
                intent_hotel_room= new Intent(this, Room.class);
                startActivity(intent_hotel_room);
                break;

            case R.id.outdoor_card:
                intent_outdoor= new Intent(this, Outdoor.class);
                startActivity(intent_outdoor);
                break;
        }
    }
}
