package uom.sandarekha.tha_204064h.data.repositories;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import uom.sandarekha.tha_204064h.data.THA_DB;
import uom.sandarekha.tha_204064h.data.dao.ItemInfoDao;
import uom.sandarekha.tha_204064h.data.entity.ItemInfo;

public class ItemInfoRepo {
    // below line is the create a variable
    private ItemInfoDao itemInfoDao;
    private LiveData<List<ItemInfo>> allItems;

    // creating a constructor for our variables
    // and passing the variables to it.
    public ItemInfoRepo(Application application) {
        THA_DB database = THA_DB.getInstance(application);
        itemInfoDao = database.Dao();
        allItems = itemInfoDao.getAllItems();
    }

    // creating a method to insert the data to our database.
    public void insert(ItemInfo model) {

        new InsertItem(itemInfoDao).execute(model);
    }

    public void update(ItemInfo itemInfo) {
        new UpdateItemInfo(itemInfoDao).execute(itemInfo);
    }

    public void delete(ItemInfo itemInfo) {
        new DeleteItem(itemInfoDao).execute(itemInfo);
    }

    public LiveData<List<ItemInfo>> getAllItems() {
        return allItems;
    }

    private static class InsertItem extends AsyncTask<ItemInfo, Void, Void> {
        private ItemInfoDao itemInfoDao;

        private InsertItem(ItemInfoDao itemInfoDao) {
            this.itemInfoDao = itemInfoDao;
        }


        @Override
        protected Void doInBackground(ItemInfo... itemInfos) {
            itemInfoDao.addItem(itemInfos[0]);
            return null;
        }
    }

    private static class UpdateItemInfo extends AsyncTask<ItemInfo, Void, Void> {
        private ItemInfoDao dao;

        private UpdateItemInfo(ItemInfoDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ItemInfo... itemInfos) {
            //below line is use to update our modal in dao.
            dao.updateItem(itemInfos[0]);
            return null;
        }
    }

    private static class DeleteItem extends AsyncTask<ItemInfo, Void, Void> {
        private ItemInfoDao dao;

        private DeleteItem(ItemInfoDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ItemInfo... itemInfos) {
            dao.delete(itemInfos[0]);
            return null;
        }
    }


}
