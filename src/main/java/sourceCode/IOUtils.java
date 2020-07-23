package sourceCode;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class IOUtils {
    public IOUtils() {
    }

    public static boolean deleteRecursive(File var0) throws FileNotFoundException {
        boolean var1 = true;
        if (!var0.exists()) {
            return true;
        } else {
            if (var0.isDirectory()) {
                File[] var2 = var0.listFiles();
                if (var2 != null) {
                    File[] var3 = var2;
                    int var4 = var2.length;

                    for(int var5 = 0; var5 < var4; ++var5) {
                        File var6 = var3[var5];
                        var1 = var1 && deleteRecursive(var6);
                    }
                }
            }

            return var1 && var0.delete();
        }
    }

    public static void copyFromURL(URL var0, File var1) throws IOException {
        if (var0 == null) {
            throw new IOException("Missing input resource!");
        } else {
            if (var1.exists()) {
                var1.delete();
            }

            InputStream var2 = var0.openStream();
            FileOutputStream var3 = new FileOutputStream(var1);
            byte[] var4 = new byte[1024];

            int var5;
            while((var5 = var2.read(var4)) != -1) {
                var3.write(var4, 0, var5);
            }

            var3.close();
            var2.close();
            var1.setReadOnly();
            var1.setReadable(true, false);
        }
    }

    public static void copyFile(File var0, File var1) throws IOException {
        var1.getParentFile().mkdirs();
        var1.delete();
        var1.createNewFile();
        FileChannel var2 = null;
        FileChannel var3 = null;
        var2 = (new FileInputStream(var0)).getChannel();
        var3 = (new FileOutputStream(var1)).getChannel();
        if (var3 != null && var2 != null) {
            var3.transferFrom(var2, 0L, var2.size());
        }

        if (var2 != null) {
            var2.close();
        }

        if (var3 != null) {
            var3.close();
        }

        if (var0.canExecute()) {
            var1.setExecutable(true, false);
        }

        if (!var0.canWrite()) {
            var1.setReadOnly();
        }

        var1.setReadable(true, false);
    }

    public static long getFolderSize(File var0) {
        long var1 = 0L;
        File[] var3 = var0.listFiles();
        if (var3 != null) {
            File[] var4 = var3;
            int var5 = var3.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                File var7 = var4[var6];
                if (var7.isDirectory()) {
                    var1 += getFolderSize(var7);
                } else {
                    var1 += var7.length();
                }
            }
        }

        return var1;
    }

    public static boolean isNotSymbolicLink(File var0) {
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            return true;
        } else {
            try {
                if (var0 == null || var0.getParent() == null) {
                    return false;
                }

                File var1 = new File(var0.getParentFile().getCanonicalFile(), var0.getName());
                if (var1.getCanonicalFile().equals(var1.getAbsoluteFile())) {
                    return true;
                }
            } catch (IOException var2) {
            }

            return false;
        }
    }

    public static byte[] readFully(File var0) throws IOException {
        FileInputStream var1 = new FileInputStream(var0);
        ByteArrayOutputStream var2 = new ByteArrayOutputStream();
        byte[] var3 = new byte[1024];
        boolean var4 = false;

        int var5;
        while((var5 = var1.read(var3)) != -1) {
            var2.write(var3, 0, var5);
        }

        var2.close();
        return var2.toByteArray();
    }
}
