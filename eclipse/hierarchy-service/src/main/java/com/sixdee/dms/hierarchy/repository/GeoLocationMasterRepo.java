package com.sixdee.dms.hierarchy.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.sixdee.dms.hierarchy.domain.GeoLocationMaster;

/**
 * @author balu.s
 *
 */

@Repository
public interface GeoLocationMasterRepo extends JpaRepository<GeoLocationMaster, Long>,
		PagingAndSortingRepository<GeoLocationMaster, Long>, JpaSpecificationExecutor<GeoLocationMaster> {

	Optional<GeoLocationMaster> findByLocationNameAndParentLocationId(String locName, Long parentLocId);

	@Transactional
	@Modifying
	@Query("update GeoLocationMaster set hasChild = true where locationId = ?1")
	void updateHasChild(long locationId);

	Optional<GeoLocationMaster> findByLocationId(Long locId);

	@Query(nativeQuery = true, value = "SELECT * FROM SD_GEO_LOCATION_MASTER WHERE "
			+ "PARENT_LOCATION_ID = ?1 OR LOCATION_ID = ?1 AND STATUS = ?2 ORDER BY LOCATION_NAME ASC")
	List<GeoLocationMaster> findByParentLocationIdOrLocationIdAndStatusOrderByLocationNameAsc(long locationId,
			int status);

	Optional<GeoLocationMaster> findByLocationName(String locationName);

	List<GeoLocationMaster> findByParentLocationIdAndStatus(Long locId, int statusActiveId);

}
