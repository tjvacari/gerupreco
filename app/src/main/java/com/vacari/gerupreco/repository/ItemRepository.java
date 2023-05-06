package com.vacari.gerupreco.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vacari.gerupreco.model.Item;
import com.vacari.gerupreco.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemRepository {

    public static void searchItem(Callback<List<Item>> callback) {
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
                            item.setId(document.getId());
                            itemTOList.add(item);
                        }
                        callback.callback(itemTOList);
                    }
                });
    }

    public static void save(Map<String, Object> item, Callback<Item> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("item")
                .add(item)
                .addOnSuccessListener(documentReference -> callback.callback(null))
                .addOnFailureListener(e -> {});
    }

    public static void delete(String id, Callback<Item> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("item").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> callback.callback(null))
                .addOnFailureListener(e -> {});
    }
}
