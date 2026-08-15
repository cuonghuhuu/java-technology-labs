package vn.edu.eaut.lab5.util;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class PhoneDocumentFilter extends DocumentFilter {
    @Override public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String old = fb.getDocument().getText(0, fb.getDocument().getLength());
        String next = old.substring(0, offset) + (text == null ? "" : text) + old.substring(offset + length);
        if (next.matches("\\d{0,10}")) super.replace(fb, offset, length, text, attrs);
    }
    @Override public void insertString(FilterBypass fb, int offset, String text, AttributeSet attrs) throws BadLocationException {
        replace(fb, offset, 0, text, attrs);
    }
}
