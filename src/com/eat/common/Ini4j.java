package com.eat.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ini4j class
 *
 * @author sylar
 * @verion 0.0.1
 * @date   2017-12-07
 */
public class Ini4j {

    private final int SECTION_TYPE  = 0;    //section line
    private final int KEYVALUE_TYPE = 1;    //key value line
    private final int COMMENT_TYPE  = 2;    //comment line

    class BaseLine {
        protected String line;
        protected int type;

        BaseLine(int type){
            this.type = type;
        }
    }

    class Section extends BaseLine {

        Section(){
            super(SECTION_TYPE);
        }
        protected String name;
        protected String comment;
        protected List<BaseLine> content = new ArrayList<BaseLine>();

    }

    class KeyVal extends BaseLine {
        KeyVal(){
            super(KEYVALUE_TYPE);
        }
        protected String key;
        protected String value;
        protected String comment;
    }

    class Comment extends BaseLine {
        Comment(String line){
            super(COMMENT_TYPE);
            this.comment = line;
        }
        protected String comment;
    }

	private String fileName;
    private List<BaseLine> content = new ArrayList<BaseLine>();
    private boolean needSave = false;

    /**
     *  constructed function
     *
     */
	public Ini4j(){
	}

    /**
     * open ini file content
     *
     * @param fileName
     * @return
     */
	public boolean open(String fileName){
		File file = new File(fileName);
		if( !file.exists() ){
			return false;
		}
		
		this.fileName = fileName;
		this.needSave = false;
		this.content.clear();

		BufferedReader reader = null;
        try {  
            reader = new BufferedReader(new FileReader(file));  

            String line = null;
            Section section = null;
            while ( ( line = reader.readLine() ) != null ) {
                BaseLine base = this.parse(line);
                if( base.type == SECTION_TYPE ){
                    content.add(base);
                    section = (Section)base;
                }else {
                    if (util.notNull(section)) {
                        section.content.add(base);
                    } else {
                        content.add(base);
                    }
                }
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();
            return false;
        } finally {  
            util.close(reader);  
        }  
        
		return true;
	}

    /**
     * read string from [section, key]
     *
     * @param section
     * @param key
     * @param value
     * @return String
     */
    public String readString(String section, String key, String value){
        if( util.isNull(section) || util.isNull(key) ){
            return value;
        }

        for( BaseLine base : content ){
            if( base.type != SECTION_TYPE ){
                continue;
            }
            Section sec = (Section)base;
            if( !sec.name.equals(section) ){
                continue;
            }
            for (BaseLine sub : sec.content ) {
                if( sub.type != KEYVALUE_TYPE){
                    continue;
                }
                KeyVal kv = (KeyVal)sub;
                if( kv.key.equals(key) ){
                    return kv.value;
                }
            }
        }

        return value;
    }

    /**
     * read int from [section, key]
     *
     * @param section
     * @param key
     * @param value
     * @return
     */
    public int readInt(String section, String key, int value){
        String ret = readString(section, key, "");
        if(util.isNull(ret)) {
            return value;
        }
        return Integer.valueOf(ret);
    }

    /**
     * read value of [section, key]
     *
     * @param section
     * @param key
     * @return String
     */
    public String readString(String section, String key){
        return readString(section, key, "");
    }

    /**
     * save if u has modify ini
     *
     * @param fileName new file name
     * @return true/false
     */
	public boolean save(String fileName){
	    if( !needSave ){
	        return true;
        }

        File file = new File(fileName);
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            fw = new FileWriter(file);
            writer = new BufferedWriter(fw);
            for( BaseLine base : this.content ){
                writer.write(base.line);
                writer.newLine();
                if( base.type == SECTION_TYPE ){
                    Section sec = (Section)base;
                      for(BaseLine kv : sec.content ){
                        writer.write(kv.line);
                        writer.newLine();
                    }
                }
            }
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }finally{
            util.close(writer);
            util.close(fw);
        }
        return true;
    }

    /**
     * override current ini file
     *
     * @return true/false
     */
    public boolean save(){
	    return save(this.fileName);
    }

    /**
     * write a string of [section, key, value]
     *
     * @param section
     * @param key
     * @param value
     * @return
     */
	public boolean writeString(String section, String key, String value ){
        if( util.isNull(section) || util.isNull(key) ){
            return false;
        }

        for( BaseLine base : content ){
            if( base.type != SECTION_TYPE ){
                continue;
            }
            Section sec = (Section)base;
            if( !sec.name.equals(section) ){
                continue;
            }

            for (BaseLine sub : sec.content ) {
                if( sub.type != KEYVALUE_TYPE){
                    continue;
                }
                KeyVal kv = (KeyVal)sub;
                if( kv.key.equals(key) ){
                    kv.value = value;
                    this.needSave = true;
                    return true;
                }
            }

            //add new key-value
            KeyVal kv = new KeyVal();
            kv.key = key;
            kv.value = value;
            kv.line = key + " = " + value;
            sec.content.add(kv);
            this.needSave = true;
            return true;
        }

        //add new section
        Section sec = new Section();
        sec.name = section;
        sec.line = "[" + section + "]";

        KeyVal kv = new KeyVal();
        kv.key = key;
        kv.value = value;
        kv.line = key + " = " + value;
        sec.content.add(kv);
        content.add(sec);
        this.needSave = true;
        return true;
    }

    /**
     * write a int of [section, key, value]
     *
     * @param section
     * @param key
     * @param value
     * @return
     */
    public boolean writeInt(String section, String key, int value ){
        return writeString(section, key, String.valueOf(value) );
    }

    /**
     * remove key
     *
     * @param section
     * @param key
     * @return true or false
     */
    public boolean remove(String section, String key){
        if( util.isNull(section) || util.isNull(key) ){
            return false;
        }

        for( BaseLine base : content ){
            if( base.type != SECTION_TYPE ){
                continue;
            }
            Section sec = (Section)base;
            if( !sec.name.equals(section) ){
                continue;
            }

            for (BaseLine sub : sec.content ) {
                if( sub.type != KEYVALUE_TYPE){
                    continue;
                }
                KeyVal kv = (KeyVal)sub;
                if( !kv.key.equals(key) ){
                    continue;
                }

                //find key
                sec.content.remove(kv);
                return true;
            }

            //no key
            return false;
        }

        //no section
        return false;
    }

    /**
     * remove section
     *
     * @param section
     * @return
     */
    public boolean remove(String section){
        if( util.isNull(section) ){
            return false;
        }

        for( BaseLine base : content ){
            if( base.type != SECTION_TYPE ){
                continue;
            }
            Section sec = (Section)base;
            if( !sec.name.equals(section) ){
                continue;
            }

            content.remove(base);
            return true;
        }

        //no section
        return false;
    }

    /**
     * parse line
     *
     * @param line
     * @return BaseLine(Section or KeyVal or Comment)
     */
	private BaseLine parse(String line){
        //find comment
        if( util.isNull(line) || line.startsWith("#") ){
            Comment base = new Comment(line);
            base.line = line;
            return base;
        }

	    //find section
	    int pos = line.indexOf('[');
	    if( pos != - 1) {
            int secEndPos = line.indexOf(']', pos);
            if (secEndPos == -1) {
                Comment base = new Comment(line);
                base.line = line;
                return base;
            }

            Section section = new Section();
            section.line = line;
            section.name = line.substring(pos+1, secEndPos - pos).trim();
            return section;
        }

        pos = line.indexOf('=');
	    if( pos == -1){
            Comment base = new Comment(line);
            base.line = line;
            return base;
        }

        KeyVal kv = new KeyVal();
	    kv.line = line;
	    kv.key  = line.substring(0, pos).trim();
	    kv.value = line.substring(pos+1).trim();

	    pos = kv.value.indexOf('#');
	    if( pos != -1 ){
	        kv.comment = kv.value.substring(pos);
	        kv.value   = kv.value.substring(0, pos).trim();
        }
	    return kv;
    }

    /**
     * test
     *
     * @param args
     */
	public static void main(String[] args) {
        Ini4j ini = new Ini4j();
        ini.open("config.ini");
        System.out.println(ini.readString("log", "level"));
        System.out.println(ini.readString("system", "port"));
        ini.writeString("log", "maxlines", "65535");
        ini.writeString("http", "ip", "192.168.1.2");
        System.out.println(ini.readString("log", "maxlines"));
        System.out.println(ini.readString("http", "ip"));
        ini.remove("http");
        ini.save("config-new.ini");
	}
}
