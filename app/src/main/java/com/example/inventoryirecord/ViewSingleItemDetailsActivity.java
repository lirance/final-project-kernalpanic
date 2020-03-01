package com.example.inventoryirecord;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.inventoryirecord.data.InventoryEditForm;
import com.example.inventoryirecord.data.InventoryItem;

import java.util.HashMap;
import java.util.Objects;

import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.DATE_PURCH;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.ITEM_NAME;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.ITEM_TYPE;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.MAKE;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.MODEL;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.NEW;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.PRICE_PAID;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.SERIAL_NUMBER;
import static com.example.inventoryirecord.ViewSingleItemDetailsActivity.TextViewKeys.VALUE;

public class ViewSingleItemDetailsActivity extends AppCompatActivity {
    public static final String INVENTORY_ITEM = "singleInventoryItem";
    public static final String TAG = ViewSingleItemDetailsActivity.class.getSimpleName();

    private HashMap<TextViewKeys, TextView> textViewHashMap;
    private HashMap<TextViewKeys, TextView> editTextViewHashMap;
    private LinearLayout editButton;
    private LinearLayout parentSaveCancelButtonsLayout;
    private LinearLayout itemDetailsLayout;
    private LinearLayout editItemDetailsLayout;

    InventoryEditForm inventoryEditForm;

    private CheckBox newCheckBox;

    private InventoryItem inventoryItem;
    private InventoryViewModel inventoryViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_single_item_details_activity);


        Intent intent = getIntent();
        if(Objects.nonNull(intent) && intent.hasExtra(INVENTORY_ITEM)) {
            Log.d(TAG, "has extra");
            initAllViewHash();
            inventoryItem = (InventoryItem) intent.getSerializableExtra(INVENTORY_ITEM);

            setViewText(textViewHashMap, Objects.requireNonNull(inventoryItem), false);


        }
        editButton = findViewById(R.id.edit_single_item_button);
        parentSaveCancelButtonsLayout = findViewById(R.id.save_cancel_button_layout);
        LinearLayout saveButton = findViewById(R.id.save_edits_single_item_button);
        LinearLayout cancelButton = findViewById(R.id.cancel_edits_single_item_button);
        itemDetailsLayout = findViewById(R.id.view_single_item_details_layout);
        editItemDetailsLayout = findViewById(R.id.edit_single_item_details_layout);

        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);
        inventoryEditForm = new InventoryEditForm(this);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEditForm();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveForm();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancelForm();
            }
        });
    }

    private void handleEditForm() {
        editButton.setVisibility(View.INVISIBLE);
        parentSaveCancelButtonsLayout.setVisibility(View.VISIBLE);
        editItemDetailsLayout.setVisibility(View.VISIBLE);
        itemDetailsLayout.setVisibility(View.INVISIBLE);
        setViewText(inventoryEditForm.getEditTextViewHashMap(), Objects.requireNonNull(inventoryItem), true);
    }

    private void handleSaveForm() {
        editButton.setVisibility(View.VISIBLE);
        parentSaveCancelButtonsLayout.setVisibility(View.INVISIBLE);
        inventoryItem = inventoryEditForm.getEditText();
        setViewText(textViewHashMap, Objects.requireNonNull(inventoryItem), false);
        editItemDetailsLayout.setVisibility(View.INVISIBLE);
        itemDetailsLayout.setVisibility(View.VISIBLE);
        inventoryViewModel.updateSingleInventoryItem(inventoryItem);
    }
    private void handleCancelForm() {
        editButton.setVisibility(View.VISIBLE);
        parentSaveCancelButtonsLayout.setVisibility(View.INVISIBLE);

        editItemDetailsLayout.setVisibility(View.INVISIBLE);
        itemDetailsLayout.setVisibility(View.VISIBLE);
    }

    private void initAllViewHash() {
        textViewHashMap = new HashMap<>();
        textViewHashMap.put(ITEM_NAME, (TextView) findViewById(ITEM_NAME.viewId));
        textViewHashMap.put(ITEM_TYPE, (TextView) findViewById(ITEM_TYPE.viewId));
        textViewHashMap.put(MAKE, (TextView) findViewById(MAKE.viewId));
        textViewHashMap.put(MODEL, (TextView) findViewById(MODEL.viewId));
        textViewHashMap.put(SERIAL_NUMBER, (TextView) findViewById(SERIAL_NUMBER.viewId));
        textViewHashMap.put(VALUE, (TextView) findViewById(VALUE.viewId));
        textViewHashMap.put(DATE_PURCH, (TextView) findViewById(DATE_PURCH.viewId));
        textViewHashMap.put(PRICE_PAID, (TextView) findViewById(PRICE_PAID.viewId));
        textViewHashMap.put(NEW, (TextView) findViewById(NEW.viewId));

        newCheckBox = findViewById(NEW.editId);

        editTextViewHashMap = new HashMap<>();
        editTextViewHashMap.put(ITEM_NAME, (EditText) findViewById(ITEM_NAME.editId));
        editTextViewHashMap.put(ITEM_TYPE, (EditText) findViewById(ITEM_TYPE.editId));
        editTextViewHashMap.put(MAKE, (EditText) findViewById(MAKE.editId));
        editTextViewHashMap.put(MODEL, (EditText) findViewById(MODEL.editId));
        editTextViewHashMap.put(SERIAL_NUMBER, (EditText) findViewById(SERIAL_NUMBER.editId));
        editTextViewHashMap.put(VALUE, (EditText) findViewById(VALUE.editId));
        editTextViewHashMap.put(DATE_PURCH, (EditText) findViewById(DATE_PURCH.editId));
        editTextViewHashMap.put(PRICE_PAID, (EditText) findViewById(PRICE_PAID.editId));

    }
    private void setViewText(HashMap<TextViewKeys, TextView> hashMap, InventoryItem item, boolean edit) {
        Objects.requireNonNull(hashMap.get(ITEM_NAME)).setText(item.itemName);
        Objects.requireNonNull(hashMap.get(ITEM_TYPE)).setText(item.itemType);
        Objects.requireNonNull(hashMap.get(MAKE)).setText(item.make);
        Objects.requireNonNull(hashMap.get(MODEL)).setText(item.model);
        Objects.requireNonNull(hashMap.get(SERIAL_NUMBER)).setText(item.serialNumber);
        Objects.requireNonNull(hashMap.get(VALUE)).setText(String.valueOf(item.value));
        Objects.requireNonNull(hashMap.get(DATE_PURCH)).setText(String.valueOf(item.datePurchased));
        Objects.requireNonNull(hashMap.get(PRICE_PAID)).setText(String.valueOf(item.pricePaid));
        if (edit) {
            newCheckBox.setChecked(item.newItem);
        } else {
            Objects.requireNonNull(hashMap.get(NEW)).setText(Boolean.toString(item.newItem));
        }
    }

    private void getEditText(HashMap<TextViewKeys, TextView> hashMap, InventoryItem item) {
        item.itemName = Objects.requireNonNull(hashMap.get(ITEM_NAME)).getText().toString();
        item.itemType = Objects.requireNonNull(hashMap.get(ITEM_TYPE)).getText().toString();
        item.make = Objects.requireNonNull(hashMap.get(MAKE)).getText().toString();
        item.model = Objects.requireNonNull(hashMap.get(MODEL)).getText().toString();
        item.serialNumber = Objects.requireNonNull(hashMap.get(SERIAL_NUMBER)).getText().toString();
        item.value = Double.parseDouble(Objects.requireNonNull(hashMap.get(VALUE)).getText().toString());
        item.datePurchased = Objects.requireNonNull(hashMap.get(DATE_PURCH)).getText().toString();
        item.pricePaid = Double.parseDouble(Objects.requireNonNull(hashMap.get(PRICE_PAID)).getText().toString());
        item.newItem = newCheckBox.isChecked();
    }

    public enum TextViewKeys {
        ITEM_NAME(R.id.single_item_name_text_view, R.id.edit_single_item_name_text_view),
        ITEM_TYPE(R.id.single_item_type_text_view, R.id.edit_single_item_type_text_view),
        MAKE(R.id.single_item_make_text_view, R.id.edit_single_item_make_text_view),
        MODEL(R.id.single_item_model_text_view, R.id.edit_single_item_model_text_view),
        SERIAL_NUMBER(R.id.single_item_serial_text_view, R.id.edit_single_item_serial_text_view),
        VALUE(R.id.single_item_value_text_view, R.id.edit_single_item_value_text_view),
        DATE_PURCH(R.id.single_item_date_purch_text_view, R.id.edit_single_item_date_purch_text_view),
        DATE_MANU(1,1),
        DATE_ADD(1,1),
        NOTES(1,1),
        PRICE_PAID(R.id.single_item_price_text_view, R.id.edit_single_item_price_text_view),
        NEW(R.id.single_item_new_text_view, R.id.edit_single_item_new_check_box);

        public int viewId;
        public int editId;

        TextViewKeys(int viewId, int editId) {
            this.viewId = viewId;
            this.editId = editId;
        }
    }

}
