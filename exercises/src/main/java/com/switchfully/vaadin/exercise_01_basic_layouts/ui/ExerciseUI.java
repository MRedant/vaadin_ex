package com.switchfully.vaadin.exercise_01_basic_layouts.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public class ExerciseUI extends UI {

    @Autowired
    public ExerciseUI() {
    }

    @Override
    protected void init(VaadinRequest request) {
        // TODO Exercise 1: Using VerticalLayout and HorizontalLayout, create a button layout resembling the buttons of an old school cellphone.

        // Use the Button component to create the buttons:
        VerticalLayout content = new VerticalLayout();
        setContent(content);
//
//        HorizontalLayout titleBar = new HorizontalLayout();
//        titleBar.setWidth("100%");
//        content.addComponent(titleBar);

        VerticalLayout keypad = new VerticalLayout();
        content.addComponent(keypad);

        HorizontalLayout firstLine = new HorizontalLayout();
        Button one = new Button("1");
        firstLine.addComponent(one);
        Button two = new Button("2");
        firstLine.addComponent(two);
        Button three = new Button("3");
        firstLine.addComponent(three);
        keypad.addComponent(firstLine);

        HorizontalLayout secondLine = new HorizontalLayout();
        Button four = new Button("4");
        secondLine.addComponent(four);
        Button five = new Button("5");
        secondLine.addComponent(five);
        Button six = new Button("6");
        secondLine.addComponent(six);
        keypad.addComponent(secondLine);

        HorizontalLayout thirthLine = new HorizontalLayout();
        Button seven = new Button("7");
        thirthLine.addComponent(seven);
        Button eight = new Button("8");
        thirthLine.addComponent(eight);
        Button nine = new Button("9");
        thirthLine.addComponent(nine);
        keypad.addComponent(thirthLine);

        HorizontalLayout fourthLine = new HorizontalLayout();
        Button star = new Button("*");
        fourthLine.addComponent(star);
        Button zero = new Button("0");
        fourthLine.addComponent(zero);
        Button hash = new Button("#");
        fourthLine.addComponent(hash);
        keypad.addComponent(fourthLine);





        // Don't forget to set your main layout using setContent(myLayout)

    }

}