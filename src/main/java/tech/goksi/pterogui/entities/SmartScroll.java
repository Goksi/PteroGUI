package tech.goksi.pterogui.entities;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class SmartScroll implements AdjustmentListener {
    private int previousValue = -1;
    private int previousMaximum = -1;
    private final Viewport viewportPosition;
    private boolean adjustScrollBar = true;

    public SmartScroll(JScrollPane scrollPane, Direction direction, Viewport viewport){
        this.viewportPosition = viewport;

        JScrollBar scrollBar = direction.isHorizontal() ? scrollPane.getHorizontalScrollBar() : scrollPane.getVerticalScrollBar();
        scrollBar.addAdjustmentListener(this);

        Component view = scrollPane.getViewport().getView();

        if(view instanceof JTextComponent){
            JTextComponent textComponent = (JTextComponent) view;
            DefaultCaret caret = (DefaultCaret)textComponent.getCaret();
            caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        }
    }

    private void checkScrollBar(AdjustmentEvent e){
        JScrollBar scrollBar = (JScrollBar)e.getSource();
        BoundedRangeModel listModel = scrollBar.getModel();
        int value = listModel.getValue();
        int extent = listModel.getExtent();
        int maximum = listModel.getMaximum();
        boolean valueChanged = previousValue != value;
        boolean maximumChanged = previousMaximum != maximum;

        if (valueChanged && !maximumChanged)
        {
            if (viewportPosition.isStart())
                adjustScrollBar = value != 0;
            else
                adjustScrollBar = value + extent >= maximum;
        }
        if (adjustScrollBar && !viewportPosition.isStart())
        {
            scrollBar.removeAdjustmentListener( this );
            value = maximum - extent;
            scrollBar.setValue( value );
            scrollBar.addAdjustmentListener( this );
        }
        if (adjustScrollBar && viewportPosition.isStart())
        {
            scrollBar.removeAdjustmentListener( this );
            value = value + maximum - previousMaximum;
            scrollBar.setValue( value );
            scrollBar.addAdjustmentListener( this );
        }
        previousMaximum = maximum;
        previousValue = value;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        SwingUtilities.invokeLater(() -> checkScrollBar(e));
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL;

        public boolean isHorizontal(){
            return this == HORIZONTAL;
        }
    }

    public enum Viewport{
        START,
        END;

        public boolean isStart(){
            return this == START;
        }
    }
}
