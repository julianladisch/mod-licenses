package org.olf.licenses

import com.k_int.okapi.OkapiClient

import groovy.util.logging.Slf4j

@Slf4j
class LicenseService {
  
  OkapiClient okapiClient
  
  List<Serializable> checkAttachedAgreements(License license) {
    List<Serializable> attachedAgreements = []
    
    if (okapiClient.withTenant().providesInterface("erm", "^2.3")) {
      // Needs to be blocking...
      List links = okapiClient.getSync("/erm/sas/linkedLicenses", [
        filters: [
          "remoteId==${license.id}"
        ]
      ])
      
      if (links?.size() > 0) {
        attachedAgreements.addAll( links.collect { "${it.id}" } )
      }
    }
    
    
    return attachedAgreements
  }
}
