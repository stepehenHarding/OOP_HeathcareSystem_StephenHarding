package Server;

import Core.Centre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCentreDao extends MySqlDao implements CentreDaoInterface{
    @Override
    public List<Centre> findAllCentres() throws DAOException {
        Connection con = null;
        PreparedStatement ps =null;
        ResultSet rs = null;
        List<Centre> centres = new ArrayList<>();

        try
        {
            con=this.getConnection();

            String query ="SELECT * FROM COURSE";
            ps=con.prepareStatement(query);

            rs=ps.executeQuery();
            while(rs.next())
            {
                int centre_id =rs.getInt("centre_id");
                String location =rs.getString("location");
                Centre c = new Centre (centre_id,location);
                centres.add(c);
            }
        }catch (SQLException e)
        {
            throw new DAOException("findAllCourses"+e.getMessage());
        }finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(ps!= null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }catch (SQLException e)
            {
                throw new DAOException("findAllCourses()"+e.getMessage());
            }
        }
        return centres;
    }

    @Override
    public Centre findCentre(String location) throws DAOException {
        return null;
    }
}
