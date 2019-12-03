//powered by SCUDRT
package com.example.tr.playplane;
import java.util.*;

/**
 * Usage:
 * String message = "{'key1': 'value1', 'key2': {'key3': 'value2'}}";
 * SimpleJSON json = new SimpleJSON(message);
 * json.put("key4", "value4");
 * json.getString("key1");
 * json.getJSONObject("key2");
 */
public class SimpleJSON{
    SimpleJSON(String _data){
        this._init();
        this.load(_data);
    }
    SimpleJSON(){
        this._init();
    }
    void _init(){
        this.isPureString = false;
        this.data = "";
        this.keys = new ArrayList<String>();
        this.values = new ArrayList<String>();
        this.pairs = new HashMap<String, SimpleJSON>();
    }

    /** PUBLIC */
    //String means it is not a JSON
    public boolean isPureString(){
        return this.isPureString;
    }

    public void load(String _data){
        // gc
        if (this.keys == null || this.keys.size() != 0){
            this.keys = new ArrayList<String>();
            this.values = new ArrayList<String>();
        }

        this.data = _data;
        char now;
        String str = "";
        int n = _data.length();

        for (int i=0;i<n;++i){
            now = _data.charAt(i);
            if (this.isNumber(now) || this.isLetter(now)){ //legal word
                str = str + now;
            }else if (this.isBlank(now)){ //skip
                ;
            }else if (now == ':'){ // key word end
                this.keys.add(str);
                str = "";
            }else if (now == ','){ // value word end
                this.values.add(str);
                str = "";
            }else if (now == '{'){ // begin, recursion is not supported
                ;
            }else if (now == '}'){ // end
                this.values.add(str);
                break;
            }else{ //illegal word
                ;
            }
        }
    }

    /**
     * Testing version.
     * Trying to support recursion and blank chars.
     * @param _data
     */
    public void _load(String _data){
        if (this.pairs.size() > 0){ //gc
            this.pairs = new HashMap<String, SimpleJSON>();
        }
        this.data = _data;

        //temporary values
        char now;
        String key = "", value = "";
        int n = _data.length();
        int level = 0;
        boolean isReadingKey = false;
        boolean isReadingWord = false;

        //eliminate spaces first
        for (int i=0;i<n;++i){
            now = _data.charAt(i);
            ; //how to put string " 1jh':'hjk " into JSON ?
        }

        //check if the string is a JSON string
        if (_data.charAt(0) != '{' || _data.charAt(n-1) != '}'){
            this.isPureString = true;
            return;
        }else{
            this.isPureString = false;
        }

        //process
        for (int i=0;i<n;++i){
            now = _data.charAt(i);
            if (now == '\''){
                if (isReadingWord == false){
                    isReadingWord = true;
                }
            }else if (now == ','){
                ;
            }else if (now == '{'){
                ++level;
                if (level > 1){
                    ;
                }
            }else if (now == '}'){
                --level;
                if (level == 1){
                    ;
                }else if (level == 0){
                    this.pairs.put(key, new SimpleJSON(value));
                    break;
                }
            }else if (now == ':'){
                ;
            }else{ //other char

            }
        }
    }

    public String getString(String _key){
        //return string value of _key
        SimpleJSON temp = this.getJSONObject(_key);
        if (temp != null){
            return temp.toString();
        }
        return null;
    }

    public SimpleJSON getJSONObject(String _key){
        //return SimpleJSON value of _key
        return this.pairs.get(_key);
    }

    //add a new pair to the JSON
    public void put(String _key, SimpleJSON _value){
        this.pairs.put(_key, _value);
    }

    //will be abandoned
    public String getValue(String _key){
        int n = this.keys.size();
        for (int i=0;i<n;++i){
            if (this.keys.get(i).equals(_key)){
                return this.values.get(i);
            }
        }
        return "";
    }

    public String toString(){
        return this.data;
    }

    /** PRIVATE */
    private boolean isBlank(char now){
        return (
                now == ' ' ||
                        now == '\t' ||
                        now == '\n' ||
                        now == '\r'
        );
    }
    private boolean isNumber(char x){
        return x >= '0' && x <= '9';
    }
    private boolean isLetter(char x){
        //x = (((int)x)|32);
        x = Character.toUpperCase(x);
        return x >= 'a' && x <= 'z';
    }
    private boolean isPureString;
    private String data;
    private Map<String, SimpleJSON> pairs;
    private ArrayList<String> keys;
    private ArrayList<String> values;
}
