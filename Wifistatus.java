import java.io.*;
//import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.net.*;
public class Wifistatus
{
    public static void main(String[] args) throws Exception
    {
        while(true)
        {
            
//            boolean status = checkWifi();
  //          logTofile(status, timestamp());
checkWifi();
               try{
                   Thread.sleep(2000);
               } catch(InterruptedException e) {
                     Thread.currentThread().interrupt();
               }
        }
    }

    public String timestamp()
    {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        return timestamp;
    }

    public void logTofile(String status, String timestamp)
    {
        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wifilog.csv", true)));
//            FileWriter out = new FileWriter("wifilog.csv", true);
            out.println(timestamp + "," + status);
            out.close();
        } catch(Exception e){e.getMessage();}
    }

    public static String checkWifi()
    {
        String ip = "not declared";
        try{
            System.out.println(NetworkInterface.getNetworkInterfaces());
//InetAddress ip = InetAddress.getHostAddress();
        } catch (Exception e) {
             e.getMessage();
          }
        System.out.println(ip);
        return ip;
    }
}
