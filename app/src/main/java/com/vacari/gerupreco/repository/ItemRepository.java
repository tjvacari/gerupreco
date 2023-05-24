package com.vacari.gerupreco.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.vacari.gerupreco.model.firebase.Item;
import com.vacari.gerupreco.util.Callback;

import java.util.ArrayList;
import java.util.Comparator;
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

                        itemTOList.sort(Comparator.comparing(Item::getDescription).thenComparing(Item::getSize));
                        callback.callback(itemTOList);
                    }
                });
    }

    public static void save(Item item, Callback<Item> callback) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> values = objectMapper.convertValue(item, Map.class);
        values.remove("id");

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

    public static void update(Item item, Callback<Item> callback) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> values = objectMapper.convertValue(item, Map.class);
        values.remove("id");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("item").document(item.getId())
                .update(values)
                .addOnSuccessListener(aVoid -> callback.callback(null))
                .addOnFailureListener(e -> {});
    }
}
