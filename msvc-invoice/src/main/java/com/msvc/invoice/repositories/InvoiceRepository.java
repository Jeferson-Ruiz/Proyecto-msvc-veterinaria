package com.msvc.invoice.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.msvc.invoice.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

    @Query("SELECT i FROM Invoice i WHERE invoiceCode =:invoiceCode")
    Optional<Invoice> findByCode(@Param("invoiceCode")String invoiceCode);

    @Query("SELECT i FROM Invoice i WHERE customerDocument =:customerDocument")
    List<Invoice> findByCustomerDocument(@Param("customerDocument") String customerDocument);

    @Query("SELECT i FROM Invoice i WHERE consultationCode =:consultationCode")
    Optional<Invoice> findByConsultationCode(@Param("consultationCode") String consultationCode);

    //Metodos de busqueda por fecha
    @Query("SELECT i FROM Invoice i WHERE i.date =:date")
    List<Invoice> findByDate(@Param("date") LocalDate date);
    
    @Query("SELECT i FROM Invoice i WHERE i.date > :date")
    List<Invoice> findByDateGreater(@Param("date") LocalDate date);

    @Query("SELECT i FROM Invoice i WHERE i.date < :date")
    List<Invoice> findByDateLess(@Param("date") LocalDate date);

    Long countBydate(LocalDate date);

    boolean existsByinvoiceCode(String invoiceCode);

}   
