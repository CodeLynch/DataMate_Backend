package com.capstone.datamate.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capstone.datamate.Entity.TableEntity;
import com.capstone.datamate.Repository.TableRepository;
import jakarta.persistence.EntityManager;

@Service
public class TableService {
    
    private final EntityManager entityManager;
    
    @Autowired
    TableRepository trepo;

    public TableEntity postTable(TableEntity tbl){
       return trepo.save(tbl);    
    }

    public TableEntity getTable(String tblName){
        return trepo.findByTableName(tblName);
    }

    public List<TableEntity> getTablesByDB(int dbId){
        return trepo.findByDatabaseDatabaseId(dbId);
    }

        

    @Autowired
    public TableService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Object[]> executeNativeQuery(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        List<Object[]> objList = new ArrayList<Object[]>();
        objList = castList(Object[].class, entityManager.createNativeQuery(sql).getResultList());
        return objList;
    }
    
    
    public static <T> List<T> castList(Class<? extends T> clazz, Collection<?> c) {
    List<T> r = new ArrayList<T>(c.size());
    for(Object o: c)
      r.add(clazz.cast(o));
    return r;
}


}
