package com.example.zhanahope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_list_item_2;



public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    private boolean isPulled = false;
    String selectedChildHouseName = "Еда";
    private static final String TAG = "MapActivity";

    private GoogleMap gMap;
    private final LatLng[] locations = {
            new LatLng(43.22577550821795, 76.96537716034884),
            new LatLng(43.20083183186626, 76.91344829658048),
            new LatLng(43.23488003123818, 76.96253204517997),
            new LatLng(43.24934121559502, 76.96715018763788),
            new LatLng(43.32541804496699, 76.96644832488741),
            new LatLng(43.21409488367408, 76.77449429028813),
            new LatLng(43.35676981750153, 76.99007534846484)
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //карта init
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        Spinner spinnerCategories = findViewById(R.id.spinner_categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categories_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);


        ListView list = findViewById(R.id.list);
        EditText filter = findViewById(R.id.searchFilter);
        ListView categoriesList = findViewById(R.id.categories);
        ListView childHousesCategory = findViewById(R.id.childHousesCategory);
        TextView constContactText = findViewById(R.id.houseContactInfoHeader);
        TextView categoriesHeader = findViewById(R.id.categoriesHeader);
        Button onMainPage = findViewById(R.id.onMainPage);
        Button categoryBackButton = findViewById(R.id.categoryBackButton);

        //info layout
        TextView houseName = findViewById(R.id.houseName);
        TextView houseAdres = findViewById(R.id.houseAdres);
        TextView houseNideeness = findViewById(R.id.houseNideeness);
        TextView housePhoneNumber = findViewById(R.id.housePhoneNumber);
        TextView houseEmail = findViewById(R.id.houseEmail);
        HorizontalScrollView houseImages = findViewById(R.id.houseImages);

        boolean userOnMainPage = true;
        categoryBackButton.setVisibility(View.GONE);
        ArrayList<String> categoriesArray = new ArrayList<>();

        //initialization of child houses
        ArrayList <String> houses = new ArrayList<>();
        ChildHouse childHouse1 = new ChildHouse("ГУ Городской дом ребенка №2", "Проспект Достык 107/5", "38 38", "383829392939329", "Детдом", "Гаджеты", "+7707896453", "email отсутвует");
        ChildHouse childHouse2 = new ChildHouse("Дом надежды", "Тойшыбек батыра 193", "38 38", "383829392939329", "Детдом", "Одежда", "+7707896453", "email отсутвует");
        ChildHouse childHouse3 = new ChildHouse("ГУ  комплекс Жанұя", "Шыгыс 2, 26", "38 38", "383829392939329", "Детдом", "Гаджеты", "+7707896453", "email отсутвует");
        ChildHouse childHouse4 = new ChildHouse("ГУ Центр Жастаруйi", " Жетысу-2, 79", "38 38", "383829392939329", "Детдом", "Мама", "+7707896453", "email отсутвует");
        ChildHouse childHouse5 = new ChildHouse("ГУ Центр поддержки детей г. Алматы", "Дунентаева, 32/1", "38 38", "383829392939329", "Детдом", "Папа", "+7707896453", "email отсутвует");
        ChildHouse childHouse6 = new ChildHouse("Каусар", "Бакинская, 22", "38 38", "383829392939329", "Детдом", "Жрачка", "+7707896453", "email отсутвует");
        ChildHouse childHouse7 = new ChildHouse("Ана аманаты (интернат для девочек)", "Коккайнар, 16", "38 38", "383829392939329", "Детдом", "Гаджеты","+7707896453", "email отсутвует");
        ChildHouse childHouse8 = new ChildHouse("Перзент", "Алтын Орда, 6а", "38 38", "383829392939329", "Детдом", "Игрушки", "+7707896453", "email отсутвует");

        ImageView newPhoto = new ImageView(this);

        ChildHouse childHouseEXAMPLE = new ChildHouse("", "", "", "", "", "", "", "");
        //устанавливаем категории для главной страницы карты
        categoriesArray.add("Еда") ;
        categoriesArray.add("Одежда");
        categoriesArray.add("Гаджеты");
        categoriesArray.add("Мама");
        categoriesArray.add("Папа");

        // Создаем ArrayAdapter для связывания данных с ListView
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, simple_list_item_1, categoriesArray);

        // Устанавливаем адаптер для ListView
        categoriesList.setAdapter(adapter1);

        //Умный поиск по названиям детдомов


        ArrayList<ChildHouse> childHouses = new ArrayList<>();
        childHouses.add(childHouse1);
        childHouses.add(childHouse2);
        childHouses.add(childHouse3);
        childHouses.add(childHouse4);
        childHouses.add(childHouse5);
        childHouses.add(childHouse6);
        childHouses.add(childHouse7);
        childHouses.add(childHouse8);

        houses.add(childHouse1.name);
        houses.add(childHouse2.name);
        houses.add(childHouse3.name);
        houses.add(childHouse4.name);
        houses.add(childHouse5.name);
        houses.add(childHouse6.name);
        houses.add(childHouse7.name);
        houses.add(childHouse8.name);

        ArrayAdapter<String> adapter228 = new ArrayAdapter<>(this, simple_list_item_1, houses);
        list.setAdapter(adapter228);
        list.setVisibility(View.GONE);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.setVisibility(View.VISIBLE);
            }
        });

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                adapter.getFilter().filter(s.toString());
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                Log.d("Selected Item", selectedItem);
                list.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(filter.getWindowToken(), 0);
                categoriesList.setVisibility(View.GONE);
                categoriesHeader.setVisibility(View.GONE);
                constContactText.setVisibility(View.VISIBLE);
                houseName.setText(selectedItem);
                filter.setHint(selectedItem);
                for(ChildHouse childHouseCycle : childHouses){
                    if(selectedItem.equals(childHouseCycle)){
                        childHouseCycle.showInfoLayout(houseAdres, houseNideeness, housePhoneNumber, houseEmail);
                    }
                }
            }
        });

        onMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                houseName.setText("");
                houseAdres.setText("");
                houseEmail.setText("");
                houseNideeness.setText("");
                housePhoneNumber.setText("");
                constContactText.setText("");
                filter.setHint("Поиск...");
                childHousesCategory.setVisibility(View.GONE);
                categoriesHeader.setVisibility(View.VISIBLE);
                categoriesList.setVisibility(View.VISIBLE);
                categoriesHeader.setText("Категории по нуждам");
            }
        });

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                categoriesList.setVisibility(View.GONE);
                List <String> childHousesNamesAtCategory = new ArrayList<>();
                childHousesCategory.setVisibility(View.VISIBLE);
                categoriesHeader.setText(selectedItem);
                filter.setHint("Поиск..");
                if(selectedItem.equals("Одежда")){
                    childHousesNamesAtCategory.add(childHouse1.name);
                    childHousesNamesAtCategory.add(childHouse3.name);
                    childHousesNamesAtCategory.add(childHouse5.name);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(MapActivity.this, android.R.layout.simple_list_item_1, childHousesNamesAtCategory);
                    childHousesCategory.setAdapter(adapter3);
                    childHousesCategory.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) parent.getItemAtPosition(position);
                            houseAdres.setText(selectedItem);
                            houseName.setText(selectedItem);
                            houseName.setVisibility(View.VISIBLE);
                            childHousesCategory.setVisibility(View.GONE);
                            categoriesHeader.setVisibility(View.GONE);
                            for(ChildHouse childHouseCycle : childHouses){
                                if(childHouseCycle.name.equals(selectedItem)){
                                    childHouseCycle.showInfoLayout(houseAdres, houseNideeness, housePhoneNumber, houseEmail);
                                }
                            }
                        }

                    });
                }
                else if(selectedItem.equals("Гаджеты")){
                    childHousesNamesAtCategory.add(childHouse2.name);
                    childHousesNamesAtCategory.add(childHouse4.name);
                    childHousesNamesAtCategory.add(childHouse6.name);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(MapActivity.this, android.R.layout.simple_list_item_1, childHousesNamesAtCategory);
                    childHousesCategory.setAdapter(adapter3);
                    childHousesCategory.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) parent.getItemAtPosition(position);
                            houseName.setText(selectedItem);
                            houseName.setVisibility(View.VISIBLE);
                            childHousesCategory.setVisibility(View.GONE);
                            categoriesHeader.setVisibility(View.GONE);
                            for(ChildHouse childHouseCycle : childHouses){
                                if(childHouseCycle.name.equals(selectedItem)){
                                    childHouseCycle.showInfoLayout(houseAdres, houseNideeness, housePhoneNumber, houseEmail);
                                }
                            }
                        }

                    });
                }
                else if(selectedItem.equals("Мама")){
                    childHousesNamesAtCategory.add(childHouse7.name);
                    childHousesNamesAtCategory.add(childHouse8.name);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(MapActivity.this, android.R.layout.simple_list_item_1, childHousesNamesAtCategory);
                    childHousesCategory.setAdapter(adapter3);
                    childHousesCategory.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) parent.getItemAtPosition(position);
                            houseName.setText(selectedItem);
                            houseName.setVisibility(View.VISIBLE);
                            childHousesCategory.setVisibility(View.GONE);
                            categoriesHeader.setVisibility(View.GONE);
                            for(ChildHouse childHouseCycle : childHouses){
                                if(childHouseCycle.name.equals(selectedItem)){
                                    childHouseCycle.showInfoLayout(houseAdres, houseNideeness, housePhoneNumber, houseEmail);
                                }
                            }
                        }

                    });
                }
                else{
                    childHousesNamesAtCategory.add(childHouse2.name);
                    childHousesNamesAtCategory.add(childHouse6.name);
                    ArrayAdapter<String> adapter3 = new ArrayAdapter<>(MapActivity.this, android.R.layout.simple_list_item_1, childHousesNamesAtCategory);
                    childHousesCategory.setAdapter(adapter3);
                    childHousesCategory.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String selectedItem = (String) parent.getItemAtPosition(position);
                            houseName.setText(selectedItem);
                            houseName.setVisibility(View.VISIBLE);
                            childHousesCategory.setVisibility(View.GONE);
                            categoriesHeader.setVisibility(View.GONE);
                            for(ChildHouse childHouseCycle : childHouses){
                                if(childHouseCycle.name.equals(selectedItem)){childHouseCycle.showInfoLayout(houseAdres, houseNideeness, housePhoneNumber, houseEmail);}
                            }
                        }
                    });
                }
            }
        });

        //pull the PullBar action
        RelativeLayout pullBar = findViewById(R.id.pullBar);
        RelativeLayout infoLayout = findViewById(R.id.infoLayout);

        pullBar.setOnTouchListener(new View.OnTouchListener() {
            private float startY;
            private float lastY;
            private int maxMargin = 1565; // Максимальное значение отступа вниз

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getRawY();
                        lastY = event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float currentY = event.getRawY();
                        float deltaY = currentY - lastY;
                        lastY = currentY;
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) infoLayout.getLayoutParams();
                        params.topMargin += deltaY;
                        // Проверяем, чтобы отступ не превышал максимальное значение
                        if (params.topMargin > maxMargin) {
                            params.topMargin = maxMargin;
                        }
                        infoLayout.setLayoutParams(params);
                        break;
                    case MotionEvent.ACTION_UP:
                        float endY = event.getRawY();
                        if (endY - startY > 100) {
                            // Пользователь тянул вниз на достаточно большое расстояние, считаем это действием "открытия" макета
                            isPulled = true;
                        } else {
                            // Возвращаем макет на место
                            isPulled = false;
                            RelativeLayout.LayoutParams paramsUp = (RelativeLayout.LayoutParams) infoLayout.getLayoutParams();
                            paramsUp.topMargin = 1000; // Начальное положение макета, подставь свое значение
                            infoLayout.setLayoutParams(paramsUp);
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gMap = googleMap;

        for (LatLng location : locations) {
            googleMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Детский дом")
                    .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("greenhouse", 150, 95)))
                    .anchor(0.5f, 1.0f)
                    .flat(true)
                    .visible(true)
                    .alpha(1.0f)
                    .zIndex(0.0f)
                    .draggable(false)
                    .snippet(null)
                    .title(null)
            );
        }

        if (locations.length > 0) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locations[0], 13));
        }
    }
    private Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.greenhouse);
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false);
    }

}