package Server;

import Core.Centre;
import java.util.List;
public interface CentreDaoInterface {
    public List<Centre>findAllCentres()throws DAOException;
    public Centre findCentre(String location)throws DAOException;
}
