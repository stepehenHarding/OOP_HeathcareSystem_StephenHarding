package Server;

import java.sql.SQLException;

public class DAOException extends SQLException
{


    public DAOException(String aMessage)
    {
        super(aMessage);
    }
}