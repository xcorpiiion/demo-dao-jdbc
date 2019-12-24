/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao.implement;

import db.Database;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author User
 */
public class SellerDaoJDBC implements SellerDao{

    private Connection Connection;

    public SellerDaoJDBC(Connection Connection) {
        this.Connection = Connection;
    }
    
    @Override
    public void insert(Seller departament) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Seller department) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = Connection.prepareStatement("select seller.*, department.Name as DepName "
            + "from seller inner join department on seller.DepartmentId = department.Id where seller.id = ?");
            
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Department department = new Department();
                department.setId(resultSet.getInt("DepartmentId"));
                department.setName(resultSet.getString("DepName"));
                Seller seller = new Seller();
                seller.setId(resultSet.getInt("Id"));
                seller.setDataNascimento(resultSet.getDate("BirthDate"));
                seller.setDepartment(department);
                seller.setEmail(resultSet.getString("Email"));
                seller.setName(resultSet.getString("Name"));
                seller.setSalarioBase(resultSet.getDouble("BaseSalary"));
                return seller;
            }
            return null;
            
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally{
            Database.fecharStatement(preparedStatement);
            Database.fecharResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
