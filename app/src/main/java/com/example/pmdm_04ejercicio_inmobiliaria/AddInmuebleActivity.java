package com.example.pmdm_04ejercicio_inmobiliaria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.pmdm_04ejercicio_inmobiliaria.configuraciones.Constantes;
import com.example.pmdm_04ejercicio_inmobiliaria.databinding.ActivityAddInmuebleBinding;
import com.example.pmdm_04ejercicio_inmobiliaria.modelos.Inmobiliaria;

public class AddInmuebleActivity extends AppCompatActivity {

    private ActivityAddInmuebleBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddInmuebleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Inmobiliaria inmueble = crearInmueble();
                if (inmueble != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.INMUEBLE, inmueble);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }else {
                    Toast.makeText(AddInmuebleActivity.this, "Faltan datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Inmobiliaria crearInmueble() {
        if (
                binding.txtCiudadAddInmueble.getText().toString().isEmpty() ||
                        binding.txtCPAddInmueble.getText().toString().isEmpty() ||
                        binding.txtProvinciaAddInmueble.getText().toString().isEmpty() ||
                        binding.txtDireccionAddInmueble.getText().toString().isEmpty() ||
                        binding.txtNumeroAddInmueble.getText().toString().isEmpty()
        )


        return null;

        return new Inmobiliaria(
                binding.txtDireccionAddInmueble.getText().toString(),
                Integer.parseInt(binding.txtNumeroAddInmueble.getText().toString()),
                binding.txtCPAddInmueble.getText().toString(),
                binding.txtCiudadAddInmueble.getText().toString(),
                binding.txtProvinciaAddInmueble.getText().toString(),
                binding.rbAddInmueble.getRating()
        );
    }
}