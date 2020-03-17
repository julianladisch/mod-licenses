databaseChangeLog = {
  changeSet(author: "efreestone (manual)", id: "202003171414-1") {
      modifyDataType( 
        tableName: "custom_property_definition", 
        columnName: "pd_description", type: "text",
        newDataType: "text",
        confirm: "Successfully updated the pd_description column."
      )
  }
}
