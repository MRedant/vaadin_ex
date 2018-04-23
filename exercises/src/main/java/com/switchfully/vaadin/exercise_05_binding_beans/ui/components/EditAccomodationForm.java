package com.switchfully.vaadin.exercise_05_binding_beans.ui.components;

import com.switchfully.vaadin.domain.Accomodation;
import com.switchfully.vaadin.domain.City;
import com.switchfully.vaadin.domain.StarRating;
import com.switchfully.vaadin.service.AccomodationService;
import com.switchfully.vaadin.service.CityService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.util.EnumSet;

import static com.switchfully.vaadin.domain.Accomodation.AccomodationBuilder.cloneAccomodation;

public class EditAccomodationForm extends FormLayout {

    private final BeanFieldGroup<Accomodation> binder = new BeanFieldGroup<Accomodation>(Accomodation.class);

    private final AccomodationAdmin admin;
    private AccomodationService accomodationService;
    private final CityService cityService;
    private Accomodation accomodation = Accomodation.AccomodationBuilder.accomodation().build();

    private final BeanItemContainer<String> nameContainer;
    private TextField name;
    private BeanItemContainer<City> cityContainer;
    private ComboBox cityField;
    private BeanItemContainer<StarRating> ratingContainer;
    private ComboBox ratingField;
    private Button deleteButton = new Button("Delete");
    private Button saveButton = new Button("Save");
    private Button cancelButton = new Button("Cancel");
    private HorizontalLayout buttons;

    public EditAccomodationForm(AccomodationAdmin accomodationAdmin, CityService cityService, AccomodationService accomodationService) {

        this.admin = accomodationAdmin;
        this.accomodationService = accomodationService;
        this.cityService = cityService;

        binder.setItemDataSource(accomodation);

        nameContainer = new BeanItemContainer<>(String.class);
        name = new TextField("Name");
        name.setWidth("100%");
        name.setNullRepresentation("");
        binder.bind(name, "name");


        cityContainer = new BeanItemContainer<>(City.class, cityService.getCities());
        cityField = new ComboBox("City", cityContainer);
        cityField.setItemCaptionPropertyId("name");
        cityField.setNullSelectionAllowed(false);
        binder.bind(cityField, "city");

        ratingContainer = new BeanItemContainer<>(StarRating.class);
        ratingContainer.addAll(EnumSet.allOf(StarRating.class));
        ratingField = new ComboBox("Rating", ratingContainer);
        ratingField.setNullSelectionAllowed(false);
        binder.bind(ratingField, "starRating");

        saveButton.addClickListener(clickEvent -> save());

        deleteButton.addClickListener(clickEvent -> delete());
        deleteButton.setDescription("Based on the 'Name' of the accommodation");

        cancelButton.addClickListener(clickEvent -> cancel());

        buttons = new HorizontalLayout(saveButton, deleteButton, cancelButton);
        buttons.setSpacing(true);

        this.addComponent(name);
        this.addComponent(cityField);
        this.addComponent(binder.buildAndBind("Number of rooms", "numberOfRooms"));
        this.addComponent(ratingField);
        this.addComponent(buttons);

    }

    public void setAccomodation(Accomodation accomodation2) {
        this.accomodation = cloneAccomodation(accomodation2).build();
        binder.setItemDataSource(accomodation);
    }

    private void cancel() {
        setVisible(false);
    }

    private void save() {
        try {
            binder.commit();
        } catch (FieldGroup.CommitException e) {
            e.printStackTrace();
        }
        boolean saved = accomodationService.save(accomodation);

        if (saved) {
            Notification.show("Accommodation Saved", Notification.Type.HUMANIZED_MESSAGE);
        }
        admin.updateList();
        setVisible(false);
    }

    private void delete() {
        accomodationService.delete(accomodation.getId());
        admin.updateList();
        setVisible(false);
    }

}
