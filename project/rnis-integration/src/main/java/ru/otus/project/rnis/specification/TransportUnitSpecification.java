package ru.otus.project.rnis.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.otus.project.rnis.entity.Municipality;
import ru.otus.project.rnis.entity.TransportType;
import ru.otus.project.rnis.entity.TransportUnit;
import ru.otus.project.rnis.model.TransportUnitFilter;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class TransportUnitSpecification {

    private TransportUnitSpecification() {
    }

    public static Specification<TransportUnit> transportUnitFilterBy(TransportUnitFilter transportUnitFilter) {

        Specification<TransportUnit> spec = (r, q, cb) -> null;

        if (Objects.nonNull(transportUnitFilter.getTransportTypeId())) {
            spec = Optional.ofNullable(spec.and(withTransportTypeIs(transportUnitFilter.getTransportTypeId())))
                           .orElse(spec);
        }

        if (Objects.nonNull(transportUnitFilter.getMunicipalityId())) {
            spec = Optional.ofNullable(spec.and(withMunicipalityIs(transportUnitFilter.getMunicipalityId())))
                           .orElse(spec);
        }

        if (Objects.nonNull(transportUnitFilter.getInformationDateFrom())) {
            spec = Optional.ofNullable(spec.and(greaterInformationDateFrom(transportUnitFilter.getInformationDateFrom())))
                           .orElse(spec);
        }

        return spec;
    }

    private static Specification<TransportUnit> withTransportTypeIs(Long transportTypeId) {
        return (r, q, cb) -> cb.equal(r.get(TransportUnit.Fields.transportType).get(TransportType.Fields.id),
                                      transportTypeId);
    }

    private static Specification<TransportUnit> withMunicipalityIs(Long municipalityId) {
        return (r, q, cb) -> cb.equal(r.get(TransportUnit.Fields.municipality).get(Municipality.Fields.id),
                                      municipalityId);
    }

    private static Specification<TransportUnit> greaterInformationDateFrom(Instant informationDateFrom) {
        return (r, q, cb) -> cb.greaterThanOrEqualTo(r.get(TransportUnit.Fields.informationDate), informationDateFrom);
    }

}