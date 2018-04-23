package com.switchfully.vaadin.exercise_04_field_binding_simple.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import static com.vaadin.ui.AbstractTextField.TextChangeEventMode.EAGER;
import static com.vaadin.ui.AbstractTextField.TextChangeEventMode.LAZY;

@SpringUI
@Theme("valo")
public class ExerciseUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        Label name = new Label("Name");

        // TODO Exercise 4: Create a TextField and a Label, both bound to the same 'name' Property.
        Property myProperty = new ObjectProperty<String>("");
        TextField textField = new TextField(myProperty);
        textField.setInputPrompt("test text");
        //        setBuffered otherwise the field automatically gets commited by performing any action on the page, now it's only on commit it changes
        textField.setBuffered(true);
        textField.addShortcutListener(new ShortcutListener("returnPressed", ShortcutAction.KeyCode.ENTER, null) {
            @Override
            public void handleAction(Object o, Object o1) {

            }
        });

        // TODO Exercise 4: Add a button to commit the field.
        Label label = new Label(myProperty);

        // TODO Exercise 4: Clicking the button should update the Label with the value in the TextField.
        Button updateButton = new Button("Update");
        updateButton.setDescription("Update Label");
        updateButton.addClickListener(clickEvent -> textField.commit());

        // TODO Exercise 4 (Extra): Add a checkbox to hide the button and make the TextField auto-commit.
        CheckBox checkbox = new CheckBox("Auto commit?");
        checkbox.addValueChangeListener(focusEvent -> {
            updateButton.setVisible(!updateButton.isVisible());

                });
        VerticalLayout mainLayout = new VerticalLayout(name, textField, label ,checkbox, updateButton);
        mainLayout.setMargin(true);
        setContent(mainLayout);
    }

}