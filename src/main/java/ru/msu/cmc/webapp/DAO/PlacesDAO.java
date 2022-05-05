package ru.msu.cmc.webapp.DAO;

import ru.msu.cmc.webapp.models.Places;

import java.util.List;

public interface PlacesDAO extends CommonDAO<Places, Long> {
    List<Places> getAllPlacesByPersonId(Long personId);
}