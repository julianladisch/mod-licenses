package org.olf.licenses.export

import grails.databinding.BindUsing
import grails.databinding.BindingHelper
import grails.databinding.DataBindingSource
import grails.validation.Validateable
import groovy.transform.CompileStatic

@BindUsing(ExportControlObject.Binder)
@CompileStatic
class ExportControlObject implements Validateable {
  
  public static final List<String> CUST_PROP_PART_ORDER = [ 'value', 'internal', 'note', 'publicNote' ] 
  
  List<String> ids = []
  LinkedHashMap<String, ?> include = [:] 
  Map<String, Boolean> terms = [:]
  
//  private List<String> rootPropertyOrder = null
//  List<String> getRootPropertyOrder() {
//    if (rootPropertyOrder == null) {
//      rootPropertyOrder = []
//      if (include) {
//        rootPropertyOrder.addAll(include.keySet().findResults({ it != 'customProperties' ? it : null }))
//      }
//    }
//    
//    rootPropertyOrder
//  }
//  
//  private List<String> customPropertyOrder = null
//  List<String> getCustomPropertyOrder() {
//    if (customPropertyOrder == null) {
//      customPropertyOrder = []
//      if (include) {
//        customPropertyOrder.addAll(include.keySet())
//      }
//    }
//    customPropertyOrder
//  }
  
  private static class Binder implements BindingHelper {

    @Override
    public Object getPropertyValue(final Object obj, final String propertyName, final DataBindingSource source) {
      
      def propVal = obj[propertyName]
      
      switch (propertyName) {
        case "ids" :
          (propVal as Collection).addAll(source.getPropertyValue(propertyName) as Collection)
          break
        case 'include':
        case 'terms':
          (propVal as Map).putAll(source.getPropertyValue(propertyName) as Map)
          break
        default: 
          propVal = source.getPropertyValue(propertyName)
      }
      
      propVal
    }
    
  }
}
