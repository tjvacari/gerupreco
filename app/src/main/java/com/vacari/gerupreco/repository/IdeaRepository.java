package com.vacari.gerupreco.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vacari.gerupreco.model.firebase.Idea;
import com.vacari.gerupreco.model.firebase.Item;
import com.vacari.gerupreco.model.firebase.User;
import com.vacari.gerupreco.util.Callback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class IdeaRepository {

    public static void searchUser(Callback<List<User>> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<User> userTOList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            ObjectMapper objectMapper = new ObjectMapper();
                            User user = objectMapper.convertValue(data, User.class);
                            user.setId(document.getId());
                            userTOList.add(user);
                        }

                        userTOList.sort(Comparator.comparing(User::getName));
                        callback.callback(userTOList);
                    }
                });
    }

    public static void searchIdea(Callback<List<Idea>> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("idea")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Idea> ideaTOList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            ObjectMapper objectMapper = new ObjectMapper();
                            Idea idea = objectMapper.convertValue(data, Idea.class);
                            idea.setId(document.getId());
                            ideaTOList.add(idea);
                        }

                        callback.callback(ideaTOList);
                    }
                });
    }

    public static void save(Idea idea, Callback<Idea> callback) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> values = objectMapper.convertValue(idea, Map.class);
        values.remove("id");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("idea")
                .add(idea)
                .addOnSuccessListener(documentReference -> callback.callback(null))
                .addOnFailureListener(e -> {});
    }
}
