package Server;

import Core.Colours;
import Core.Patient;
import Server.DAOException;
import Server.MySqlDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;


public class MySqlPatient extends MySqlDao implements PatientDaoInterface {

    @Override
    public boolean login(Patient p) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Patient u = null;
        boolean login = false;

        try
        {
            con = this.getConnection();

            String query = "SELECT PASSWORD FROM EMAIL WHERE email = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, p.getEmail());


            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {


                String password = rs.getString("PASSWORD");

                u = new Patient(p.getUser_id(),p.getEmail(), password);

            }

            if (p.equals(u))
            {
                login = true;
            }
        } catch (SQLException e)
        {
            throw new DAOException(""+e.getMessage());
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
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DAOException(""+e.getMessage());
            }
        }


        return login;
    }

    @Override
    public boolean registerPatient(Patient newPatient) throws DAOException {


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
    }




    @Override
    public boolean isregistered(Patient newPatient) throws DAOException {
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs = null;

        try
        {
            connection= this.getConnection();
            String query= "select * from user where email = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, newPatient.getEmail());

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

}