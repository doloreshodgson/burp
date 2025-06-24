/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.json.JSONObject
 *  com.h3110w0r1d.json.JSONType
 *  com.h3110w0r1d.json.ParseResult
 */
package com.h3110w0r1d.json;

import com.h3110w0r1d.json.JSONObject;
import com.h3110w0r1d.json.JSONType;
import com.h3110w0r1d.json.ParseResult;
import java.util.HashMap;

public class JSONParse {
    private static int spaceLen(String json) {
        return json.length() - json.trim().length();
    }

    private static ParseResult ParseDict(String json) {
        JSONObject obj = new JSONObject(JSONType.Dict, new HashMap());
        int len = 1;
        len += JSONParse.spaceLen(json.substring(len));
        while (true) {
            if (json.charAt(len) == '}') {
                ++len;
                len += JSONParse.spaceLen(json.substring(len));
                break;
            }
            ParseResult key = JSONParse.ParseString(json.substring(len));
            len += key.len;
            if (json.charAt(len += JSONParse.spaceLen(json.substring(len))) != ':') {
                throw new RuntimeException("Invalid JSON");
            }
            ParseResult value = JSONParse.parse(json.substring(++len));
            len += value.len;
            len += JSONParse.spaceLen(json.substring(len));
            obj.set(key.obj.string, value.obj);
            if (json.charAt(len) == '}') {
                ++len;
                len += JSONParse.spaceLen(json.substring(len));
                break;
            }
            if (json.charAt(len) != ',') throw new RuntimeException("Invalid JSON");
            ++len;
            len += JSONParse.spaceLen(json.substring(len));
        }
        return new ParseResult(len, obj);
    }

    private static ParseResult ParseString(String json) {
        int len = 1;
        StringBuilder sb = new StringBuilder();
        while (true) {
            block14: {
                block13: {
                    if (json.charAt(len) == '\"') {
                        return new ParseResult(++len, new JSONObject(JSONType.String, (Object)sb.toString()));
                    }
                    if (json.charAt(len) != '\\') break block13;
                    switch (json.charAt(++len)) {
                        case '\"': {
                            sb.append('\"');
                            break block14;
                        }
                        case '\\': {
                            sb.append('\\');
                            break block14;
                        }
                        case '/': {
                            sb.append('/');
                            break block14;
                        }
                        case 'b': {
                            sb.append('\b');
                            break block14;
                        }
                        case 'f': {
                            sb.append('\f');
                            break block14;
                        }
                        case 'n': {
                            sb.append('\n');
                            break block14;
                        }
                        case 'r': {
                            sb.append('\r');
                            break block14;
                        }
                        case 't': {
                            sb.append('\t');
                            break block14;
                        }
                        case 'u': {
                            sb.append((char)Integer.parseInt(json.substring(len + 1, len + 5), 16));
                            len += 4;
                            break block14;
                        }
                        default: {
                            throw new RuntimeException("Invalid JSON");
                        }
                    }
                }
                sb.append(json.charAt(len));
            }
            ++len;
        }
    }

    private static ParseResult ParseList(String json) {
        JSONObject[] list = new JSONObject[]{};
        int len = 1;
        while (true) {
            if (json.charAt(len) == ']') {
                ++len;
                len += JSONParse.spaceLen(json.substring(len));
                break;
            }
            ParseResult value = JSONParse.parse(json.substring(len));
            len += value.len;
            len += JSONParse.spaceLen(json.substring(len));
            JSONObject[] newList = new JSONObject[list.length + 1];
            System.arraycopy(list, 0, newList, 0, list.length);
            newList[list.length] = value.obj;
            list = newList;
            if (json.charAt(len) == ']') {
                ++len;
                len += JSONParse.spaceLen(json.substring(len));
                break;
            }
            if (json.charAt(len) != ',') throw new RuntimeException("Invalid JSON");
            ++len;
            len += JSONParse.spaceLen(json.substring(len));
        }
        return new ParseResult(len, new JSONObject(JSONType.List, (Object)list));
    }

    private static ParseResult ParseNumber(String json) {
        int len = 0;
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (json.charAt(len) == '-' || json.charAt(len) == '+' || json.charAt(len) == 'e' || json.charAt(len) == 'E' || json.charAt(len) == '.') {
                sb.append(json.charAt(len));
            } else {
                if ('0' > json.charAt(len)) return new ParseResult(len, new JSONObject(JSONType.Number, (Object)sb.toString()));
                if (json.charAt(len) > '9') return new ParseResult(len, new JSONObject(JSONType.Number, (Object)sb.toString()));
                sb.append(json.charAt(len));
            }
            ++len;
        }
    }

    private static ParseResult ParseNull(String json) {
        return new ParseResult(4, new JSONObject(JSONType.Null, null));
    }

    private static ParseResult ParseBoolean(String json) {
        return new ParseResult(json.startsWith("true") ? 4 : 5, new JSONObject(JSONType.Boolean, (Object)json.startsWith("true")));
    }

    private static ParseResult parse(String json) {
        ParseResult result;
        int slen = JSONParse.spaceLen(json);
        if ((json = json.substring(slen)).length() == 0) {
            return null;
        }
        if (json.startsWith("{")) {
            result = JSONParse.ParseDict(json);
        } else if (json.startsWith("[")) {
            result = JSONParse.ParseList(json);
        } else if (json.startsWith("\"")) {
            result = JSONParse.ParseString(json);
        } else if ('0' <= json.charAt(0) && json.charAt(0) <= '9') {
            result = JSONParse.ParseNumber(json);
        } else if (json.startsWith("null")) {
            result = JSONParse.ParseNull(json);
        } else {
            if (!json.startsWith("true")) {
                if (!json.startsWith("false")) throw new RuntimeException("Invalid JSON");
            }
            result = JSONParse.ParseBoolean(json);
        }
        result.AddLen(slen + JSONParse.spaceLen(json.substring(result.len)));
        return result;
    }

    public static JSONObject Parse(String json) {
        if ((json = json.trim().replace("\n", "").replace("\r", "")).length() == 0) {
            return null;
        }
        ParseResult result = JSONParse.parse(json);
        if (result.len == json.length()) return result.obj;
        throw new RuntimeException("Invalid JSON");
    }
}
