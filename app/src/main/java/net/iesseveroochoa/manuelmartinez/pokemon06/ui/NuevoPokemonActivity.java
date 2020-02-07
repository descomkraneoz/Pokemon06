package net.iesseveroochoa.manuelmartinez.pokemon06.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;


import net.iesseveroochoa.manuelmartinez.pokemon06.R;
import net.iesseveroochoa.manuelmartinez.pokemon06.adapters.PokemonApiAdapter;
import net.iesseveroochoa.manuelmartinez.pokemon06.model.Pokemon;
import net.iesseveroochoa.manuelmartinez.pokemon06.utils.Utils;
import net.iesseveroochoa.manuelmartinez.pokemon06.viewmodels.PokemonApiViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static net.iesseveroochoa.manuelmartinez.pokemon06.utils.Utils.definirFormatoReciclerView;

public class NuevoPokemonActivity extends AppCompatActivity {
    public static final String EXTRA_POKEMON = "net.iessochoa.manuelmartinez.practica6.ui.extrapokemon";

    private PokemonApiViewModel pokemonViewModel;
    private RecyclerView rvListaPokemon;
    private PokemonApiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pokemon);
        //flecha en el menu para volver atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Nuevo Pokemon");

        //RECYCLER_VIEW
        rvListaPokemon = findViewById(R.id.rvListaPokemon);
        //creamos el adaptador
        adapter = new PokemonApiAdapter();
        rvListaPokemon.setAdapter(adapter);
        definirFormatoReciclerView(this, rvListaPokemon);
        //definirEventoSwiper();

        //VIEW_MODEL
        //Recuperamos el ViewModel
        pokemonViewModel = ViewModelProviders.of(this).get(PokemonApiViewModel.class);
        //Este livedata nos permite ver todos los contactos y en caso de que haya un
        //cambio en la base de datos, se mostrará automáticamente
        pokemonViewModel.getListaPokemonApi().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                //actualizamos el RecyclerView con la nueva lista
                adapter.setListaPokemon(pokemons);
            }
        });

        //Accion de comprar
        adapter.setOnItemComprarClickListener(new PokemonApiAdapter.onItemClickComprarListener() {
            @Override
            public void onItemComprarClick(Pokemon pokemon) {
                dialogoNuevoPokemon(pokemon);
                Intent nuevoPokemon=new Intent();
                nuevoPokemon.putExtra(EXTRA_POKEMON, new Pokemon(pokemon.getId(),pokemon.getNombre(),pokemon.getFechaCompra()));
                setResult(RESULT_OK, nuevoPokemon);
                finish();
            }
        });
    }

    public void dialogoNuevoPokemon(final Pokemon p){
        AlertDialog.Builder dialogo=new AlertDialog.Builder(this);
        dialogo.setTitle(getResources().getString(R.string.tituloNuevoPokemon));
        dialogo.setMessage(getResources().getString(R.string.cuerpoNuevoPokemon) +" "+p.getNombre() );
        dialogo.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               pokemonViewModel.insert(p);
                onRestart();
            }
        });
        dialogo.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onRestart();
            }
        });
        //Mostramos el dialogo
        dialogo.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_prueba_pokemon:
                cargaPokemonEjemplo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargaPokemonEjemplo(){
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Pokemon pokemon;
        try {
            pokemon=new Pokemon(1,"bulbasaur",formatoDelTexto.parse("10-10-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(2,"ivysaur",formatoDelTexto.parse("11-10-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(3,"venusaur",formatoDelTexto.parse("12-11-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(4,"charmander",formatoDelTexto.parse("12-9-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(5,"charmeleon",formatoDelTexto.parse("12-5-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(6,"charizard",formatoDelTexto.parse("8-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(7,"squirtle",formatoDelTexto.parse("1-1-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(8,"wartortle",formatoDelTexto.parse("13-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(9,"blastoise",formatoDelTexto.parse("16-4-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(10,"caterpie",formatoDelTexto.parse("2-5-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(11,"metapod",formatoDelTexto.parse("6-7-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(12,"butterfree",formatoDelTexto.parse("20-2-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(13,"weedle",formatoDelTexto.parse("20-1-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(14,"kakuna",formatoDelTexto.parse("20-3-2019"));
            pokemonViewModel.insert(pokemon);
            pokemon=new Pokemon(15,"beedrill",formatoDelTexto.parse("20-4-2019"));
            pokemonViewModel.insert(pokemon);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
