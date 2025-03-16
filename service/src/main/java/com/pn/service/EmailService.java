package com.pn.service;


import java.io.File;
import java.util.List;

public interface EmailService {
    void send(String name, String form, String to, String subject, String content, Boolean isHtml, String cc, String bcc, List<File> files);

    void sendArticlePaid(String username, String title, Long receiveId);
}
