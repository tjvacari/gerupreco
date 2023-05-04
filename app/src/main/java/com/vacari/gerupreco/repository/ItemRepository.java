package com.vacari.gerupreco.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vacari.gerupreco.model.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemRepository {

    public static void searchItem(CallbackRepo<List<Item>> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("item")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Item> itemTOList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> data = document.getData();
                            ObjectMapper objectMapper = new ObjectMapper();
                            Item item = objectMapper.convertValue(data, Item.class);
                            itemTOList.add(item);
                        }
                        callback.callback(itemTOList);
                    }
                });
    }

    public static void save(Map<String, Object> item, CallbackRepo<Item> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("item")
                .add(item)
                .addOnSuccessListener(documentReference -> callback.callback(null))
                .addOnFailureListener(e -> {});
    }
}
