/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.dao.jdbc;


import java.text.SimpleDateFormat;
import java.util.Date;
import model.entities.Department;
import model.entities.Seller;

/**
 *
 * @author User
 */
public class DemoDaoJdbc {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Department department = new Department(1, "Livros");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        Seller seller = new Seller(21, "Lucas", "lucas@gmail.com",  new Date(),  2000.00, department);
        
        System.out.println(seller);
    }
    
}
