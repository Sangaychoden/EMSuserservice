package bt.edu.gcit.emsusermicroservice.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
