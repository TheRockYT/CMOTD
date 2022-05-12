package TheRockYT.CMOTD;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class CMOTDUpdater {
    private String api;
    private String version, latest = "unknown", latest_development = "unknown";
    private CheckState state = CheckState.CHECKING;
    private Plugin plugin;
    private ScheduledTask scheduledTask;
    public CMOTDUpdater(String version, String api, Plugin plugin){
        this.api = api;
        this.version = version;
        this.plugin = plugin;
    }
    public void runUpdater(){
        if(scheduledTask != null){
            scheduledTask.cancel();
        }
        scheduledTask = ProxyServer.getInstance().getScheduler().schedule(plugin, new Runnable() {
            @Override
            public void run() {
                scheduledTask = null;
                check();
                runUpdater();
            }
        }, 30, TimeUnit.MINUTES);
    }
    public void check(){
        CheckState oldState = state;
        try {
            JsonElement jsonElement = JsonParser.parseString(getURLContent(new URL("https://therockyt.github.io/CMOTD/versions.json")));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            latest = jsonObject.get("latest").getAsString();
            latest_development = jsonObject.get("latest_development").getAsString();
            if(latest.equalsIgnoreCase(version)){
                state = CheckState.LATEST;
            }else{
                if(latest_development.equalsIgnoreCase(version)){
                    state = CheckState.LATEST_DEVELOPMENT;
                }else{
                    JsonArray jsonArray = jsonObject.get("versions").getAsJsonArray();
                    for(JsonElement jsonElement1 : jsonArray){
                        JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                        if(jsonObject1.get("version").getAsString().equalsIgnoreCase(version)){
                            if(jsonObject1.get("release").getAsBoolean()){
                                state = CheckState.OUTDATED;
                            }else{
                                state = CheckState.OUTDATED_DEVELOPMENT;
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            state = CheckState.CHECK_FAILED;
            e.printStackTrace();
        }
        if(oldState != state){
            CmotdAPI.broadcast(CMOTD.replacePlaceholder(CMOTD.getConfig().get("messages.update."+CMOTD.getUpdater().getState().toString().toLowerCase())), CMOTD.getConfig().getString("permission.updates"));
        }
    }

    public String getLatest() {
        return latest;
    }

    public String getLatestDevelopment() {
        return latest_development;
    }

    public CheckState getState() {
        return state;
    }

    public void stop(){
        if(scheduledTask != null){
            scheduledTask.cancel();
            scheduledTask = null;
        }
    }
    public String getURLContent(URL url) throws Exception {
        InputStream is = url.openConnection().getInputStream();

        BufferedReader reader = new BufferedReader( new InputStreamReader( is )  );

        String lines = "";
        String line = null;
        while((line = reader.readLine()) != null)  {
            lines += line;
        }
        reader.close();
        return lines;
    }
    public enum CheckState {
        LATEST_DEVELOPMENT,
        LATEST,
        OUTDATED,
        OUTDATED_DEVELOPMENT,
        CHECKING,
        CHECK_FAILED,
    }
}
