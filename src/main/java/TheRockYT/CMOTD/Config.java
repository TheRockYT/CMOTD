package TheRockYT.CMOTD;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
    Configuration config;
    private File file;
    public Config(File file){
        this.file = file;
    }
    public void load(){
        try {
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void save(){
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void set(String key, Object value){
        config.set(key, value);
    }
    public void add(String key, Object value){
        if(!contains(key)){

            config.set(key, value);
        }
    }
    public boolean contains(String key){
        return config.contains(key);
    }
    public Object get(String key){
        return config.get(key);
    }
    public String getString(String key){
        if(contains(key)){
            return config.getString(key);
        }
        return null;
    }
    public List<String> getStringList(String key){
        if(contains(key)){
            return config.getStringList(key);
        }
        return null;
    }
    public Integer getInt(String key){
        if(contains(key)){
            return config.getInt(key);
        }
        return null;
    }
    public Boolean getBool(String key){
        if(contains(key)){
            return config.getBoolean(key);
        }
        return null;
    }
    public File getFile() {
        return file;
    }
}
