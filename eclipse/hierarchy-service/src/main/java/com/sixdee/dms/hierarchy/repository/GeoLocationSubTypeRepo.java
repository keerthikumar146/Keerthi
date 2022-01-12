package com.sixdee.dms.hierarchy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sixdee.dms.hierarchy.domain.GeoLocationSubType;

/**
 * @author balu.s
 *
 */

@Repository
public interface GeoLocationSubTypeRepo extends JpaRepository<GeoLocationSubType, Integer> {

}
