package ru.mirea.kichibekov.employeedb;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.kichibekov.employeedb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = App.getInstance().getDatabase();
        EmployeeDao employeeDao = db.employeeDao();
        //Employee employee = new Employee();
// запись сотрудников в базу
        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editId.getText().length() != 0 &
                        binding.editName.getText().length() != 0 &
                        binding.editSalary.getText().length() != 0) {
                    Employee employee = new Employee();
                    String id = String.valueOf(binding.editId.getText());
                    employee.id = Long.parseLong(id);
                    employee.name = String.valueOf(binding.editName.getText());
                    String salary = String.valueOf(binding.editSalary.getText());
                    employee.salary = Integer.parseInt(salary);
                    employeeDao.insert(employee);
                    Toast.makeText(getApplicationContext(), "Сохранено", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Вы не заполнили все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
// Загрузка всех работников
        binding.loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Employee> employees = employeeDao.getAll();
                List<String> names = new ArrayList<>();
                List<Long> ids = new ArrayList<>();
                List<String> salarys = new ArrayList<>();
                for (int i = 0; i < employees.size(); i++) {
                    names.add(employees.get(i).getName());
                    ids.add(employees.get(i).getId());
                    salarys.add(employees.get(i).getSalary());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, names);
                ArrayAdapter<Long> adapter2 = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, ids);
                ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, salarys);
                binding.listView.setAdapter(adapter);
                binding.listView3.setAdapter(adapter2);
                binding.listView2.setAdapter(adapter3);

            }
        });


// Получение определенного работника с id = 1
        binding.loadButtonById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editId.getText().length() != 0) {
                    Employee employee = new Employee();
                    String id = String.valueOf(binding.editId.getText());
                    employee = employeeDao.getById(Long.parseLong(id));
                    binding.editName.setText(employee.getName());
                    binding.editSalary.setText(employee.getSalary());
                } else {
                    Toast.makeText(getApplicationContext(), "Введите id в первой строке", Toast.LENGTH_SHORT).show();
                }
            }
        });
// Обновление полей объекта
        //employee.salary = 20000;
        //employeeDao.update(employee);
        //Log.d(TAG, employee.name + " " + employee.salary);
    }
}