package com.binnavigator.be.Bin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BinRepository extends JpaRepository<Bin, Long> {
    @Modifying
    @Query("UPDATE Bin b SET b.information = :newInformation WHERE b.id = :binId")
    void updateInformation(@Param("binId") Long binId, @Param("newInformation") String newInformation);
}
