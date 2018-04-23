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

public class EditAccomodationForm extends FormLayout {

    private final AccomodationAdmin admin;
    private AccomodationService accomodationService;
    private final CityService cityService;
    private Accomodation accomodation;

    public EditAccomodationForm(AccomodationAdmin accomodationAdmin, CityService cityService, AccomodationService accomodationService) {

        this.admin = accomodationAdmin;
        this.accomodationService = accomodationService;
        this.cityService = cityService;

        final BeanFieldGroup<Accomodation> binder = new BeanFieldGroup<Accomodation>(Accomodation.class);
        binder.setItemDataSource(accomodation);

        TextField name = new TextField("Name");
        name.setNullRepresentation("");
        Component nameComponent = binder.buildAndBind("Name", "name");

        BeanItemContainer<City> cityContainer = new BeanItemContainer<>(City.class, cityService.getCities());
        ComboBox cityField = new ComboBox("City", cityContainer);
        cityField.setItemCaptionPropertyId("name");
        cityField.setNullSelectionAllowed(false);
        binder.bind(cityField, "city");

        BeanItemContainer<StarRating> ratingContainer = new BeanItemContainer<>(StarRating.class);
        ComboBox ratingField = new ComboBox("Rating", ratingContainer);
        ratingContainer.addAll(EnumSet.allOf(StarRating.class));
        ratingField.setNullSelectionAllowed(false);
        binder.bind(ratingField,"starRating");

        Button saveButton = new Button("Save");
        saveButton.addClickListener(clickEvent ->
        {
            try {
                binder.commit();
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }
            boolean saved = accomodationService.save(accomodation);

            if (saved) {
                Notification.show("Accomodation Saved", Notification.Type.HUMANIZED_MESSAGE);
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setDescription("Based on the 'Name' of the accommodation");
        deleteButton.addClickListener(clickEvent -> accomodationService.delete(
                accomodationService.findAccomodations(nameComponent.getCaption()).stream()
                        .findFirst()
                        .orElse(null)
                        .getId()));

        Button cancelButton = new Button("Cancel");
//        cancelButton.addClickListener(this.setVisible(false));

        HorizontalLayout buttons = new HorizontalLayout(saveButton, deleteButton, cancelButton);
        buttons.setSpacing(true);

        this.addComponent(nameComponent);
        this.addComponent(cityField);
        this.addComponent(binder.buildAndBind("Number of rooms", "numberOfRooms"));
        this.addComponent(ratingField);
        this.addComponent(buttons);
    }


}
