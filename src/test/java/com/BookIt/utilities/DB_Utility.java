package com.BookIt.utilities;

import java.sql.*;
import java.util.*;

public class DB_Utility {
    //make tha var available inside the whole class
    private static Connection con;
    private static Statement stmnt;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;


    /**
     * This method will reset the cursor to beforeFirst() location
     */
    public static void resetCursor() {
        try {
            rs.beforeFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * static method to create Connection with valid url and username/password
     */
    public static void createConnection() {
        String url = ConfigurationReader.getProperty("dbUrl");
        String username = ConfigurationReader.getProperty("dbUsername");
        String password = ConfigurationReader.getProperty("dbPassword");

//        try {
//            con = DriverManager.getConnection(url, "hr", "hr");
//            System.out.println("CONNECTION SUCCESSFUL");
//
//        } catch (SQLException e) {
//            System.err.println("CONNECTION HAS FAILED " + e.getMessage());
//        }

        createConnection(url, username, password);
    }

    /**
     * Create Connection by jdbc url and username , password provided
     *
     * @param url
     * @param username
     * @param password
     */
    public static void createConnection(String url, String username, String password) {

        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("CONNECTION SUCCESSFUL");

        } catch (SQLException e) {
            System.err.println("CONNECTION HAS FAILED " + e.getMessage());
        }
    }

    /**
     * a static method to get the ResultSet object with valid connection by executing query
     *
     * @param query
     * @return ResultSet object that contains the result just in cases needed outside the class
     */
    public static ResultSet runQuery(String query) {
        try {
            stmnt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmnt.executeQuery(query);//setting the value of ResultSet
            rsmd = rs.getMetaData(); //setting the value of ResultSet MetaData
        } catch (SQLException e) {
            System.err.println("Error while getting ResultSet" + e.getMessage());
        }
        return rs;
    }

    /**
     * cleaning up the resources
     */
    public static void destroy() {

        try {
            if (rs != null) {
                rs.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            if (con != null) {
                con.close();
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while closing the resources " + e.getMessage());
        }
    }

    /**
     * Get the row count of the ResultSet
     *
     * @return the row number of the ResultSet given
     */
    public static int getRowCount() {
        int rowCount = 0;
        try {
            rs.last();
            rowCount = rs.getRow();
            rs.beforeFirst();
        } catch (SQLException e) {
            System.err.println("error while getting Row Count " + e.getMessage());
        }
        return rowCount;
    }

    /**
     * get the Column Count of the current ResultSet
     * getColumnCount()
     */
    public static int getColumnCount() {
        int columnCount = 0;
        try {
            columnCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.err.println("ERROR while counting the Columns " + e.getMessage());
        }
        return columnCount;
    }

    /**
     * Get all the column names into a list object
     */
    public static List<String> getAllColumnNamesAsList() {
        List<String> columnNamesList = new ArrayList<>();
        try {
            for (int colIndex = 1; colIndex <= getColumnCount(); colIndex++) {
                String columnName = rsmd.getColumnName(colIndex);
                columnNamesList.add(columnName);
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while getAllColumnNameAsList " + e.getMessage());
        }
        return columnNamesList;

    }

    /**
     * Get the entire Row of data according to row number
     */
    public static List<String> getRowDataAsList(int rowNum) {
        List<String> rowDataList = new ArrayList<>();
        int colCount = getColumnCount();
        try {
            rs.absolute(rowNum);
            for (int colIndex = 1; colIndex <= colCount; colIndex++) {
                String cellValue = rs.getString(colIndex);
                rowDataList.add(cellValue);
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while getRowDataAsList " + e.getMessage());
        }
        return rowDataList;
    }

    /**
     * Get the cell value according to row ans column index
     *
     * @param rowNum
     * @param columnIndex
     * @return cellValue
     */
    public static String getCellValue(int rowNum, int columnIndex) {
        String cellValue = "";

        try {
            rs.absolute(rowNum);
            cellValue = rs.getString(columnIndex);
        } catch (SQLException e) {
            System.err.println("Error while  getCellValue " + e.getMessage());
        }
        return cellValue;
    }

    /**
     * Get the cell value according to row ans column name
     *
     * @param rowNum
     * @param columnName
     * @return cellValue
     */
    public static String getCellValue(int rowNum, String columnName) {
        String cellValue = "";

        try {
            rs.absolute(rowNum);
            cellValue = rs.getString(columnName);
        } catch (SQLException e) {
            System.err.println("Error while  getCellValue " + e.getMessage());
        }
        return cellValue;
    }

    /**
     * GETTING ENTIRE COLUMN DATA AS lIST ACCORDING TO COLUMN NUMBER
     *
     * @param columnNum
     * @return List object that contains all the rows of that column
     */
    public static List<String> getColumnDataAsList(int columnNum) {
        List<String> columnDataList = new ArrayList<>();
        try {
            rs.beforeFirst();
            while (rs.next()) {
                String cellValue = rs.getString(columnNum);
                columnDataList.add(cellValue);
            }
            rs.beforeFirst();
        } catch (SQLException e) {
            System.err.println("Error occurred while etColumnDataAsList " + e.getMessage());
        }

        return columnDataList;
    }

    /**
     * GETTING ENTIRE COLUMN DATA AS lIST ACCORDING TO COLUMN NAME
     *
     * @param columnName
     * @return List object that contains all the rows of that column
     **/
    public static List<String> getColumnDataAsList(String columnName) {
        List<String> columnDataList = new ArrayList<>();
        resetCursor();
        try {
            while (rs.next()) {
                String cellValue = rs.getString(columnName);
                columnDataList.add(cellValue);
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while etColumnDataAsList " + e.getMessage());
        } finally {
            resetCursor();
        }

        return columnDataList;
    }

    /**
     * Display all data from the ResultSet object
     */
    public static void displayAllData() {
        int columnCount = getColumnCount();

        resetCursor();
        try {
            while (rs.next()) {
                for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
                    //System.out.print(rs.getString(colIndex) + "\t");
                    System.out.printf("%-35s", rs.getString(colIndex));
                }
                System.out.println();
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while displayAllData " + e.getMessage());
        } finally {
            resetCursor();
        }
    }

    /**
     * Save entire row data as Map<String,String>
     *
     * @param rowNum
     * @return Map object that contains key value pair
     * key     : column name
     * value   : cell value
     */
    public static Map<String, String> getRowMap(int rowNum) {
        Map<String, String> rowMap = new LinkedHashMap<>();
        int columnCount = getColumnCount();

        try {
            rs.absolute(rowNum);
            for (int colIndex = 1; colIndex <= columnCount; colIndex++) {
                String columnName = rsmd.getColumnName(colIndex);
                String cellValue = rs.getString(colIndex);
                rowMap.put(columnName, cellValue);
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while getRowMap() " + e.getMessage());
        }
        return rowMap;
    }

    /**
     * Store all Rows as List of Map Object
     * @return List of Map object that contains each row data as Map<String, String>
     */
    public static List<Map<String,String>> getAllRowsAsListOfMap(){
        List<Map<String,String>> allRowListOfMap = new ArrayList<>();
        int rowCount = getRowCount();

        //move from 1st row to the last row
        //get each row as map object and add it to that list

        for (int rowIndex = 1; rowIndex <= rowCount; rowIndex++){

            Map<String,String> rowMap = getRowMap(rowIndex);
            allRowListOfMap.add(rowMap);
        }
        resetCursor();

        return allRowListOfMap;
    }

    /**
     * Get first cell value at first row, first column
     */
    public static String getFirstRowFirstColumn(){

        return getCellValue(1,1);

    }

    // List of Maps

    public static List<Map<String, Object>> getQueryResultMap(String query) {
        runQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        try {
            rsmd = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> colNameValueMap = new HashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    colNameValueMap.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rowList;
    }


}
