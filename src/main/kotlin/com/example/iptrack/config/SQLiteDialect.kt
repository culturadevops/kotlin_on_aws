package com.example.iptrack.config

import org.hibernate.dialect.Dialect
import org.hibernate.dialect.function.*
import org.hibernate.type.StandardBasicTypes
import java.sql.Types

class SQLiteDialect : Dialect() {
    fun supportsIdentityColumns(): Boolean {
        return true
    }

    /*
  public boolean supportsInsertSelectIdentity() {
    return true; // As specify in NHibernate dialect
  }
  */
    fun hasDataTypeInIdentityColumn(): Boolean {
        return false // As specify in NHibernate dialect
    }// return "integer primary key autoincrement";

    /*
 public String appendIdentitySelectToInsert(String insertString) {
   return new StringBuffer(insertString.length()+30). // As specify in NHibernate dialect
     append(insertString).
     append("; ").append(getIdentitySelectString()).
     toString();
 }
 */
    val identityColumnString: String
        get() =// return "integer primary key autoincrement";
            "integer"

    val identitySelectString: String
        get() = "select last_insert_rowid()"

    override fun supportsLimit(): Boolean {
        return true
    }

    override fun getLimitString(query: String, hasOffset: Boolean): String {
        return StringBuffer(query.length + 20).append(query).append(if (hasOffset) " limit ? offset ?" else " limit ?")
            .toString()
    }

    fun supportsTemporaryTables(): Boolean {
        return true
    }

    val createTemporaryTableString: String
        get() = "create temporary table if not exists"

    fun dropTemporaryTableAfterUse(): Boolean {
        return false
    }

    override fun supportsCurrentTimestampSelection(): Boolean {
        return true
    }

    override fun isCurrentTimestampSelectStringCallable(): Boolean {
        return false
    }

    override fun getCurrentTimestampSelectString(): String {
        return "select current_timestamp"
    }

    override fun supportsUnionAll(): Boolean {
        return true
    }

    override fun hasAlterTable(): Boolean {
        return false // As specify in NHibernate dialect
    }

    override fun dropConstraints(): Boolean {
        return false
    }

    override fun getAddColumnString(): String {
        return "add column"
    }

    override fun getForUpdateString(): String {
        return ""
    }

    override fun supportsOuterJoinForUpdate(): Boolean {
        return false
    }

    override fun getDropForeignKeyString(): String {
        throw UnsupportedOperationException("No drop foreign key syntax supported by SQLiteDialect")
    }

    override fun getAddForeignKeyConstraintString(
        constraintName: String,
        foreignKey: Array<String>, referencedTable: String, primaryKey: Array<String>,
        referencesPrimaryKey: Boolean
    ): String {
        throw UnsupportedOperationException("No add foreign key syntax supported by SQLiteDialect")
    }

    override fun getAddPrimaryKeyConstraintString(constraintName: String): String {
        throw UnsupportedOperationException("No add primary key syntax supported by SQLiteDialect")
    }

    override fun supportsIfExistsBeforeTableName(): Boolean {
        return true
    }

    override fun supportsCascadeDelete(): Boolean {
        return false
    }

    init {
        registerColumnType(Types.BIT, "integer")
        registerColumnType(Types.TINYINT, "tinyint")
        registerColumnType(Types.SMALLINT, "smallint")
        registerColumnType(Types.INTEGER, "integer")
        registerColumnType(Types.BIGINT, "bigint")
        registerColumnType(Types.FLOAT, "float")
        registerColumnType(Types.REAL, "real")
        registerColumnType(Types.DOUBLE, "double")
        registerColumnType(Types.NUMERIC, "numeric")
        registerColumnType(Types.DECIMAL, "decimal")
        registerColumnType(Types.CHAR, "char")
        registerColumnType(Types.VARCHAR, "varchar")
        registerColumnType(Types.LONGVARCHAR, "longvarchar")
        registerColumnType(Types.DATE, "date")
        registerColumnType(Types.TIME, "time")
        registerColumnType(Types.TIMESTAMP, "timestamp")
        registerColumnType(Types.BINARY, "blob")
        registerColumnType(Types.VARBINARY, "blob")
        registerColumnType(Types.LONGVARBINARY, "blob")
        // registerColumnType(Types.NULL, "null");
        registerColumnType(Types.BLOB, "blob")
        registerColumnType(Types.CLOB, "clob")
        registerColumnType(Types.BOOLEAN, "boolean")
        registerFunction("concat", VarArgsSQLFunction(StandardBasicTypes.STRING, "", "||", ""))
        registerFunction("mod", SQLFunctionTemplate(StandardBasicTypes.INTEGER, "?1 % ?2"))
        registerFunction("quote", StandardSQLFunction("quote", StandardBasicTypes.STRING))
        registerFunction("random", NoArgSQLFunction("random", StandardBasicTypes.INTEGER))
        registerFunction("round", StandardSQLFunction("round"))
        registerFunction("substr", StandardSQLFunction("substr", StandardBasicTypes.STRING))
        registerFunction("trim", object : AbstractAnsiTrimEmulationFunction() {
            override fun resolveBothSpaceTrimFunction(): SQLFunction {
                return SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1)")
            }

            override fun resolveBothSpaceTrimFromFunction(): SQLFunction {
                return SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?2)")
            }

            override fun resolveLeadingSpaceTrimFunction(): SQLFunction {
                return SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(?1)")
            }

            override fun resolveTrailingSpaceTrimFunction(): SQLFunction {
                return SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(?1)")
            }

            override fun resolveBothTrimFunction(): SQLFunction {
                return SQLFunctionTemplate(StandardBasicTypes.STRING, "trim(?1, ?2)")
            }

            override fun resolveLeadingTrimFunction(): SQLFunction {
                return SQLFunctionTemplate(StandardBasicTypes.STRING, "ltrim(?1, ?2)")
            }

            override fun resolveTrailingTrimFunction(): SQLFunction {
                return SQLFunctionTemplate(StandardBasicTypes.STRING, "rtrim(?1, ?2)")
            }
        })
    }
}