/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.json.JSONObject
 */
package com.h3110w0r1d.json;

import com.h3110w0r1d.json.JSONObject;

public class ParseResult {
    public int len;
    public JSONObject obj;

    public ParseResult(int len, JSONObject obj) {
        this.len = len;
        this.obj = obj;
    }

    public void AddLen(int len) {
        this.len += len;
    }
}
