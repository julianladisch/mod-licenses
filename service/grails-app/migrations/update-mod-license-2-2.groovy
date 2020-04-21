databaseChangeLog = {
  changeSet(author: "efreestone (manual)", id: "202003171414-1") {
    modifyDataType(
      tableName: "custom_property_definition",
      columnName: "pd_description", type: "text",
      newDataType: "text",
      confirm: "Successfully updated the pd_description column."
    )
  }

  changeSet(author: "sosguthorpe (generated)", id: "1584554943442-1") {
    addColumn(tableName: "refdata_category") {
      column(name: "internal", type: "boolean")
    }
    addNotNullConstraint (tableName: "refdata_category", columnName: "internal", defaultNullValue: false)
  }
  
  changeSet(author: "sosguthorpe (generated)", id: "1584554943442-1") {
    addColumn(tableName: "refdata_category") {
      column(name: "internal", type: "boolean")
    }
    addNotNullConstraint (tableName: "refdata_category", columnName: "internal", defaultNullValue: false)
  }
  
  changeSet(author: "sosguthorpe (generated)", id: "1584554943442-2") {
    grailsChange {
      change {
        // Change all categories to internal where necessary. 
        sql.execute("""
          UPDATE ${database.defaultSchemaName}.refdata_category SET internal = true
            WHERE rdc_description IN ('License.EndDateSemantics','License.Status','LicenseOrg.Role')
        """.toString())
      }
    }
  }

  changeSet(author: "claudia (manual)", id: "202004211400-01") {
        createTable(tableName: "alternate_name") {
            column(name: "an_id", type: "VARCHAR(36)") {
                constraints(nullable: "false")
            }

            column(name: "an_version", type: "BIGINT") {
                constraints(nullable: "false")
            }

            column(name: "an_owner_fk", type: "VARCHAR(36)")

            column(name: "an_name", type: "VARCHAR(255)")
        }
  }

  changeSet(author: "claudia (manual)", id: "2202004211400-02") {
        addForeignKeyConstraint(baseColumnNames: "an_owner_fk", baseTableName: "alternate_name", constraintName: "an_to_lic_fk", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "lic_id", referencedTableName: "license")
  }

}
