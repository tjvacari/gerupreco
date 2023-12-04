package com.vacari.gerupreco.activity.idea;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.vacari.gerupreco.R;
import com.vacari.gerupreco.model.firebase.Idea;
import com.vacari.gerupreco.model.firebase.User;
import com.vacari.gerupreco.repository.IdeaRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RegisterIdeaActivity extends AppCompatActivity {

    private List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_idea);

        configureActions();

        IdeaRepository.searchUser(data -> {
            setUsers(data);
        });
    }

    public void configureActions() {
        Button btnSalvar = findViewById(R.id.btn_save_idea);
        btnSalvar.setOnClickListener(v -> {
            save();
        });
    }

    private void save() {
        EditText editIdea = findViewById(R.id.edit_idea);
        Spinner spUser = findViewById(R.id.sp_user_idea);

        User selected = users.get(0);
        String user = spUser.getSelectedItem().toString();
        for(User u : users) {
            if(u.getName().equals(user)) {
                selected = u;
                break;
            }
        }

        Idea idea = new Idea();
        idea.setDate(new Date().getTime());
        idea.setDescription(editIdea.getText().toString());
        idea.setUser(selected);

        IdeaRepository.save(idea, c -> {
            RegisterIdeaActivity.this.finish();
        });
    }

    private void setUsers(List<User> data) {
        users = data;
        runOnUiThread(() -> {
            List<String> usersList = new ArrayList<>();

            for(User u : data) {
                usersList.add(u.getName());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, usersList);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            Spinner editUnitMeasure = findViewById(R.id.sp_user_idea);
            editUnitMeasure.setAdapter(adapter);
            editUnitMeasure.setSelection(0);
        });
    }
}