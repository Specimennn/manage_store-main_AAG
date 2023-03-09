package co.edu.umanizales.manage_store.service;

import co.edu.umanizales.manage_store.model.Sale;
import co.edu.umanizales.manage_store.model.Seller;
import co.edu.umanizales.manage_store.model.Store;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
@Setter
public class SaleService {

    private List<Sale> sales;
    private  SellerService sellerService;

    public  SaleService() {this.sales = new ArrayList<>();
            //sales.add(new Sale((StoreService.getStores().get(1)),));
        }

    public  void addSale(Sale sale ){
         this.sales.add(sale);
    }

    public int getTotalSales() {

        int sum = 0;
        for (Sale sale:sales){
            sum = sum + sale.getQuantity();

        }
        return sum;

    }

    public int getTotalSalesBySeller(String sellerCode) {

        int sum = 0;
        for (Sale sale:sales){
            if (sale.getSeller().getCode().equals(sellerCode)){
                sum = sum + sale.getQuantity();
            }


        }
        return sum;

    }

    public int getTotalSalesByStore(String storeCode) {
        int sum = 0;
        for (Sale sale:sales){
            if (sale.getStore().getCode().equals(storeCode)){
                sum = sum + sale.getQuantity();
            }
        }
        return sum;
    }

    public Seller BestSeller(List<Seller> sellers) {



        if (sellers.size() > 0){

            Seller bestSeller = sales.get(0).getSeller();

            for (Seller seller:sellers){

                if(getTotalSalesBySeller(seller.getCode()) > getTotalSalesBySeller(bestSeller.getCode())){
                    bestSeller = seller;

                }

            }
            return bestSeller;
        }
        return null;

    }

    public Store BestStore() {


        if (sales.size() > 0) {
            Store bestStore = sales.get(0).getStore();
            for (Sale sale : sales) {
                if (getTotalSalesByStore(sale.getStore().getCode()) > getTotalSalesByStore(bestStore.getCode())) {
                    bestStore = sale.getStore();

                }
            }
            return bestStore;
        } else {
            return null;
        }

    }

    public int TotalSales(){
        int sum = 0;
        for(Sale sale:sales){
            sum += sale.getQuantity();
        }

        return sum;
    }

    public int GetTotalSalesByStore(String storeCode){
        int sum = 0;
            for(Sale sale:sales){
                if (sale.getStore().getCode().equals(storeCode)){
                    sum = sum + sale.getQuantity();
                }
            }

            return sum;
        }

    public float AverageSalesBySellers(){

        List <Seller> sellers = sellerService.getSellers();
        float average = 0;
        int sum = 0;
        for (Seller seller:sellers){
            sum = sum + getTotalSalesBySeller(seller.getCode());
        }
        average = sum/sellers.size();
        return average;
    }
}
