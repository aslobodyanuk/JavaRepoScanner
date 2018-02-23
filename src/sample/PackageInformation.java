package sample;

import java.util.ArrayList;

public class PackageInformation {
    int id;
    String packageName = null;
    String version = null;
    String priority = null;
    String section = null;
    String maintainer = null;
    String installSize = null;
    String downloadSize = null;
    String depends = null;
    String breaks = null;
    String replaces = null;
    String homepage = null;
    String tag = null;
    String aptSources = null;
    String recommends = null;
    String provides = null;
    String sourceName = null;
    String preDepends = null;
    String suggests = null;
    String description = null;

    public void processFullInfo(ArrayList<String> inputList){
        boolean isDescription = false;
        String descriptionString = "";
        for (String value : inputList){
            if (value.indexOf("Description:") != -1){
                isDescription = true;
                descriptionString = getDataSubstring(value);
            }
            if (isDescription){
                if (value.equals("")){
                    setValue("Description",descriptionString);
                    isDescription = false;
                }
                descriptionString = descriptionString.concat(value);
            }else {
                setValue(getDataNameSubstring(value),getDataSubstring(value));
            }
        }
    }

    public String getDataSubstring(String input){
        int searchIndex = input.indexOf(':');
        if (searchIndex == -1){
            return input;
        }else {
            searchIndex = searchIndex + 2;
        }
        return input.substring(searchIndex, input.length());
    }

    public String getDataNameSubstring(String input){
        int searchIndex = input.indexOf(':');
        if (searchIndex == -1){
            return null;
        }else {
            return input.substring(0,searchIndex);
        }
    }

    public void setValue(String infoType, String value){
        if (infoType == null)
            return;
        switch (infoType){
            case "Package":{
                packageName = value;
                break;
            }
            case "Version":{
                version = value;
                break;
            }
            case "Priority":{
                priority = value;
                break;
            }
            case "Section":{
                section = value;
                break;
            }
            case "Maintainer":{
                maintainer = value;
                break;
            }
            case "Installed-Size":{
                installSize = value;
                break;
            }
            case "Download-Size":{
                downloadSize = value;
                break;
            }
            case "APT-Sources":{
                aptSources = value;
                break;
            }
            case "Description":{
                description = value;
                break;
            }
            case "Breaks":{
                breaks = value;
                break;
            }
            case "Depends":{
                depends = value;
                break;
            }
            case "Replaces":{
                replaces = value;
                break;
            }
            case "Homepage":{
                homepage = value;
                break;
            }
            case "Tag":{
                tag = value;
                break;
            }
            case "Recommends":{
                recommends = value;
                break;
            }
            case "Provides":{
                provides = value;
                break;
            }
            case "Source":{
                sourceName = value;
                break;
            }
            case "Pre-Depends":{
                preDepends = value;
                break;
            }
            case "Suggests":{
                suggests = value;
                break;
            }
        }
    }

    public void setValue(int dbIndex, String value){
        if (dbIndex < 1 || dbIndex > 20 || value == null)
            return;
        switch (dbIndex){
            case 1:{
                id = Integer.parseInt(value);
                break;
            }
            case 2:{
                packageName = value;
                break;
            }
            case 3:{
                version = value;
                break;
            }
            case 4:{
                priority = value;
                break;
            }
            case 5:{
                section = value;
                break;
            }
            case 6:{
                maintainer = value;
                break;
            }
            case 7:{
                installSize = value;
                break;
            }
            case 8:{
                downloadSize = value;
                break;
            }
            case 9:{
                depends = value;
                break;
            }
            case 10:{
                breaks = value;
                break;
            }
            case 11:{
                replaces = value;
                break;
            }
            case 12:{
                homepage = value;
                break;
            }
            case 13:{
                tag = value;
                break;
            }
            case 14:{
                aptSources = value;
                break;
            }
            case 15:{
                recommends = value;
                break;
            }
            case 16:{
                provides = value;
                break;
            }
            case 17:{
                sourceName = value;
                break;
            }
            case 18:{
                preDepends = value;
                break;
            }
            case 19:{
                suggests = value;
                break;
            }
            case 20:{
                description = value;
                break;
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getAptSources() {
        return aptSources;
    }

    public String getBreaks() {
        return breaks;
    }

    public String getDepends() {
        return depends;
    }

    public String getDownloadSize() {
        return downloadSize;
    }

    public String getDescription() {
        return description;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getInstallSize() {
        return installSize;
    }

    public String getMaintainer() {
        return maintainer;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPreDepends() {
        return preDepends;
    }

    public String getPriority() {
        return priority;
    }

    public String getProvides() {
        return provides;
    }

    public String getRecommends() {
        return recommends;
    }

    public String getReplaces() {
        return replaces;
    }

    public String getSection() {
        return section;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getSuggests() {
        return suggests;
    }

    public String getTag() {
        return tag;
    }

    public String getVersion() {
        return version;
    }
}
