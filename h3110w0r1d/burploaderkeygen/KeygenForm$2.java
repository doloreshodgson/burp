/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.burploaderkeygen.KeygenForm
 */
package com.h3110w0r1d.burploaderkeygen;

import com.h3110w0r1d.burploaderkeygen.KeygenForm;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/*
 * Exception performing whole class analysis ignored.
 */
static final class KeygenForm.2
extends ComponentAdapter {
    KeygenForm.2() {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int H = KeygenForm.access$000().getHeight() - 150;
        int W = KeygenForm.access$000().getWidth();
        KeygenForm.access$100().setSize(W - 235, 22);
        KeygenForm.access$200().setLocation(W - 80, 50);
        KeygenForm.access$300().setSize(W - 170, 22);
        KeygenForm.access$400().setSize(W - 170, 20);
        KeygenForm.access$500().setSize((W - 15) / 2 - 25, H / 2 - 25);
        KeygenForm.access$600().setSize((W - 15) / 2 - 25, H / 2 - 25);
        KeygenForm.access$700().setSize(W - 43, H / 2 - 25);
        KeygenForm.access$800().setBounds(5, 104, (W - 15) / 2 - 5, H / 2);
        KeygenForm.access$900().setBounds(3 + (W - 15) / 2, 104, (W - 15) / 2 - 5, H / 2);
        KeygenForm.access$1000().setBounds(5, 109 + H / 2, W - 23, H / 2);
    }
}
