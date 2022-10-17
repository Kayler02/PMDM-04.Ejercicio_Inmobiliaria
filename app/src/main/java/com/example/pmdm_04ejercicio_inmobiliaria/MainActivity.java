package com.example.pmdm_04ejercicio_inmobiliaria;

import android.content.Intent;
import android.os.Bundle;

import com.example.pmdm_04ejercicio_inmobiliaria.configuraciones.Constantes;
import com.example.pmdm_04ejercicio_inmobiliaria.modelos.Inmobiliaria;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pmdm_04ejercicio_inmobiliaria.databinding.ActivityMainBinding;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Inmobiliaria> inmueblesList;
    private ActivityResultLauncher<Intent> addInmuebleLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inmueblesList = new ArrayList<>();
        inicializaLaunchers();

        setSupportActionBar(binding.toolbar);



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addInmuebleLauncher.launch(new Intent(MainActivity.this, AddInmuebleActivity.class));
            }
        });
    }

    private void inicializaLaunchers() {
        addInmuebleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK){
                            if (result.getData() != null){
                                if (result.getData().getExtras() != null){
                                    Inmobiliaria inmueble = (Inmobiliaria) result.getData().getExtras().getSerializable(Constantes.INMUEBLE);
                                    if (inmueble != null){
                                        inmueblesList.add(inmueble);
                                        mostrarInmuebles();
                                    }else{
                                        Toast.makeText(MainActivity.this, "No hay Inmueble en el Bundle", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this, "No hay Bundle en el Intent", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "No tiene Intent", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Ventana cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void mostrarInmuebles() {
        // ¿?
        binding.contentMain.contenedor.removeAllViews();

        for (int i = 0; i < inmueblesList.size(); i++) {
            Inmobiliaria inmueble = inmueblesList.get(i);

            View inmuebleView = LayoutInflater.from(MainActivity.this).inflate(R.layout.inmueble_model_view, null);
            TextView lblDireccion = inmuebleView.findViewById(R.id.lblDireccionInmuebleModel);
            TextView lblNumero = inmuebleView.findViewById(R.id.lblNumeroInmuebleModel);
            TextView lblProvincia = inmuebleView.findViewById(R.id.lblProvinciaInmuebleModel);
            TextView lblCiudad = inmuebleView.findViewById(R.id.lblCiudadInmuebleModel);
            RatingBar rbValoracion = inmuebleView.findViewById(R.id.rbValoracionInmuebleModel);

            lblDireccion.setText(inmueble.getDireccion());
            lblCiudad.setText(inmueble.getCiudad());
            lblProvincia.setText(inmueble.getProvincia());
            lblNumero.setText(String.valueOf(inmueble.getNumero()));
            rbValoracion.setRating(inmueble.getValoracion());

            binding.contentMain.contenedor.addView(inmuebleView);
        }
    }
}