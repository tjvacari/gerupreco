package com.vacari.gerupreco.repository;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.vacari.gerupreco.database.DatabaseManager;
import com.vacari.gerupreco.model.sqlite.Notification;

import java.util.List;

public class NotificationRepository {

    public static List<Notification> searchAllNotifications(Context context) {
        try {
            Dao<Notification, Integer> notificationDAO = DatabaseManager.getDB(context).getNotificationDAO();
            return notificationDAO.queryBuilder().orderBy("description", true).query();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveNotification(Context context, Notification notification) {
        try {
            Dao<Notification, Integer> notificationDAO = DatabaseManager.getDB(context).getNotificationDAO();
            notificationDAO.createOrUpdate(notification);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
