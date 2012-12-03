package com.betthief.util;

import com.betthief.domain.Coeficent;
import com.betthief.domain.Game;

import java.util.ArrayList;

public class UI {

    public static double displayAverage(ArrayList<Game> liveGames) {
        double avgP1Win = 0.0;
        double avgX = 0.0;
        double avgP2Win = 0.0;
        for (Game game : liveGames) {
            System.out.println(game);

            Coeficent coeficent = game.getCoeficent();
            avgP1Win += coeficent.getPlayerOneWin();
            avgX += coeficent.getX();
            avgP2Win += coeficent.getPlayerTwoWin();

        }

        int members = liveGames.size();
        System.out
                .println("========================================================");
        System.out.println("AVG P1WIN:" + avgP1Win / members + " X:" + avgX
                / members + " P2WIN:" + avgP2Win / members);
       
        return avgP1Win / members;
    }

    
}
