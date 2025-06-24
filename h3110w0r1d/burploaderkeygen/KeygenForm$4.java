/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.h3110w0r1d.burploaderkeygen.Keygen
 *  com.h3110w0r1d.burploaderkeygen.KeygenForm
 */
package com.h3110w0r1d.burploaderkeygen;

import com.h3110w0r1d.burploaderkeygen.Keygen;
import com.h3110w0r1d.burploaderkeygen.KeygenForm;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/*
 * Exception performing whole class analysis ignored.
 */
static final class KeygenForm.4
implements DocumentListener {
    KeygenForm.4() {
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        KeygenForm.access$500().setText(Keygen.generateLicense((String)KeygenForm.access$300().getText()));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        KeygenForm.access$500().setText(Keygen.generateLicense((String)KeygenForm.access$300().getText()));
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        KeygenForm.access$500().setText(Keygen.generateLicense((String)KeygenForm.access$300().getText()));
    }
}
