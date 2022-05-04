package ru.msu.cmc.webapp.models;

public interface CommonEntity<ID> {
    ID getId();
    void setId(ID id);
}