package com.meli.urlshortener.service.repository;

import com.meli.urlshortener.dto.ResponseEventDTO;
import com.meli.urlshortener.entity.EventRestModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<EventRestModel, Long> {

    @Query("SELECT new com.meli.urlshortener.dto.ResponseEventDTO(e.method, e.statusCode, COUNT(e.statusCode)) FROM EventRestModel e " +
            "WHERE e.date BETWEEN :dateFrom AND :dateTo AND e.method LIKE 'handle%' OR e.method LIKE 'deleteUrl' " +
            "GROUP BY e.method, e.statusCode")
    List<ResponseEventDTO> findEventRestModelsBetweenDates(
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime localDateTime
    );
}
