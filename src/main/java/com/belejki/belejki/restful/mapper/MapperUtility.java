package com.belejki.belejki.restful.mapper;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtility {

    // Generic method to map a List<T> to List<D>
    public static <T, D> List<D> entitiesMapper(Collection<T> entities, Function<T, D> mapper) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
