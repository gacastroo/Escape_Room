package com.escaperoom.modelo;
import lombok.Data;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class GameState {

    private final Set<String> puzzlesDisponibles = new HashSet<>();
    private final Set<String> puzzlesResueltos = new HashSet<>();
    private final Map<String, Integer> intentosPuzzle = new HashMap<>();

}
