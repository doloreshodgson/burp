/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.json.JSONObject$1
 *  com.h3110w0r1d.json.JSONType
 */
package com.h3110w0r1d.json;

import com.h3110w0r1d.json.JSONObject;
import com.h3110w0r1d.json.JSONType;
import java.util.HashMap;

public class JSONObject {
    private JSONType type;
    private JSONObject[] list;
    public String string;
    private String number;
    private Boolean bool;
    private HashMap<String, JSONObject> dict;

    public JSONObject(JSONType type, Object value) {
        this.type = type;
        switch (1.$SwitchMap$com$h3110w0r1d$json$JSONType[type.ordinal()]) {
            case 1: {
                this.list = (JSONObject[])value;
                break;
            }
            case 2: {
                this.dict = (HashMap)value;
                break;
            }
            case 3: {
                this.string = (String)value;
                break;
            }
            case 4: {
                this.number = (String)value;
                break;
            }
            case 5: {
                this.bool = (Boolean)value;
                break;
            }
            case 6: {
                break;
            }
        }
    }

    public void set(String key, JSONObject value) {
        if (this.type != JSONType.Dict) {
            throw new RuntimeException("Not a dict");
        }
        this.dict.put(key, value);
    }

    public JSONObject get(String key) {
        if (this.type == JSONType.Dict) return this.dict.get(key);
        throw new RuntimeException("Not a dict");
    }

    public String getString(String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get((Object)key).type != JSONType.String) throw new RuntimeException("Not a string");
        return this.dict.get((Object)key).string;
    }

    public String getNumber(String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get((Object)key).type != JSONType.Number) throw new RuntimeException("Not a number");
        return this.dict.get((Object)key).number;
    }

    public Boolean getBoolean(String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get((Object)key).type != JSONType.Boolean) throw new RuntimeException("Not a boolean");
        return this.dict.get((Object)key).bool;
    }

    public JSONObject[] getList(String key) {
        if (this.dict.get(key) == null) {
            throw new RuntimeException("Unknown key");
        }
        if (this.dict.get((Object)key).type != JSONType.List) throw new RuntimeException("Not a list");
        return this.dict.get((Object)key).list;
    }

    public Boolean Boolean() {
        if (this.type != JSONType.Boolean) throw new RuntimeException("Not a boolean");
        return this.bool;
    }

    public String Number() {
        if (this.type != JSONType.Number) throw new RuntimeException("Not a number");
        return this.number;
    }

    public String String() {
        if (this.type != JSONType.String) throw new RuntimeException("Not a string");
        return this.string;
    }
}
