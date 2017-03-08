package com.example.dhanushsbhargav.colorme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Button;
import android.view.View;
import android.graphics.Color;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    long colormode;
    static boolean called=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final RelativeLayout dannysLayout = new RelativeLayout(this);
        final Button dannysButton = new Button(this);

        if(!called){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            called=true;
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
       final  DatabaseReference myRef = database.getReference();


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                colormode = (long) dataSnapshot.child("colormode").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dannysButton.setText("Change Color");
        RelativeLayout.LayoutParams buttonDet = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        if(colormode==2) {
            dannysLayout.setBackgroundColor(Color.BLUE);
        }
        else if(colormode==1){
            dannysLayout.setBackgroundColor(Color.RED);
        }


        buttonDet.addRule(RelativeLayout.CENTER_HORIZONTAL);
        buttonDet.addRule(RelativeLayout.CENTER_VERTICAL);
        dannysLayout.addView(dannysButton,buttonDet);
        setContentView(dannysLayout);

        dannysButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        if(colormode==1){
                            dannysLayout.setBackgroundColor(Color.BLUE);
                            colormode=2;
                            myRef.child("colormode").setValue(colormode);
                        }else if(colormode==2){
                            dannysLayout.setBackgroundColor(Color.RED);
                            colormode=1;
                            myRef.child("colormode").setValue(colormode);
                        }
                    }
                }
        );
    }
}
