package ui.web.taf.utils;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailUtils {

    private Store store;
    private Folder inbox;

    /**
     * Connects to the email server.
     *
     * @param host     The email server host (e.g., "imap.gmail.com").
     * @param username The email account username.
     * @param password The email account password.
     */
    public void connect(String host, String username, String password) {
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            Session emailSession = Session.getDefaultInstance(properties);
            store = emailSession.getStore("imaps");
            LoggingUtils.info("Connecting to email host: " + host);
            store.connect(host, username, password);
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
        } catch (MessagingException e) {
            LoggingUtils.error("Failed to connect to email server: " + e.getMessage());
            throw new RuntimeException("Failed to connect to email server", e);
        }
    }

    /**
     * Gets the latest unread email with a specific subject.
     *
     * @param subject The subject of the email to look for.
     * @return The latest unread email message, or null if not found.
     */
    public Message getLatestEmail(String subject) {
        try {
            Message[] unreadMessages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            for (int i = unreadMessages.length - 1; i >= 0; i--) {
                Message message = unreadMessages[i];
                if (message.getSubject().contains(subject)) {
                    LoggingUtils.info("Found email with subject: " + subject);
                    return message;
                }
            }
            LoggingUtils.warn("No unread email found with subject: " + subject);
            return null;
        } catch (MessagingException e) {
            LoggingUtils.error("Failed to get latest email: " + e.getMessage());
            throw new RuntimeException("Failed to get latest email", e);
        }
    }

    /**
     * Extracts the first link found in the email body.
     *
     * @param email The email message.
     * @return The extracted URL as a string, or null if no link is found.
     */
    public String extractLinkFromEmail(Message email) {
        try {
            String content = (String) email.getContent();
            Pattern linkPattern = Pattern.compile("href=\"(.*?)\"");
            Matcher matcher = linkPattern.matcher(content);
            if (matcher.find()) {
                String link = matcher.group(1);
                LoggingUtils.info("Extracted link from email: " + link);
                return link;
            }
            LoggingUtils.warn("No link found in the email.");
            return null;
        } catch (Exception e) {
            LoggingUtils.error("Failed to extract link from email: " + e.getMessage());
            throw new RuntimeException("Failed to extract link from email", e);
        }
    }

    /**
     * Verifies if the email content contains the expected text.
     *
     * @param email        The email message.
     * @param expectedText The text to search for in the email body.
     * @return True if the text is found, false otherwise.
     */
    public boolean verifyEmailContent(Message email, String expectedText) {
        try {
            String content = (String) email.getContent();
            boolean found = content.contains(expectedText);
            LoggingUtils.info("Verifying email content. Found '" + expectedText + "': " + found);
            return found;
        } catch (Exception e) {
            LoggingUtils.error("Failed to verify email content: " + e.getMessage());
            throw new RuntimeException("Failed to verify email content", e);
        }
    }

    /**
     * Closes the connection to the email server.
     */
    public void disconnect() {
        try {
            if (inbox != null && inbox.isOpen()) {
                inbox.close(false);
            }
            if (store != null && store.isConnected()) {
                store.close();
            }
            LoggingUtils.info("Email connection closed.");
        } catch (MessagingException e) {
            LoggingUtils.error("Failed to close email connection: " + e.getMessage());
        }
    }
}
