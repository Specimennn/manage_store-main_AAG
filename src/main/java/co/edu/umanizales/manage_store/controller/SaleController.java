package co.edu.umanizales.manage_store.controller;

import co.edu.umanizales.manage_store.controller.dto.ResponseDTO;
import co.edu.umanizales.manage_store.controller.dto.SaleDTO;
import co.edu.umanizales.manage_store.model.Sale;
import co.edu.umanizales.manage_store.model.Seller;
import co.edu.umanizales.manage_store.model.Store;
import co.edu.umanizales.manage_store.service.SaleService;
import co.edu.umanizales.manage_store.service.SellerService;
import co.edu.umanizales.manage_store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "sale")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<ResponseDTO> getSales(){
        return new ResponseEntity<>(
                new ResponseDTO(200,
                        saleService.getSales(),
                        null),
                HttpStatus.OK);
    }

    @GetMapping(path="/total")
    public ResponseEntity<ResponseDTO> getTotalSales(){
        return new ResponseEntity<>(new ResponseDTO(200,
                saleService.getTotalSales(),null),
                HttpStatus.OK);
        
    }

    @GetMapping(path="/total/{code}")
    public ResponseEntity<ResponseDTO> getTotalSalesBySeller(@PathVariable String code){
        return new ResponseEntity<>(new ResponseDTO(200,
                saleService.getTotalSalesBySeller(code),null),
                HttpStatus.OK);

    }

    @GetMapping(path="/storetotal/{code}")
    public ResponseEntity<ResponseDTO> getTotalSalesByStore(@PathVariable String code){
        return new ResponseEntity<>(new ResponseDTO(200,
                saleService.getTotalSalesByStore(code),null),
                HttpStatus.OK);

    }

    @GetMapping(path="/totalsales")
    public ResponseEntity<ResponseDTO> TotalSales(){
        return new ResponseEntity<>(new ResponseDTO(200,
                saleService.TotalSales(),null),
                HttpStatus.OK);

    }

    @GetMapping(path="/averagesalesbysellers")
    public ResponseEntity<ResponseDTO> AverageSalesBySellers(){
        return new ResponseEntity<>(new ResponseDTO(200,
                saleService.getTotalSales()/(float)storeService.getStores().size(),
                null),HttpStatus.OK);

    }

    @GetMapping(path="/averagesalesbystore")
    public ResponseEntity<ResponseDTO> AverageSalesByStore(){
        return new ResponseEntity<>(new ResponseDTO(200,
                saleService.getTotalSales()/(float)sellerService.getSellers().size(),
                null),HttpStatus.OK);

    }

    @GetMapping(path="/storesabove/{amount}")
    public ResponseEntity<ResponseDTO> getStoresAboveSaleAmount(@PathVariable int amount){
        return new ResponseEntity<>(new ResponseDTO(200,
                saleService.getStoresAboveSaleAmount(amount),
                null),HttpStatus.OK);

    }

    @PostMapping
    @GetMapping(path="/createsale")
    public ResponseEntity<ResponseDTO> createSale(@RequestBody SaleDTO saleDTO){
        Seller findSeller = sellerService.getSellerById(saleDTO.getSellerId());
        if( findSeller == null){
            return new ResponseEntity<>(new ResponseDTO(409,
                    "El vendedor ingresado no existe",null),
                    HttpStatus.BAD_REQUEST);
        }
        Store findStore = storeService.getStoreById(saleDTO.getStoreId());
        if( findStore == null){
            return new ResponseEntity<>(new ResponseDTO(409,
                    "La tienda ingresada no existe",null),
                    HttpStatus.BAD_REQUEST);
        }
        saleService.addSale(new Sale(findStore,findSeller,
                saleDTO.getQuantity()));
        return new ResponseEntity<>(new ResponseDTO(200,
                "Venta adicionada",null),
                HttpStatus.OK);

    }

    @GetMapping(path="/bestseller")
    public ResponseEntity<ResponseDTO> BestSeller(){

        if (saleService.BestSeller(sellerService.getSellers())==null) {

            return new ResponseEntity<>(new ResponseDTO(409,
                    "La lista de ventas está vacía",null),
                    HttpStatus.BAD_REQUEST);


        } else {
            return new ResponseEntity<>(new ResponseDTO(200,
                    saleService.BestSeller(sellerService.getSellers()), null),
                    HttpStatus.OK);
        }

    }

    @GetMapping(path="/beststore")
    public ResponseEntity<ResponseDTO> BestStore(){

        if (saleService.BestStore()==null) {

            return new ResponseEntity<>(new ResponseDTO(409,
                    "La lista de ventas está vacía",null),
                    HttpStatus.BAD_REQUEST);


        } else {
            return new ResponseEntity<>(new ResponseDTO(200,
                    saleService.BestStore(), null),
                    HttpStatus.OK);
        }

    }

    @GetMapping(path="/totalstore/{code}")
    public ResponseEntity<ResponseDTO> GetTotalSalesByStore(@PathVariable String code){

            return new ResponseEntity<>(new ResponseDTO(200,
                    saleService.GetTotalSalesByStore(code), null),
                    HttpStatus.OK);
        }

}
