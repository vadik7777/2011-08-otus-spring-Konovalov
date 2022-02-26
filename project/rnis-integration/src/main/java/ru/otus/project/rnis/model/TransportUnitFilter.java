package ru.otus.project.rnis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransportUnitFilter {
    private Long transportTypeId;
    private Long municipalityId;
    private Instant informationDateFrom;
}