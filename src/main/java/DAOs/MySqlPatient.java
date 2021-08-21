package DAOs;

import Core.Colours;
import Core.Patient;
import Server.DAOException;

import java.sql.*;
import java.util.HashMap;

public class MySqlPatient extends MySqlDao
{

    public boolean registerPatient(Patient newPatient) throws DAOException {
    if (!checkIfPatientExists(newPatient))
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = this.getConnection();

            String query = "insert into Patient values (?,?,?)";
            ps = connection.prepareStatement(query);

            ps.setInt(1, newPatient.getUser_id());
            ps.setString(2, newPatient.getEmail());
            ps.setString(3, newPatient.getPassword());

            ps.executeUpdate();
            return true;
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "There was an error in registerStudent() " + se.getMessage() + Colours.RESET);
        }
        finally
        {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (connection != null) {
                    freeConnection(connection);
                }
            } catch (SQLException se)
            {
                throw new DAOException(Colours.RED + "There was an error in registerStudent() finally " + se.getMessage() + Colours.RESET);
            }
        }
    }return false;
}


    public boolean checkIfPatientExists(Patient newPatient) throws DAOException
    {
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs = null;

        try
        {
            connection= this.getConnection();
            String query= "select * from user where email = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, newPatient.getUser_id());

            rs =ps.executeQuery();

            if(rs.next())
            {
                return true;
            }
        }catch (SQLException e)
        {
            System.out.println("Problem in checkIfTaskExists() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (connection != null)
                {
                    freeConnection(connection);
                }
            } catch (SQLException e)
            {
                System.out.println("checkIfTaskExists finally() block " + e.getMessage());
            }
        }
        return false;
    }



    public Patient findPatientByEmail(String email) throws DAOException
    {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Patient returnedPatient = null;

        try
        {
            connection = this.getConnection();

            String query = "select * from user where email = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if(rs.next())
            {
                int dbEmail = rs.getInt("email");
                String dbUser_id = rs.getString("user_id");


                String dbPassword = rs.getString("password");

                Patient readInPatient = new Patient(dbEmail,dbUser_id,dbPassword);
            }
            else{
                System.out.println(Colours.RED + "student cannot be found"+ Colours.RESET);
            }
        }
        catch (SQLException se)
        {
            throw new DAOException(Colours.RED + "There was an error in findPatientByEmail() " + se.getMessage() + Colours.RESET);
        }
        finally
        {
            try
            {
                if(rs != null)
                {
                    rs.close();
                }
                if(ps != null)
                {
                    ps.close();
                }
                if(connection != null)
                {
                    freeConnection(connection);
                }
            }
            catch (SQLException se)
            {
                throw new DAOException(Colours.RED + "There was an error findPatientByEmail() finally " + se.getMessage() + Colours.RESET);
            }
        }
        return returnedPatient;
    }
}