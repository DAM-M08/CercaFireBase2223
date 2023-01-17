package net.vidalibarraquer.profe.cercafirebase2223;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity  {

    public TextView tvData;
    public Spinner cel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvData = findViewById(R.id.tvData);
        cel = findViewById(R.id.cel);

        cel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("prediccions");
                StringBuffer cerca = new StringBuffer();
                ref.orderByChild("cel").equalTo(cel.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            System.out.println(userSnapshot.getKey());
                            cerca.append(userSnapshot.getKey()+" ");
                            System.out.println(userSnapshot.child("cel").getValue(String.class));
                            cerca.append(userSnapshot.getKey()+" "+userSnapshot.child("cel").getValue(String.class)+"\n");
                            tvData.setText(cerca);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        throw databaseError.toException();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

    }
}

