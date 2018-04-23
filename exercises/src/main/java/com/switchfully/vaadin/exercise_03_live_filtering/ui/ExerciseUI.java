package com.switchfully.vaadin.exercise_03_live_filtering.ui;

import com.switchfully.vaadin.domain.Accomodation;
import com.switchfully.vaadin.service.AccomodationService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class ExerciseUI extends UI {

    private Grid grid = new Grid();
    TextField filterField = new TextField();
    Button deleteButton = new Button(FontAwesome.TIMES);

    private AccomodationService accomodationService;

    @Autowired
    public ExerciseUI(AccomodationService accomodationService) {
        this.accomodationService = accomodationService;
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout(new HorizontalLayout(filterField, deleteButton), grid);

        BeanItemContainer<Accomodation> container =
                new BeanItemContainer<>(Accomodation.class, accomodationService.getAccomodations());

        container.addNestedContainerProperty("city.name");

        grid.setColumns("name", "starRating", "city.name");

        grid.getColumn("city.name").setHeaderCaption("City");

        grid.setContainerDataSource(container);

        // TODO Exercise 3: Add a filter TextField to the top of the grid to filter the list of accomodations by name.
        filterField.setInputPrompt("Filter by name...");
        filterField.addTextChangeListener(e -> {
            if (!e.getText().isEmpty())
                container.addContainerFilter(
                        new SimpleStringFilter("name", e.getText(), true, false));
        });


        // TODO Exercise 3: Add a button next to the filter TextField to clear the filter.

        deleteButton.setDescription("Clear the current filter");
        deleteButton.addClickListener(clickEvent -> container.removeAllContainerFilters());
        deleteButton.addClickListener(clickEvent -> filterField.clear());

        mainLayout.setMargin(true);
        setContent(mainLayout);

    }
}