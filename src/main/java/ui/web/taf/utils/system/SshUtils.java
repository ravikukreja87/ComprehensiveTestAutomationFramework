package ui.web.taf.utils.system;

import com.jcraft.jsch.*;
import ui.web.taf.utils.core.LoggingUtils;

import java.io.InputStream;

public class SshUtils {

    private Session session;

    /**
     * Connects to a remote server via SSH.
     *
     * @param host     The server hostname or IP address.
     * @param user     The username for authentication.
     * @param password The password for authentication.
     */
    public void connect(String host, String user, String password) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            LoggingUtils.info("Establishing SSH connection to " + host);
            session.connect();
        } catch (JSchException e) {
            LoggingUtils.error("SSH connection failed: " + e.getMessage());
            throw new RuntimeException("SSH connection failed", e);
        }
    }

    /**
     * Executes a command on the remote server.
     *
     * @param command The command to execute.
     * @return The command output.
     */
    public String executeCommand(String command) {
        if (session == null || !session.isConnected()) {
            throw new IllegalStateException("SSH session is not connected.");
        }
        StringBuilder output = new StringBuilder();
        try {
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            InputStream in = channel.getInputStream();
            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    output.append(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    if (in.available() > 0) continue;
                    LoggingUtils.info("Exit status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
        } catch (Exception e) {
            LoggingUtils.error("Failed to execute command: " + e.getMessage());
            throw new RuntimeException("Failed to execute command", e);
        }
        return output.toString();
    }

    /**
     * Uploads a file to the remote server.
     *
     * @param localPath  The path to the local file.
     * @param remotePath The destination path on the remote server.
     */
    public void uploadFile(String localPath, String remotePath) {
        if (session == null || !session.isConnected()) {
            throw new IllegalStateException("SSH session is not connected.");
        }
        try {
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            LoggingUtils.info("Uploading file " + localPath + " to " + remotePath);
            channel.put(localPath, remotePath);
            channel.disconnect();
        } catch (Exception e) {
            LoggingUtils.error("File upload failed: " + e.getMessage());
            throw new RuntimeException("File upload failed", e);
        }
    }

    /**
     * Disconnects the SSH session.
     */
    public void disconnect() {
        if (session != null && session.isConnected()) {
            LoggingUtils.info("Disconnecting SSH session.");
            session.disconnect();
        }
    }
}
