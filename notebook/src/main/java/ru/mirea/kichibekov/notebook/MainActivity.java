package ru.mirea.kichibekov.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import kotlin.text.Charsets;
import ru.mirea.kichibekov.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExternalStorageWritable();
                writeFileToExternalStorage();
                Toast.makeText(MainActivity.this, "Сохранено", Toast.LENGTH_SHORT).show();
            }
        });
        binding.loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExternalStorageReadable();
                readFileFromExternalStorage();
            }
        });
    }

    /* Проверяем хранилище на доступность чтения и записи*/
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    /* Проверяем внешнее хранилище на доступность чтения */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void writeFileToExternalStorage() {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        String fileName = String.valueOf(binding.nameFile.getText());
        File file = new File(path, fileName + ".txt");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
            OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);
// Запись строки в файл
            String quote = String.valueOf(binding.quoteEdit.getText());
            output.write(quote);
// Закрытие потока записи
            output.close();
        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        }
    }

    public void readFileFromExternalStorage() {
        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        String fileName = String.valueOf(binding.nameFile.getText());
        File file = new File(path, fileName + ".txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());

            InputStreamReader inputStreamReader = new InputStreamReader(
                    fileInputStream,
                    StandardCharsets.UTF_8);

            List<String> lines = new ArrayList<String>();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
            Log.w("ExternalStorage", String.format("Read from file %s successful", lines.toString()));
            binding.quoteEdit.setText(lines.toString());
        } catch (Exception e) {
            Log.w("ExternalStorage", String.format("Read from file %s failed", e.getMessage()));
        }
    }
}