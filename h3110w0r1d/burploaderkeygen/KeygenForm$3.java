/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.burploaderkeygen.KeygenForm
 */
package com.h3110w0r1d.burploaderkeygen;

import com.h3110w0r1d.burploaderkeygen.KeygenForm;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/*
 * Exception performing whole class analysis ignored.
 */
static final class KeygenForm.3
extends MouseAdapter {
    KeygenForm.3() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        try {
            Runtime.getRuntime().exec(KeygenForm.access$1100());
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
