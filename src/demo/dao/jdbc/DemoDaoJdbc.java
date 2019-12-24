/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.dao.jdbc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
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
        SellerDao sellerDao = DaoFactory.createSellerDao();

        Seller seller = sellerDao.findById(3);

        System.out.println(seller);
        System.out.println("----------------------------");

        Department department = new Department(2, null);
        List<Seller> listSellers = sellerDao.findByDeparment(department);

        for (Seller sellers : listSellers) {
            System.out.println(sellers);
        }
        
        System.out.println("--------------------------------");
        
        listSellers = sellerDao.findAll();

        for (Seller sellers : listSellers) {
            System.out.println(sellers);
        }
        
        System.out.println("-------------------------");
        
        //seller = new Seller(null, "Lucas", "lucas@gmail.com", new Date(), 2000.00, department);
        //sellerDao.insert(seller);
        
        System.out.println("Id: " + seller.getId());
        
        System.out.println("-------------------------------------------------------------");
        seller = sellerDao.findById(2);
        seller.setName("Vergil");
        seller.setEmail("vergilDMC@gmail.com");
        sellerDao.update(seller);
        System.out.println("Completo");
    }

}
