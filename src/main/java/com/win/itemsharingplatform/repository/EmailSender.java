package com.win.itemsharingplatform.repository;

import com.win.itemsharingplatform.model.User;

public interface EmailSender {
    void send(String to, String email);
    String buildEmail(String name, String link);
    void generateLinkAndSend(User user, String token);
}
