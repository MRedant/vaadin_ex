package com.switchfully.vaadin.exercise_02_grids.ui;

import com.switchfully.vaadin.domain.Accomodation;
import com.switchfully.vaadin.service.AccomodationService;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringUI
public class ExerciseUI extends UI {

    private AccomodationService accomodationService;

    @Autowired
    public ExerciseUI(AccomodationService accomodationService) {
        this.accomodationService = accomodationService;
    }

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout mainLayout = new VerticalLayout();


        // TODO Exercise 2: Show the list of accomodations from accomodationService.getAccomodations() in a Grid.

        // Use BeanItemContainer as the ContainerDataSource for the Grid.

        // Try to only show the following properties of an accomodation:
        // - Name
        // - Star Rating
        // - City Name

        Grid grid = new Grid();

        BeanItemContainer<Accomodation> itemContainer = new BeanItemContainer<>(Accomodation.class, accomodationService.getAccomodations());
        grid.setContainerDataSource(itemContainer);

        itemContainer.addNestedContainerProperty("city.name");
        grid.setColumns("name", "starRating", "city.name");
        grid.getColumn("city.name").setHeaderCaption("City");

        mainLayout.addComponent(grid);

        mainLayout.setMargin(true);
        setContent(mainLayout);


    }

}