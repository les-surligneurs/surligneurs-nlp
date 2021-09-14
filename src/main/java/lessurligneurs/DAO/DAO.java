package lessurligneurs.DAO;
import java.lang.*;
import java.util.*;
import java.sql.Connection;


 
public abstract class DAO<T> 
{  
    public Connection connect = Connector.getInstance();

    protected  abstract void  create(T obj);

    protected abstract T find(String title);

    protected abstract void  update(T obj);

    protected abstract void delete (T obj );
}