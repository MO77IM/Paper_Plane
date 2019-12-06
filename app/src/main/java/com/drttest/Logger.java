import java.io.*;
import java.util.*;
import com.alibaba.fastjson.*;

public class Logger{
    static final int THRESHOLD = 10; //size of each log file
    private Logger(){
        //TODO: read log config
        this.logs = new ArrayList<String>();
    }

    public static boolean log(String str){
        //make new record with time stamp
        str = (new Date().toString()) + " :\n" + str + '\n';
        System.out.println(str);
        instance.logs.add(str);

        //if logs need to be saved
        if (instance.logs.size() >= THRESHOLD){
            //read the count of log files
            str = "";
            JSONObject config = new JSONObject();
            int n = 0;
            try{
                FileReader fr = new FileReader("log_config.xml");
                while ((n=fr.read()) != -1){
                    str += (char)n;
                }
                fr.close();
                config = JSONObject.parseObject(str);
                n = config.getInteger("size");
            }catch(IOException ioe){
                System.out.println("log_config.xml doesn't exists, trying to create..");
            }
            //modify log files' count
            config.put("size", n+1);
            try{
                FileWriter fw = new FileWriter("log_config.xml");
                fw.write(config.toJSONString());
                fw.close();
                
                //write logs into new file
                fw = new FileWriter("log" + n + ".txt");
                for (int i=0;i<THRESHOLD;++i){
                    fw.write(instance.logs.get(i) + '\n');
                }
                fw.close();    
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
        return true;
    }

    private static Logger instance = new Logger();
    private ArrayList<String> logs;
}