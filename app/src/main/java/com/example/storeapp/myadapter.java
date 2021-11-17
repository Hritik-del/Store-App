package com.example.storeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class myadapter extends FirebaseRecyclerAdapter<model, myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final model model)
    {
       holder.store.setText(model.getShop());
        Log.e("Name Hritik :", "" + model.getShop());
                       holder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final DialogPlus dialogPlus= DialogPlus.newDialog(holder.store.getContext())
                                    .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                    .setExpanded(true,1100)
                                    .create();

                            View myview=dialogPlus.getHolderView();
                            final EditText purl=myview.findViewById(R.id.uimgurl);
                            final EditText name=myview.findViewById(R.id.uname);
                            final EditText course=myview.findViewById(R.id.ucourse);
                            final EditText email=myview.findViewById(R.id.uemail);
                            Button submit=myview.findViewById(R.id.usubmit);

                            purl.setText(model.getShop());
                            name.setText(model.getPhone());
                            course.setText(model.getAccount());
                            email.setText(model.getIfsc());

                            dialogPlus.show();

                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        Map<String,Object> map=new HashMap<>();
                                        map.put("shop",purl.getText().toString());
                                        map.put("phone",name.getText().toString());
                                        map.put("account",email.getText().toString());
                                        map.put("ifsc",course.getText().toString());

                                        FirebaseDatabase.getInstance().getReference().child("Shop Entry")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(holder.store.getContext(), "Succesfully Updated", Toast.LENGTH_SHORT).show();
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                });


                        }
                    });



    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_view,parent,false);
       return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView edit;
        TextView store;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            edit= itemView.findViewById(R.id.editIcon);
            store = itemView.findViewById(R.id.groupListDetailName);
        }
    }
}
