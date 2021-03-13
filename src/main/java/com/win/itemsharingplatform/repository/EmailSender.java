package com.win.itemsharingplatform.repository;

public interface EmailSender {
    void send(String to, String email);
}
