package com.example.storeapp;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Add_Details extends AppCompatActivity {

    EditText shopName;
    EditText phoneNumber;
    EditText accountNumber;
    EditText ifsc;
    Spinner spinner;
    Button save, image_front, image_left, image_right, image_pan;

    private  Uri imageUri;

    private static  final int IMAGE_REQUEST = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_details);



        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Select type of business", "Agro Products", "Automobile Repair", "Automobile Accessories", "Bicycle"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        //Now saving the text items to the firebase
        shopName = findViewById(R.id.shop_name);
        phoneNumber = findViewById(R.id.phone_number);
        accountNumber = findViewById(R.id.account_number);
        ifsc = findViewById(R.id.ifsc_code);
        save = findViewById(R.id.save);
        image_front = findViewById(R.id.button1);
        image_left = findViewById(R.id.button2);
        image_right = findViewById(R.id.button3);
        image_pan = findViewById(R.id.button4);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the text from the view
                String shop = shopName.getText().toString();
                String phone = phoneNumber.getText().toString();
                String account = accountNumber.getText().toString();
                String ifscnum = ifsc.getText().toString();
                if(shop.isEmpty())
                {
                    Toast.makeText(Add_Details.this, "Please enter shop name", Toast.LENGTH_SHORT).show();
                }
                else if (phone.isEmpty())
                {
                    Toast.makeText(Add_Details.this, "Please enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if (account.isEmpty())
                {
                    Toast.makeText(Add_Details.this, "Please enter account number", Toast.LENGTH_SHORT).show();
                }
                else if (ifscnum.isEmpty())
                {
                    Toast.makeText(Add_Details.this, "Please enter ifsc code", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("shop", shop);
                    map.put("phone", phone);
                    map.put("account", account);
                    map.put("ifsc", ifscnum);
                    FirebaseDatabase.getInstance().getReference().child("Shop Entry").push().setValue(map);//push will give unique id to the each children of "Shop Entry"
                    Toast.makeText(Add_Details.this, "Whoa!! Saved Succesfully", Toast.LENGTH_SHORT).show();
                }
            }
        });

        image_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        image_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        image_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

        image_pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();

            uploadImage();
        }
    }

    private  String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if(imageUri != null){
            final StorageReference fileRef = FirebaseStorage.getInstance().getReference().child("uploads").child(System.currentTimeMillis()+"."+ getFileExtension(imageUri));

            fileRef.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            Log.d("DownloadUri", url);
                            pd.dismiss();
                            Toast.makeText(Add_Details.this, "Image upload successful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
