/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.burploaderkeygen.Keygen
 *  com.h3110w0r1d.json.JSONObject
 *  com.h3110w0r1d.json.JSONParse
 */
package com.h3110w0r1d.burploaderkeygen;

import com.h3110w0r1d.burploaderkeygen.Keygen;
import com.h3110w0r1d.json.JSONObject;
import com.h3110w0r1d.json.JSONParse;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentListener;

public class KeygenForm {
    private static final String Version = "v1.17";
    private static JFrame frame;
    private static JButton btn_run;
    private static JTextField text_cmd;
    private static JTextField text_license_text;
    private static JTextArea text_license;
    private static JTextArea request;
    private static JTextArea response;
    private static JLabel label0_1;
    private static JPanel panel1;
    private static JPanel panel2;
    private static JPanel panel3;
    private static JCheckBox check_autorun;
    private static JCheckBox check_ignore;
    private static JCheckBox check_early;
    private static String LatestVersion;
    private static final String DownloadURL = "https://portswigger-cdn.net/burp/releases/download?product=pro&type=Jar&version=";
    private static final String LoaderPath;
    private static String VersionData;
    private static final String LoaderDir;
    private static final String ConfigFileName;
    private static String[] cmd;
    private static String cmd_str;

    public static BufferedReader execCommand(String[] command) {
        try {
            Process proc = new ProcessBuilder(command).start();
            return new BufferedReader(new InputStreamReader(proc.getErrorStream()));
        }
        catch (Exception e) {
            return null;
        }
    }

    private static int getJavaVersion(String path) {
        String line;
        String[] command = new String[]{path, "-version"};
        BufferedReader buf = KeygenForm.execCommand(command);
        if (buf == null) {
            return 0;
        }
        do {
            try {
                line = buf.readLine();
            }
            catch (IOException e) {
                return 0;
            }
            if (line != null) continue;
            System.out.println("Warning: cannot get Java version of '" + path + "'!");
            return 0;
        } while (!line.contains("version"));
        String[] version = line.split("\"")[1].split("[.\\-]");
        if (!"1".equals(version[0])) return Integer.parseInt(version[0]);
        return Integer.parseInt(version[1]);
    }

    private static String getBurpPath() {
        String newest_file = "burpsuite_jar_not_found";
        try {
            long newest_time = 0L;
            File f = new File(LoaderPath);
            String current_dir = f.isDirectory() ? f.getPath() : f.getParentFile().toString();
            DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(current_dir, new String[0]), "burpsuite_*.jar");
            for (Path path : dirStream) {
                if (Files.isDirectory(path, new LinkOption[0]) || newest_time >= path.toFile().lastModified()) continue;
                newest_time = path.toFile().lastModified();
                newest_file = path.toString();
            }
            dirStream.close();
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return newest_file;
    }

    private static boolean IsWindows() {
        return System.getProperty("os.name").toLowerCase().startsWith("win");
    }

    private static String[] GetCMD() {
        String JAVA_PATH = KeygenForm.getJavaPath();
        int JAVA_VERSION = KeygenForm.getJavaVersion(JAVA_PATH);
        String BURP_PATH = KeygenForm.getBurpPath();
        ArrayList<String> cmd = new ArrayList<String>();
        cmd.add(JAVA_PATH);
        if (JAVA_VERSION == 0) {
            return new String[]{"Cannot find java! Please put jdk in the same path with keygen."};
        }
        if (JAVA_VERSION == 16) {
            cmd.add("--illegal-access=permit");
        }
        if (JAVA_VERSION >= 17) {
            cmd.add("--add-opens=java.desktop/javax.swing=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/java.lang=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/jdk.internal.org.objectweb.asm.tree=ALL-UNNAMED");
            cmd.add("--add-opens=java.base/jdk.internal.org.objectweb.asm.Opcodes=ALL-UNNAMED");
        }
        if (JAVA_VERSION <= 8) return new String[]{"Not support Java 8, please use old version! https://github.com/h3110w0r1d-y/BurpLoaderKeygen/releases/tag/1.7"};
        cmd.add("-javaagent:" + LoaderPath);
        cmd.add("-noverify");
        cmd.add("-jar");
        cmd.add(BURP_PATH);
        return cmd.toArray(new String[0]);
    }

    private static String GetCMDStr(String[] cmd) {
        StringBuilder cmd_str = new StringBuilder();
        String[] stringArray = cmd;
        int n = stringArray.length;
        int n2 = 0;
        while (n2 < n) {
            String x = stringArray[n2];
            cmd_str.append("\"").append(x.replace("\"", "\\\"")).append("\" ");
            ++n2;
        }
        return cmd_str.toString();
    }

    private static boolean verifyFile(File javafile) {
        if (!javafile.exists()) return false;
        if (javafile.isDirectory()) {
            return false;
        }
        if (!javafile.canExecute()) {
            System.out.println("Warning: '" + javafile.getPath() + "' can not execute!");
            return false;
        }
        System.out.println("\u001b[32mSuccess\u001b[0m: '" + javafile.getPath() + "' can execute!");
        return true;
    }

    private static String verifyPath(String path) {
        File javafile = new File(path);
        if (KeygenForm.verifyFile(javafile)) {
            return javafile.getPath();
        }
        javafile = new File(path + ".exe");
        if (!KeygenForm.verifyFile(javafile)) return null;
        return javafile.getPath();
    }

    private static String getJavaPath() {
        String[] paths = new String[]{LoaderDir + File.separator + "bin", LoaderDir + File.separator + "jre" + File.separator + "bin", LoaderDir + File.separator + "jdk" + File.separator + "bin", System.getProperty("java.home") + File.separator + "bin"};
        String java_path = null;
        String[] stringArray = paths;
        int n = stringArray.length;
        int n2 = 0;
        while (n2 < n) {
            String path_str = stringArray[n2];
            java_path = KeygenForm.verifyPath(path_str + File.separator + "java");
            if (java_path != null) {
                return java_path;
            }
            try {
                DirectoryStream<Path> dirStream = Files.newDirectoryStream(Paths.get(path_str, new String[0]), "java[0-9]{1,2}");
                for (Path path : dirStream) {
                    if (Files.isDirectory(path, new LinkOption[0]) || !Files.isExecutable(path)) continue;
                    return path.toString();
                }
                dirStream = Files.newDirectoryStream(Paths.get(path_str, new String[0]), "java[0-9]{1,2}\\.exe");
                for (Path path : dirStream) {
                    if (Files.isDirectory(path, new LinkOption[0]) || !Files.isExecutable(path)) continue;
                    return path.toString();
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
            ++n2;
        }
        return java_path;
    }

    private static String readProperty(String key) {
        Properties properties = new Properties();
        File file = new File(ConfigFileName);
        try {
            file.createNewFile();
        }
        catch (Exception ignored) {
            return "0";
        }
        try {
            InputStream inStream = Files.newInputStream(file.toPath(), new OpenOption[0]);
            properties.load(inStream);
        }
        catch (IOException e) {
            return "0";
        }
        return properties.getProperty(key);
    }

    private static void setProperty(String key, String value) {
        Properties properties = new Properties();
        try {
            InputStream inStream = Files.newInputStream(Paths.get(ConfigFileName, new String[0]), new OpenOption[0]);
            properties.load(inStream);
            properties.setProperty(key, value);
            FileOutputStream out = new FileOutputStream(ConfigFileName, false);
            properties.store(out, "");
            out.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public static String GetHTTPBody(String url) {
        try {
            String temp;
            URL realUrl = new URL(url);
            HttpsURLConnection https = (HttpsURLConnection)realUrl.openConnection();
            https.connect();
            if (https.getResponseCode() != 200) return "";
            InputStream is = https.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder sbf = new StringBuilder();
            while ((temp = br.readLine()) != null) {
                sbf.append(temp);
                sbf.append("\r\n");
            }
            return sbf.toString();
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    private static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[]{new /* Unavailable Anonymous Inner Class!! */};
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String GetLatestVersion() {
        try {
            JSONObject[] Results;
            JSONObject data;
            if (VersionData == null) {
                VersionData = KeygenForm.GetHTTPBody("https://portswigger.net/burp/releases/data?pageSize=5");
            }
            if ((data = JSONParse.Parse((String)VersionData)) == null) {
                return "";
            }
            JSONObject[] jSONObjectArray = Results = data.get("ResultSet").getList("Results");
            int n = jSONObjectArray.length;
            int n2 = 0;
            while (n2 < n) {
                JSONObject Result = jSONObjectArray[n2];
                boolean isProfessional = false;
                boolean isEarlyAdopter = false;
                for (JSONObject category : Result.getList("categories")) {
                    if (!"Professional".equals(category.String())) continue;
                    isProfessional = true;
                }
                if (isProfessional) {
                    if ("Early Adopter".equals(Result.getList("releaseChannels")[0].String())) {
                        isEarlyAdopter = true;
                    }
                    if (!isEarlyAdopter) return Result.getString("version");
                    if ("1".equals(KeygenForm.readProperty("early"))) {
                        return Result.getString("version");
                    }
                }
                ++n2;
            }
            return "";
        }
        catch (Exception exception) {
            // empty catch block
        }
        return "";
    }

    private static void CheckNewVersion() {
        label0_1.setText("Checking the latest version of BurpSuite...");
        label0_1.setForeground(Color.BLACK);
        LatestVersion = KeygenForm.GetLatestVersion();
        if (LatestVersion.equals("")) {
            label0_1.setText("Failed to check the latest version of BurpSuite");
        } else if (!cmd_str.contains(LatestVersion + ".jar")) {
            label0_1.setText("Latest version: " + LatestVersion + ". Click to download.");
            label0_1.setForeground(Color.BLUE);
        } else {
            label0_1.setText("Your BurpSuite is already the latest version(" + LatestVersion + ")");
            label0_1.setForeground(new Color(0, 100, 0));
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            KeygenForm.trustAllHosts();
        }
        catch (Exception exception) {
            // empty catch block
        }
        String LicenseName = "h3110w0r1d";
        if (KeygenForm.readProperty("auto_run") == null) {
            KeygenForm.setProperty("auto_run", "0");
        }
        if (KeygenForm.readProperty("ignore") == null) {
            KeygenForm.setProperty("ignore", "0");
        }
        if (KeygenForm.readProperty("early") == null) {
            KeygenForm.setProperty("early", "0");
        }
        block17: for (int i = 0; i < args.length; ++i) {
            switch (args[i]) {
                case "-a": 
                case "-auto": {
                    if (i + 1 < args.length) {
                        if (args[i + 1].equals("0")) {
                            KeygenForm.setProperty("auto_run", "0");
                            continue block17;
                        }
                        KeygenForm.setProperty("auto_run", "1");
                        continue block17;
                    }
                    KeygenForm.setProperty("auto_run", "1");
                    continue block17;
                }
                case "-i": 
                case "-ignore": {
                    if (i + 1 < args.length) {
                        if (args[i + 1].equals("0")) {
                            KeygenForm.setProperty("ignore", "0");
                            continue block17;
                        }
                        KeygenForm.setProperty("ignore", "1");
                        continue block17;
                    }
                    KeygenForm.setProperty("ignore", "1");
                    continue block17;
                }
                case "-n": 
                case "-name": {
                    if (i + 1 >= args.length) continue block17;
                    LicenseName = args[i + 1];
                    continue block17;
                }
            }
        }
        if (cmd_str.endsWith(".jar\" ") && KeygenForm.readProperty("auto_run").equals("1")) {
            try {
                new ProcessBuilder(cmd).start();
                if (KeygenForm.readProperty("ignore").equals("1") || cmd_str.contains(KeygenForm.GetLatestVersion() + ".jar")) {
                    System.exit(0);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        frame = new JFrame("Burp Suite Pro Loader & Keygen v1.17 - By h3110w0r1d");
        btn_run = new JButton("Run");
        label0_1 = new JLabel("Checking the latest version of BurpSuite...");
        JLabel label1 = new JLabel("Loader Command:", 4);
        JLabel label2 = new JLabel("License Text:", 4);
        text_cmd = new JTextField(cmd_str);
        text_license_text = new JTextField("licensed to " + LicenseName);
        text_license = new JTextArea(Keygen.generateLicense((String)text_license_text.getText()));
        request = new JTextArea();
        response = new JTextArea();
        check_autorun = new JCheckBox("Auto Run");
        check_ignore = new JCheckBox("Ignore Update");
        check_early = new JCheckBox("Early Adopter");
        check_autorun.setBounds(150, 25, 110, 20);
        check_autorun.setSelected(KeygenForm.readProperty("auto_run").equals("1"));
        check_autorun.addChangeListener(changeEvent -> {
            if (check_autorun.isSelected()) {
                KeygenForm.setProperty("auto_run", "1");
            } else {
                KeygenForm.setProperty("auto_run", "0");
            }
        });
        check_ignore.setBounds(260, 25, 150, 20);
        check_ignore.setSelected(KeygenForm.readProperty("ignore").equals("1"));
        check_ignore.addChangeListener(changeEvent -> {
            if (check_ignore.isSelected()) {
                KeygenForm.setProperty("ignore", "1");
            } else {
                KeygenForm.setProperty("ignore", "0");
            }
        });
        check_early.setBounds(410, 25, 150, 20);
        check_early.setSelected(KeygenForm.readProperty("early").equals("1"));
        check_early.addChangeListener(changeEvent -> {
            if (check_early.isSelected()) {
                KeygenForm.setProperty("early", "1");
            } else {
                KeygenForm.setProperty("early", "0");
            }
            KeygenForm.CheckNewVersion();
        });
        label0_1.setLocation(150, 5);
        label1.setBounds(5, 50, 140, 22);
        text_cmd.setLocation(150, 50);
        btn_run.setSize(60, 22);
        label2.setBounds(5, 77, 140, 22);
        text_license_text.setLocation(150, 77);
        panel1.setBorder(BorderFactory.createTitledBorder("License"));
        panel2.setBorder(BorderFactory.createTitledBorder("Activation Request"));
        panel3.setBorder(BorderFactory.createTitledBorder("Activation Response"));
        text_license.setLocation(10, 15);
        request.setLocation(10, 15);
        response.setLocation(10, 15);
        panel1.setLayout(null);
        panel2.setLayout(null);
        panel3.setLayout(null);
        frame.setLayout(null);
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(3);
        frame.setBackground(Color.LIGHT_GRAY);
        frame.addComponentListener((ComponentListener)new /* Unavailable Anonymous Inner Class!! */);
        btn_run.addMouseListener((MouseListener)new /* Unavailable Anonymous Inner Class!! */);
        text_license.setLineWrap(true);
        text_license.setEditable(false);
        text_license_text.setHorizontalAlignment(0);
        text_license.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        text_license_text.getDocument().addDocumentListener((DocumentListener)new /* Unavailable Anonymous Inner Class!! */);
        request.setLineWrap(true);
        request.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        request.getDocument().addDocumentListener((DocumentListener)new /* Unavailable Anonymous Inner Class!! */);
        response.setLineWrap(true);
        response.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        frame.add(check_autorun);
        frame.add(check_ignore);
        frame.add(check_early);
        frame.add(btn_run);
        frame.add(label0_1);
        frame.add(label1);
        frame.add(label2);
        frame.add(panel1);
        frame.add(panel2);
        frame.add(panel3);
        frame.add(text_cmd);
        frame.add(text_license_text);
        panel1.add(text_license);
        panel2.add(request);
        panel3.add(response);
        if (text_cmd.getText().contains("burpsuite_jar_not_found.jar")) {
            btn_run.setEnabled(false);
            check_autorun.setSelected(false);
            check_autorun.setEnabled(false);
        }
        frame.setVisible(true);
        btn_run.setFocusable(false);
        LatestVersion = KeygenForm.GetLatestVersion();
        if (LatestVersion.equals("")) {
            label0_1.setText("Failed to check the latest version of BurpSuite");
        } else if (!cmd_str.contains(LatestVersion + ".jar")) {
            label0_1.setText("Latest version: " + LatestVersion + ". Click to download.");
            label0_1.setForeground(Color.BLUE);
            label0_1.setCursor(Cursor.getPredefinedCursor(12));
            label0_1.addMouseListener((MouseListener)new /* Unavailable Anonymous Inner Class!! */);
        } else {
            label0_1.setText("Your BurpSuite is already the latest version(" + LatestVersion + ")");
            label0_1.setForeground(new Color(0, 100, 0));
        }
    }

    static /* synthetic */ JFrame access$000() {
        return frame;
    }

    static /* synthetic */ JTextField access$100() {
        return text_cmd;
    }

    static /* synthetic */ JButton access$200() {
        return btn_run;
    }

    static /* synthetic */ JTextField access$300() {
        return text_license_text;
    }

    static /* synthetic */ JLabel access$400() {
        return label0_1;
    }

    static /* synthetic */ JTextArea access$500() {
        return text_license;
    }

    static /* synthetic */ JTextArea access$600() {
        return request;
    }

    static /* synthetic */ JTextArea access$700() {
        return response;
    }

    static /* synthetic */ JPanel access$800() {
        return panel1;
    }

    static /* synthetic */ JPanel access$900() {
        return panel2;
    }

    static /* synthetic */ JPanel access$1000() {
        return panel3;
    }

    static /* synthetic */ String[] access$1100() {
        return KeygenForm.GetCMD();
    }

    static /* synthetic */ String access$1200() {
        return LatestVersion;
    }

    static {
        try {
            LoaderPath = KeygenForm.IsWindows() ? KeygenForm.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().substring(1) : KeygenForm.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        LoaderDir = new File(LoaderPath).getParent();
        ConfigFileName = LoaderDir + File.separator + ".config.ini";
        cmd = KeygenForm.GetCMD();
        cmd_str = KeygenForm.GetCMDStr(cmd);
    }
}
