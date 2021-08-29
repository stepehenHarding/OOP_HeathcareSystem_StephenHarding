package Server;

import Core.Patient;


public interface PatientDaoInterface {
    public boolean registerPatient(Patient p) throws DAOException;
    public boolean login(Patient p) throws DAOException;
    public boolean isregistered(Patient p) throws DAOException;
}
