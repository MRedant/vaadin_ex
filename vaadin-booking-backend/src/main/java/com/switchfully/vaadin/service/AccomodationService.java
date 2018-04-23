package com.switchfully.vaadin.service;

import com.switchfully.vaadin.domain.Accomodation;
import com.switchfully.vaadin.domain.AccomodationId;
import com.switchfully.vaadin.domain.Booking;
import com.switchfully.vaadin.domain.StarRating;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static com.switchfully.vaadin.domain.Accomodation.AccomodationBuilder.accomodation;
import static com.switchfully.vaadin.domain.Accomodation.AccomodationBuilder.cloneAccomodation;
import static java.lang.String.format;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class AccomodationService {

    private List<Accomodation> accomodations = new ArrayList<>();

    public AccomodationService() {
        initData();
    }

    public List<Accomodation> getAccomodations() {
        return accomodations.stream()
                .sorted((acc1, acc2) -> acc1.getName().compareToIgnoreCase(acc2.getName()))
                .collect(toList());
    }

    public Accomodation getAccomodation(AccomodationId id) {
        return accomodations.stream()
                .filter(accomodation -> accomodation.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        format("Accomodation with id %s not found.", id.toString()))
                );
    }

    public void delete(AccomodationId id) {
        this.accomodations = this.accomodations.stream()
                .filter(accomodation -> !accomodation.getId().equals(id))
                .collect(toList());
    }

    public void save(AccomodationId accomodationId, Booking booking) {
        getAccomodation(accomodationId).addBooking(booking);
    }

    public boolean save(Accomodation accomodation) {
        boolean saved = false;
        if (!accomodation.isPersisted()) {
            this.accomodations.add(
                    cloneAccomodation(accomodation)
                    .withId()
                    .build());
            saved = true;
        } else {
            Accomodation accomodationToUpdate = getAccomodation(accomodation.getId());

            accomodationToUpdate.setName(accomodation.getName());
            accomodationToUpdate.setCity(accomodation.getCity());
            accomodationToUpdate.setNumberOfRooms(accomodation.getNumberOfRooms());
            accomodationToUpdate.setStarRating(accomodation.getStarRating());
        }
        return saved;
    }

    private void initData() {
        // Amsterdam
        accomodations.add(accomodation()
                .withId()
                .withName("Hotel Tulip")
                .withCity(CityService.AMSTERDAM)
                .withStarRating(StarRating.THREE_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Onder dat brugje")
                .withCity(CityService.AMSTERDAM)
                .withStarRating(StarRating.NO_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Hotel Estherea")
                .withCity(CityService.AMSTERDAM)
                .withStarRating(StarRating.FIVE_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Breitner House")
                .withCity(CityService.AMSTERDAM)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());

        // Berlin
        accomodations.add(accomodation()
                .withId()
                .withName("Hotel Otto")
                .withCity(CityService.BERLIN)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Unter der Brücke")
                .withCity(CityService.BERLIN)
                .withStarRating(StarRating.NO_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Hotel Edelweiss")
                .withCity(CityService.BERLIN)
                .withStarRating(StarRating.TWO_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());

        // Brussels
        accomodations.add(accomodation()
                .withId()
                .withName("Hotel Amigo")
                .withCity(CityService.BRUSSELS)
                .withStarRating(StarRating.FIVE_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("B&B Onder de Brug")
                .withCity(CityService.BRUSSELS)
                .withStarRating(StarRating.NO_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Regency Hotel")
                .withCity(CityService.BRUSSELS)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());

        // London
        accomodations.add(accomodation()
                .withId()
                .withName("The Royal Horseguards")
                .withCity(CityService.LONDON)
                .withStarRating(StarRating.FIVE_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Hilton London Westminster")
                .withCity(CityService.LONDON)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Under The Bridge")
                .withCity(CityService.LONDON)
                .withStarRating(StarRating.NO_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Shangri-La Hotel, At The Shard")
                .withCity(CityService.LONDON)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());

        // New-York
        accomodations.add(accomodation()
                .withId()
                .withName("Hotel Trump")
                .withCity(CityService.NEW_YORK)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Chelsea International Hostel")
                .withCity(CityService.NEW_YORK)
                .withStarRating(StarRating.ONE_STAR)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("The Plaza")
                .withCity(CityService.NEW_YORK)
                .withStarRating(StarRating.FIVE_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());

        // Paris
        accomodations.add(accomodation()
                .withId()
                .withName("Hilton Paris Centre")
                .withCity(CityService.PARIS)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Hyatt Paris Nord")
                .withCity(CityService.PARIS)
                .withStarRating(StarRating.FOUR_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Novotel Paris Centre")
                .withCity(CityService.PARIS)
                .withStarRating(StarRating.THREE_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
        accomodations.add(accomodation()
                .withId()
                .withName("Sous le Pont")
                .withCity(CityService.PARIS)
                .withStarRating(StarRating.NO_STARS)
                .withNumberOfRooms(getRandomNumberInRange(10, 500))
                .build());
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public List<Accomodation> findAccomodations(String searchText) {
        return accomodations.stream()
                .filter(accomodation -> accomodation.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(toList());
    }
}