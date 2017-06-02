package com.tvnsoftware.simpleapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tvnsoftware.simpleapp.model.Task;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TamHH on 6/1/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Task, Integer> mTaskDao = null;
    private RuntimeExceptionDao<Task, ?> m;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Task.class);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Task.class, true);
            onCreate(database, connectionSource);
        } catch (java.sql.SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> getAll(Class<T> clazz) throws SQLException, java.sql.SQLException {
        Dao<T, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public <T> List<T> getAllOrdered(Class<T> clazz, String orderBy, boolean ascending) throws SQLException, java.sql.SQLException {
        Dao<T, ?> dao = getDao(clazz);
        return dao.queryBuilder().orderBy(orderBy, ascending).query();
    }

    public <T> void fillObject(Class<T> clazz, T aObj) throws SQLException, java.sql.SQLException {
        Dao<T, ?> dao = getDao(clazz);
        dao.createOrUpdate(aObj);
    }

    public <T> void fillObjects(Class<T> clazz, Collection<T> aObjList) throws SQLException, java.sql.SQLException {
        Dao<T, ?> dao = getDao(clazz);
        for (T obj : aObjList) {
            dao.createOrUpdate(obj);
        }
    }

    public <T> T getById(Class<T> clazz, Object aId) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public <T> List<T> query(Class<T> clazz, Map<String, Object> aMap) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);

        return dao.queryForFieldValues(aMap);
    }

    public <T> List<T> queryNot(Class<T> clazz, String columnName, int value) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);

        return dao.queryBuilder().where().ne(columnName, value).query();
    }

    public <T> T queryFirst(Class<T> clazz, Map<String, Object> aMap) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        List<T> list = dao.queryForFieldValues(aMap);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    public <T> Dao.CreateOrUpdateStatus createOrUpdate(T obj) throws SQLException {
        Dao<T, ?> dao = (Dao<T, ?>) getDao(obj.getClass());
        return dao.createOrUpdate(obj);
    }

    public <T> int deleteById(Class<T> clazz, Object aId) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }

    public <T> int deleteObjects(Class<T> clazz, Collection<T> aObjList) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);

        return dao.delete(aObjList);
    }

    public <T> void deleteAll(Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        dao.deleteBuilder().delete();
    }

    public static HashMap<String, Object> where(String aVar, Object aValue) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put(aVar, aValue);
        return result;
    }
}
