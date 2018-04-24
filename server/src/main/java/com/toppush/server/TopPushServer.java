package com.toppush.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiki on 2017/6/1.
 */

public class TopPushServer {
    private static List<TopPushServerManager> managers = new ArrayList<>();


    public static void addPushServerManager(TopPushServerManager serverManager) {
        for(TopPushServerManager item : managers){
            // 避免重复添加
            if (item.getClass().equals(serverManager.getClass())){
                return;
            }
        }
        managers.add(serverManager);
    }


    public static void sendMessageToAlias(List<String> alias, String messagePayload) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendMessageToAlias(alias, messagePayload);
        }
    }


    public static void sendMessageToTags(List<String> tags, String messagePayload) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendMessageToTags(tags, messagePayload);
        }
    }
    public static void sendMessageToAll(String messagePayload) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendMessageToAll(messagePayload);
        }
    }

    public static void sendNotifyToAlias(List<String> alias, String title, String description, String messagePayload) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendNotifyToAlias(alias, title, description, messagePayload);
        }
    }


    public static void sendNotifyToTags(List<String> tags, String title, String description, String messagePayload) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendNotifyToTags(tags, title, description, messagePayload);
        }
    }

    public static void sendNotifyToAll(String title, String description, String messagePayload) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendNotifyToAll(title, description, messagePayload);
        }
    }




    public static void sendLinkNotifyToAlias(List<String> alias, String title, String description, String url) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendLinkNotifyToAlias(alias, title, description, url);
        }
    }

    public static void sendLinkNotifyToTags(List<String> tags, String title, String description, String url) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendLinkNotifyToTags(tags, title, description, url);
        }
    }

    public static void sendLinkNotifyToAll(String title, String description, String url) throws Exception {
        for (TopPushServerManager item : managers) {
            item.sendLinkNotifyToAll(title, description, url);
        }
    }


}
