package com.backend.Rina.services;

import com.backend.Rina.models.Receta;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WeeklyMenuList {

    @Autowired
    private RecetaService recetaService;

    public List<Document> obtenerIngredientesTotales(Document menuSemanal) {
        List<String> idsRecetas = extraerIdsRecetas(menuSemanal);
        List<Receta> recetas = new ArrayList<>();

        for (String id : idsRecetas) {
            recetaService.obtenerRecetaPorId(id).ifPresent(recetas::add);
        }

        return sumarIngredientes(recetas);
    }

    private List<String> extraerIdsRecetas(Document menuSemanal) {
        List<String> ids = new ArrayList<>();
        String[] dias = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo"};
        String[] comidas = {"desayuno", "almuerzo", "cena"};

        for (String dia : dias) {
            Document diaMenu = menuSemanal.get(dia, Document.class);
            if (diaMenu != null) {
                for (String comida : comidas) {
                    String idReceta = diaMenu.getString(comida);
                    if (idReceta != null && !idReceta.isEmpty()) {
                        ids.add(idReceta);
                    }
                }
            }
        }

        return ids;
    }

    private List<Document> sumarIngredientes(List<Receta> recetas) {
        Map<String, Document> ingredientesTotales = new HashMap<>();

        for (Receta receta : recetas) {
            receta.getIngredientes().forEach(ingrediente -> {
                String key = ingrediente.getNombre() + "_" + ingrediente.getCantidad().getUnidad();
                Document actual = ingredientesTotales.getOrDefault(key, new Document()
                        .append("nombre", ingrediente.getNombre())
                        .append("cantidad", new Document()
                                .append("valor", 0.0)
                                .append("unidad", ingrediente.getCantidad().getUnidad())
                        ));

                double valorActual = actual.get("cantidad", Document.class).getDouble("valor");
                actual.get("cantidad", Document.class).put("valor", valorActual + ingrediente.getCantidad().getValor());

                ingredientesTotales.put(key, actual);
            });
        }

        List<Document> resultado = new ArrayList<>(ingredientesTotales.values());
        resultado.sort(Comparator.comparing(d -> d.getString("nombre")));

        return resultado;
    }
}
