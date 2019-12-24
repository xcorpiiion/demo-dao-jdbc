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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author User
 */
public class SellerDaoJDBC implements SellerDao {

    private Connection Connection;

    public SellerDaoJDBC(Connection Connection) {
        this.Connection = Connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = Connection.prepareStatement("insert into seller(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getDataNascimento().getTime()));
            preparedStatement.setDouble(4, seller.getSalarioBase());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            
            int linhasAfetadas = preparedStatement.executeUpdate();
            
            if(linhasAfetadas > 0){
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(preparedStatement == null) {
                    int id = resultSet.getInt(1);
                    seller.setId(id);
                    
                }
                Database.fecharResultSet(resultSet);
            } else{
                throw  new DbException("Erro inesperado, nenhuma linha foi afetada");
            }
            
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally{
            Database.fecharStatement(preparedStatement);
            
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = Connection.prepareStatement("update seller set Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, "
                    + "DepartmentId = ? "
                    + "where id = ?");
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getEmail());
            preparedStatement.setDate(3, new java.sql.Date(seller.getDataNascimento().getTime()));
            preparedStatement.setDouble(4, seller.getSalarioBase());
            preparedStatement.setInt(5, seller.getDepartment().getId());
            preparedStatement.setInt(6, seller.getId());
            
            preparedStatement.executeUpdate();
            
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally{
            Database.fecharStatement(preparedStatement);
            
        }
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
            if (resultSet.next()) {
                Department department = instanciarDepartment(resultSet);
                Seller seller = instanciarSeller(resultSet, department);
                return seller;
            }
            return null;

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.fecharStatement(preparedStatement);
            Database.fecharResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = Connection.prepareStatement("select seller.*, department.Name as DepName "
                    + "from seller inner join department on seller.DepartmentId = department.Id order by Name");

            resultSet = preparedStatement.executeQuery();
            List<Seller> listSeller = new ArrayList<>();

            Map<Integer, Department> mapDepartment = new HashMap<>();

            while (resultSet.next()) {
                Department department = mapDepartment.get(resultSet.getInt("DepartmentId"));
                if (department == null) {
                    department = instanciarDepartment(resultSet);
                    mapDepartment.put(resultSet.getInt("DepartmentId"), department);
                }
                Seller seller = instanciarSeller(resultSet, department);
                listSeller.add(seller);
                
            }
            return listSeller;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.fecharStatement(preparedStatement);
            Database.fecharResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDeparment(Department department) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = Connection.prepareStatement("select seller.*, department.Name as DepName "
                    + "from seller inner join department on seller.DepartmentId = department.Id where DepartmentId = ? order by Name");

            preparedStatement.setInt(1, department.getId());
            resultSet = preparedStatement.executeQuery();
            List<Seller> listSeller = new ArrayList<>();

            Map<Integer, Department> mapDepartment = new HashMap<>();

            while (resultSet.next()) {
                department = mapDepartment.get(resultSet.getInt("DepartmentId"));
                if (department == null) {
                    department = instanciarDepartment(resultSet);
                    mapDepartment.put(resultSet.getInt("DepartmentId"), department);
                }
                Seller seller = instanciarSeller(resultSet, department);
                listSeller.add(seller);
                
            }
            return listSeller;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            Database.fecharStatement(preparedStatement);
            Database.fecharResultSet(resultSet);
        }
    }

    private Department instanciarDepartment(ResultSet resultSet) {
        Department department = new Department();
        try {
            department.setId(resultSet.getInt("DepartmentId"));
            department.setName(resultSet.getString("DepName"));
            return department;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    private Seller instanciarSeller(ResultSet resultSet, Department department) {
        Seller seller = new Seller();
        try {
            seller.setId(resultSet.getInt("Id"));
            seller.setDataNascimento(resultSet.getDate("BirthDate"));
            seller.setDepartment(department);
            seller.setEmail(resultSet.getString("Email"));
            seller.setName(resultSet.getString("Name"));
            seller.setSalarioBase(resultSet.getDouble("BaseSalary"));
            return seller;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }

    }

}
