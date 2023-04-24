package ru.mirea.kichibekov.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ru.mirea.kichibekov.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences sharedPref =
                getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("NUMBER", String.valueOf(binding.myNumber.getText()));
                editor.putString("GROUP", String.valueOf(binding.numberGroup.getText()));
                editor.putString("FAVOURITE FILM", String.valueOf(binding.favouriteMovie.getText()));
                editor.apply();
                Toast toast = Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        String group = sharedPref.getString("GROUP", "");
        String number = sharedPref.getString("NUMBER", "");
        String film = sharedPref.getString("FAVOURITE FILM", "");

        System.out.println("group " + group);
        binding.myNumber.setText(number);
        binding.numberGroup.setText(group);
        binding.favouriteMovie.setText(film);
    }
}