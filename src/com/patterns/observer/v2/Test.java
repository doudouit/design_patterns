package com.patterns.observer.v2;


import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Button button = new Button();
        button.add(new MyActionListener());
        button.add(new MyActionListener2());
        button.buttonPressed();
    }

}

/**
 * 被观察者
 */
class Button {
    private List<ActionListener> actionListeners = new ArrayList<>();


    public void buttonPressed() {
        ActionEvent actionEvent = new ActionEvent(System.currentTimeMillis(), this);
        for (int i = 0; i < actionListeners.size(); i++) {
            ActionListener actionListener = actionListeners.get(i);
            actionListener.actionPreformed(actionEvent);
        }
    }

    public void add(ActionListener listener) {
        actionListeners.add(listener);
    }
}

/**
 * 观察者
 */
interface ActionListener {
    void actionPreformed(ActionEvent event);
}

class MyActionListener implements ActionListener {
    @Override
    public void actionPreformed(ActionEvent event) {
        System.out.println("button pressed!");
    }
}

class MyActionListener2 implements ActionListener {
    @Override
    public void actionPreformed(ActionEvent event) {
        System.out.println("button pressed2!");
    }
}

/**
 * 事件
 */
class ActionEvent {
    long when;
    Object source;

    public ActionEvent(long when, Object source) {
        this.when = when;
        this.source = source;
    }

    public long getWhen() {
        return when;
    }

    public void setWhen(long when) {
        this.when = when;
    }

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }
}
