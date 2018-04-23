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
import java.util.List;
import java.util.stream.Collectors;

public class EditAccomodationForm extends FormLayout {


    private Accomodation accomodation;

    public EditAccomodationForm(CityService cityService, AccomodationService accomodationService) {

        List<String> cityList = cityService.getCities().stream()
                .map(City::getName)
                .collect(Collectors.toList());

        this.accomodation = Accomodation.AccomodationBuilder.accomodation().build();

        final BeanFieldGroup<Accomodation> binder = new BeanFieldGroup<Accomodation>(Accomodation.class);
        binder.setItemDataSource(accomodation);

        TextField name = new TextField("Name");
        name.setNullRepresentation("");

        Component nameComponent = binder.buildAndBind("Name", "name");

        this.addComponent(nameComponent);

        BeanItemContainer<City> cityContainer = new BeanItemContainer<>(City.class, cityService.getCities());
        ComboBox cityField = new ComboBox("City", cityContainer);
        cityField.setItemCaptionPropertyId("name");
        cityField.setNullSelectionAllowed(false);
        binder.bind(cityField, "city");
        this.addComponent(cityField);

        this.addComponent(binder.buildAndBind("Number of rooms", "numberOfRooms"));

        BeanItemContainer<StarRating> ratingContainer = new BeanItemContainer<>(StarRating.class);
        ratingContainer.addAll(EnumSet.allOf(StarRating.class));
        ComboBox ratingField = new ComboBox("Rating", ratingContainer);
        ratingField.setNullSelectionAllowed(false);
        binder.bind(ratingField,"starRating");
        this.addComponent(ratingField);

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
        this.addComponent(buttons);
    }

}
