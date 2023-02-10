/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.seata.rm.datasource.exception;

import java.sql.SQLException;

/**
 * The type TableMetaException exception.
 *
 * @author Bughue
 */
public class TableMetaException extends SQLException {
    private String columnName;
    private String tableName;
    private boolean refreshable;

    public TableMetaException(String columnName, String tableName,boolean refreshable) {
        this.columnName = columnName;
        this.tableName = tableName;
        this.refreshable = refreshable;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public boolean isRefreshable() {
        return refreshable;
    }
}
