package co.edu.umanizales.manage_store.service;

import co.edu.umanizales.manage_store.model.Store;
import co.edu.umanizales.manage_store.model.Seller;
import lombok.Getter;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Getter

public class StoreService {
    private List<Store>stores;

    public StoreService() {
        stores = new ArrayList<>();
        stores.add(new Store("1","Dorada"));
        stores.add(new Store("2","Manizales"));
        stores.add(new Store("3","Bogota"));
    }

    public void addStore(Store store) {
        stores.add(store);
    }

    public Store getStoreById(String code)
    {
        for(Store store:stores)
        {
            if(store.getCode().equalsIgnoreCase(code))
            {
                return store;
            }
        }
        return null;
    }


}
