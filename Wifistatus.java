import java.io.*;
//import java.sql.Timestamp;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;
public class Wifistatus
{
    public static void main(String[] args) throws Exception
    {
        while(true)
        {

            boolean status = checkWifi();
            logTofile(status, timestamp());
            System.out.println(status + timestamp());
               try{
                   Thread.sleep(60000);
               } catch(InterruptedException e) {
                     Thread.currentThread().interrupt();
               }
        }
    }

    public static String timestamp()
    {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return timestamp;
    }

    public static void logTofile(boolean status, String timestamp)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wifilog.csv", true)));
//            FileWriter out = new FileWriter("wifilog.csv", true);
            out.println(timestamp + "," + status);
            out.close();
        } catch(Exception e){e.getMessage();}
    }

    public static boolean checkWifi() {
        String response = "";
        ProcessBuilder pb = new ProcessBuilder("/sbin/iwconfig", "wlan0");
// | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1}'");
//        ProcessBuilder pb = new ProcessBuilder("/sbin/ifconfig eth0 | grep 'inet addr:' | cut -d: -f2 | awk '{ print $1}'");
        pb.redirectErrorStream(true);
        try {
            Process shell = pb.start();
            // To capture output from the shell
            InputStream shellIn = shell.getInputStream();
            // Wait for the shell to finish and get the return code
            int shellExitStatus = shell.waitFor();
            response = convertStreamToStr(shellIn);
            shellIn.close();
        }

        catch (IOException e) {
            System.out.println("Error occured while executing Linux command. Error Description: "
            + e.getMessage());
        }

        catch (InterruptedException e) {
            System.out.println("Error occured while executing Linux command. Error Description: "
            + e.getMessage());
        }
        if(response.length() > 300){
            return true;
        }
            else
        {
            return false;
        }
    }

/*
* To convert the InputStream to String we use the Reader.read(char[]
* buffer) method. We iterate until the Reader return -1 which means
* there's no more data to read. We use the StringWriter class to
* produce the string.
*/

    public static String convertStreamToStr(InputStream is) throws IOException {
       if (is != null) {
           Writer writer = new StringWriter();
           char[] buffer = new char[1024];
           try {
               Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
               int n;
               while ((n = reader.read(buffer)) != -1) {
                   writer.write(buffer, 0, n);
               }
           }
           finally {
               is.close();
           }
           return writer.toString();
       }
       else {
           return "";
       }
    }
}
