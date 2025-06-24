/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.burploaderkeygen.KeygenForm
 */
package com.h3110w0r1d.burploaderkeygen;

import com.h3110w0r1d.burploaderkeygen.KeygenForm;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

/*
 * Exception performing whole class analysis ignored.
 */
static final class KeygenForm.6
extends MouseAdapter {
    KeygenForm.6() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        try {
            Desktop.getDesktop().browse(new URI("https://portswigger-cdn.net/burp/releases/download?product=pro&type=Jar&version=" + KeygenForm.access$1200()));
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
