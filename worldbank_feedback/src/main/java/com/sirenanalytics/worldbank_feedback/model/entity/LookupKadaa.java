package com.sirenanalytics.worldbank_feedback.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "kadaa")
public class LookupKadaa extends BaseLookup
{
    @Column(name = "mouhafaza_key", nullable = false, length = 10)
    private String mouhafazaKey;

    public String getMouhafazaKey()
    {
        return mouhafazaKey;
    }

    public void setMouhafazaKey(String mouhafazaKey)
    {
        this.mouhafazaKey = mouhafazaKey;
    }
}
