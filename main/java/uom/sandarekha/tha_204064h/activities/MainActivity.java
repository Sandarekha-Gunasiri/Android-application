package uom.sandarekha.tha_204064h.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import uom.sandarekha.tha_204064h.R;
import uom.sandarekha.tha_204064h.adapters.ItemAdapter;
import uom.sandarekha.tha_204064h.data.entity.ItemInfo;
import uom.sandarekha.tha_204064h.data.viewModels.ItemInfoViewModel;


public class MainActivity extends AppCompatActivity {

    RecyclerView rv_itemList;
    ItemAdapter itemAdapter;
    FloatingActionButton floatingActionButton;
    ItemInfoViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        floatingActionButton = findViewById(R.id.fab_add_todo_item);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ItemInfoForm.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent, 1);
            }
        });

        rv_itemList = findViewById(R.id.rv_item_list);
        rv_itemList.setLayoutManager(new LinearLayoutManager(this));

        itemAdapter = new ItemAdapter(MainActivity.this);
        rv_itemList.setAdapter(itemAdapter);

        viewModel = new ViewModelProvider(this).get(ItemInfoViewModel.class);


        viewModel.getAllItems().observe(this, itemInfos -> {
                    itemAdapter.submitList(itemInfos);
                }
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent i = data;
        if (i != null) {
            if (resultCode == 1) {
                ItemInfo item = new ItemInfo();
                item.name = i.getExtras().getString("Title").toString();
                item.description = i.getExtras().getString("Description").toString();
                item.price = i.getExtras().getDouble("Price");
                viewModel.insert(item);
                rv_itemList.smoothScrollToPosition(viewModel.getAllItems().getValue().size());
            } else if (resultCode == 2) {
                ItemInfo item = new ItemInfo();
                item.setItemId(i.getExtras().getInt("ID"));
                item.setName(i.getExtras().getString("Title").toString());
                item.setDescription(i.getExtras().getString("Description").toString());
                item.setPrice(i.getExtras().getDouble("Price"));
                viewModel.update(item);
            } else {
                viewModel.delete(itemAdapter.getItemAt(i.getExtras().getInt("Position")));
            }
        }
    }

}