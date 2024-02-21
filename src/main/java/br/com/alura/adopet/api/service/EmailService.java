package br.com.alura.adopet.api.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void enviarEmail(String to, String subject, String message);
}