// 
// Decompiled by Procyon v0.5.36
// 

package com.userofbricks.expandedcombat.config;

import java.util.Arrays;
import java.util.List;

public class AlignmentHelper
{
    public static final List<String> validAlignmentValues;
    
    static {
        validAlignmentValues = Arrays.asList("top_left", "top_center", "top_right", "center_left", "center", "center_right", "bottom_left", "bottom_center", "bottom_right");
    }
    
    public enum Alignment
    {
        TOP_LEFT, 
        TOP_CENTER, 
        TOP_RIGHT, 
        CENTER_LEFT, 
        CENTER, 
        CENTER_RIGHT, 
        BOTTOM_LEFT, 
        BOTTOM_CENTER, 
        BOTTOM_RIGHT;
        
        public static Alignment fromString(final String align) {
            final int idx = AlignmentHelper.validAlignmentValues.indexOf(align);
            if (idx != -1) {
                return values()[idx];
            }
            return Alignment.CENTER;
        }
    }
}
